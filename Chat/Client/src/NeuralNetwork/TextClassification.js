var math = require('math');
var fs = require('fs');
var nltk = require('natural');
var save = require('./TextSave.json');

var Neuron = function(MaxWeights) {
    this.weights = [];
    for (var i = 0; i < MaxWeights; i++) {
        this.weights.push(Math.random() * 2 - 1);
    }
    this.bias = Math.random() * 2 - 1;
}
Neuron.prototype.Forward = function(inputs) {
    this.lastInputs = inputs;
    var sum = 0;
    for (var i = 0; i < inputs.length; i++) {
        sum += inputs[i] * this.weights[i];
    }
    sum += this.bias;
    return this.lastOutputs = new Functions().Sigmoid(sum);
}

var Layer = function(MaxNeurons, MaxInputs) {
    this.neurons = [];
    for (var i = 0; i < MaxNeurons; i++) {
        this.neurons[i] = new Neuron(MaxInputs);
    }
}
Layer.prototype.Forward = function(inputs) {
    return this.neurons.map(function(neuron) {
        return neuron.Forward(inputs);
    })
}

var Dropout = function(DropRate, mode) {
    this.mask = 0;
    this.out = [];
    this.DropRate = DropRate;
    this.mode = mode;
}

var Network = function() {
    this.network = [];
}
Network.prototype.Forwardpropagation = function(inputs) {
    var outputs;
    this.network.forEach(function(layer) {
        outputs = layer.Forward(inputs);
        inputs = outputs;
    })
    return outputs;
}
Network.prototype.FullConnectedLayer = function(MaxNeurons, MaxInputs) {
    var index = 0;
    if (MaxInputs == null) {
        var prevLayer = this.network[this.network.length - 1];
        MaxInputs = prevLayer.neurons.length;
    }
    var layer = new Layer(MaxNeurons, MaxInputs);
    this.network.push(layer);
}
Network.prototype.Train = function(examples, LearningRate, epochs) {
    var outLayer = this.network[this.network.length - 1];
    var temp = [];

    for (var i = 0; i < epochs; i++) {
        for (var j = 0; j < examples.length; j++) {
            var targets = examples[j][1];
            var inputs = examples[j][0];
            var outputs = this.Forwardpropagation(inputs);
            for (let k = 0; k < outLayer.neurons.length; k++) {
                var neuron = outLayer.neurons[k];
                neuron.error = targets[k] - outputs[k];
                neuron.adjust = new Functions().SigmoidDerivative(neuron.lastOutputs) * neuron.error;
            }

            for (let h = this.network.length - 2; h >= 0; h--) {
                for (let f = 0; f < this.network[h].neurons.length; f++) {
                    var neuron = this.network[h].neurons[f];
                    neuron.error = math.sum(this.network[h + 1].neurons.map(function(n) {
                        return n.weights[f] * n.adjust;
                    }));

                    neuron.adjust = new Functions().SigmoidDerivative(neuron.lastOutputs) * neuron.error;

                    for (let m = 0; m < this.network[h + 1].neurons.length; m++) {
                        var nextNeuron = this.network[h + 1].neurons[m];
                        for (let w = 0; w < nextNeuron.weights.length; w++) {
                            nextNeuron.weights[w] += LearningRate * nextNeuron.lastInputs[w] * nextNeuron.adjust;
                        }
                        nextNeuron.bias += LearningRate * nextNeuron.adjust;
                    }
                }
            }
        }

        var error = new Functions().Mse(outLayer.neurons.map(function(n) {
            return n.error;
        }));

        if (i % 10000 == 0) {
            console.log("Iteration : ", i, " error : ", error);
        }
    }
}
Network.prototype.Serialize = function() {
    return JSON.stringify(this, null, ' ');
}
Network.prototype.Deserialize = function(inputs, serial) {
    var serialData = JSON.parse(serial);
    if (serialData) {
        this.network.length = 0;
        this.FullConnectedLayer(serialData.network[0].neurons.length, inputs);

        for (var i = 1; i < serialData.network.length; i++) {
            this.FullConnectedLayer(serialData.network[i].neurons.length)
        }

        for (var i = 0; i < serialData.network.length; i++) {
            for (var j = 0; j < serialData.network[i].neurons.length; j++) {
                this.network[i].neurons[j].bias = serialData.network[i].neurons[j].bias
                this.network[i].neurons[j].error = serialData.network[i].neurons[j].error
                this.network[i].neurons[j].lastOutput = serialData.network[i].neurons[j].lastOutput
                this.network[i].neurons[j].adjust = serialData.network[i].neurons[j].adjust

                for (var w = 0; w < serialData.network[i].neurons[j].weights.length; w++) {
                    this.network[i].neurons[j].weights[w] = serialData.network[i].neurons[j].weights[w];
                }

                this.network[i].neurons[j].lastInputs = [];
                for (var v = 0; v < serialData.network[i].neurons[j].weights.length; v++) {
                    this.network[i].neurons[j].lastInputs.push(serialData.network[i].neurons[j].lastInputs[v]);
                }
            }
        }
    } else {
        return false;
    }
}

var Functions = function() {};
Functions.prototype.Sigmoid = function(activation) {
    return 1.0 / (1.0 + Math.exp(-activation));
}
Functions.prototype.Activation = function(weights, inputs) {
    var activation = 0;
    for (var i = 0; i < weights.length; i++) {
        activation += weights[i] * inputs[i];
    }
    return activation;
}
Functions.prototype.Predict = function(phrase) {
    var net = new Network();
    var output = net.Process(phrase);
    return output;
}
Functions.prototype.SigmoidDerivative = function(out) {
    return out * (1.0 - out);
}
Functions.prototype.Mse = function(errors) {
    var sum = errors.reduce(function(sum, i) {
        return sum + i * i;
    }, 0);
    return sum / errors.length;
}
Functions.prototype.Softmax = function(vector) {
    var shiftx = [];
    for (var i = 0; i < vector.length; i++) {
        shiftx.push(Math.exp(vector[i]));
    }
    var exps = math.sum(shiftx);
    var softmax = [];
    for (var j = 0; j < shiftx.length; j++) {
        softmax.push((j / exps));
    }
    return softmax;
}

var Dataset = function() {}
var TrainingData = [];
var words = [];
var classes = [];
var documents = [];
Dataset.prototype.Add = function(label, sentence) {
    TrainingData.push({ "class": label, "sentence": sentence });
}
Dataset.prototype.Prepare = function() {
    words = [];
    classes = [];
    documents = [];
    var ignore = ['?'];

    for (var pattern = 0; pattern < TrainingData.length; pattern++) {
        var word = new nltk.AggressiveTokenizer().tokenize(TrainingData[pattern]['sentence']);
        for (var i = 0; i < word.length; i++) {
            words.push(word[i]);
        }
        documents.push({ "TokenClass": TrainingData[pattern]['class'], "token": word });
        classes.push(TrainingData[pattern]['class']);
    }

    var StemmedWords = [];
    for (var w = 0; w < words.length; w++) {
        var check = 0;
        for (var i = 0; i < ignore.length; i++) {
            if (words[w] == ignore[i]) {
                check++;
            }
        }
        if (check == 0) {
            StemmedWords[w] = nltk.LancasterStemmer.stem(words[w].toLowerCase());
        }
    }

    StemmedWords = Array.from(new Set(StemmedWords));
    classes = Array.from(new Set(classes));

    var training = [];
    var output = [];
    var empty = [];
    var dataset = [];

    for (var i = 0; i < classes.length; i++) {
        empty.push(0);
    }

    for (var doc = 0; doc < documents.length; doc++) {
        var bag = [];

        var PatternWords = documents[doc].token;
        for (var word = 0; word < PatternWords.length; word++) {
            PatternWords[word] = nltk.LancasterStemmer.stem(PatternWords[word]);
        }

        for (var w = 0; w < words.length; w++) {
            var check = 0;
            for (var pattern = 0; pattern < PatternWords.length; pattern++) {
                if (words[w] == PatternWords[pattern]) {
                    check++;
                }
            }
            if (check == 0) {
                bag.push(0);
            } else {
                if (check > 0) {
                    bag.push(1);
                }
            }
        }

        training.push(bag);
        var OutRow = empty;
        OutRow[classes.indexOf(documents[1].TokenClass)] = 1;
        output.push(OutRow);
    }
    dataset.push(training);
    dataset.push(output);
    return dataset;
}
Dataset.prototype.Clean = function(sentence) {
    var words = new nltk.AggressiveTokenizer().tokenize(sentence);
    for (var w = 0; w < words.length; w++) {
        words[w] = nltk.LancasterStemmer.stem(words[w].toLowerCase());
    }
    return words;
}
Dataset.prototype.Bow = function(sentence) {
    var SentenceWords = this.Clean(sentence);
    var bag = [];
    for (var j = 0; j < words.length; j++) {
        bag.push(0);
    }
    for (var s = 0; s < SentenceWords.length; s++) {
        for (var w = 0; w < words.length; w++) {
            if (words[w] == SentenceWords[s]) {
                bag[w] = 1;
            }
        }
    }

    return bag;
}
Dataset.prototype.Classify = function(sentence) {
    var j = 0;
    for (var k = 0; k < sentence.length; k++) {
        sentence[k] = [classes[k], sentence[k]];
    }
    return sentence;
}

var data = new Dataset();
data.Add('insult', "deficiente");
data.Add('insult', "imbecille");
data.Add('insult', "gay");
data.Add('insult', "mona");
data.Add('insult', "ritardato");
data.Add('insult', "bastardo");
data.Add('heavy insult', "stronzo");
data.Add('heavy insult', "coglione");
data.Add('heavy insult', "testa di cazzo");
data.Add('heavy insult', "puttana");
data.Add('heavy insult', "frocio di merda");
data.Add('blasfemy', "porco d");
data.Add('blasfemy', "boia d");
data.Add('blasfemy', "dio m");
data.Add('blasfemy', "stronzo d");
data.Add('blasfemy', "dio c");
data.Add('bad word', "cazzo");
data.Add('bad word', "figa");
data.Add('bad word', "merda");
data.Add('bad word', "cancro");
data.Add('bad word', "tumore");
data.Add('none', "come stai?");
data.Add('none', "oh sanata");
data.Add('none', "vinto");
data.Add('none', "finita la chat");
data.Add('none', "bella giornata");
data.Add('none', "oh ma cosa fai oggi?");
data.Add('none', "ara i fioi");
data.Add('none', "perso");
data.Add('none', "ci vediamo");
data.Add('none', "che figata");
data.Add('none', "oh ora pusho");
data.Add('none', "che fastidio");
data.Add('none', "si ma stai calmino eh");
data.Add('none', "oggi vado al mare. Te cosa fai?");
data.Add('none', "stai a casa che è meglio");
data.Add('none', "boia. Massa forte");


var dataset = data.Prepare();

var features = dataset[0];

var labels = [
    [1, 0, 0, 0, 0, 0],
    [1, 0, 0, 0, 0, 0],
    [1, 0, 0, 0, 0, 0],
    [1, 0, 0, 0, 0, 0],
    [1, 0, 0, 0, 0, 0],
    [1, 0, 0, 0, 0, 0],
    [0, 1, 0, 0, 0, 0],
    [0, 1, 0, 0, 0, 0],
    [0, 1, 0, 0, 0, 0],
    [0, 1, 0, 0, 0, 0],
    [0, 1, 0, 0, 0, 0],
    [0, 0, 1, 0, 0, 0],
    [0, 0, 1, 0, 0, 0],
    [0, 0, 1, 0, 0, 0],
    [0, 0, 1, 0, 0, 0],
    [0, 0, 1, 0, 0, 0],
    [0, 0, 0, 1, 0, 0],
    [0, 0, 0, 1, 0, 0],
    [0, 0, 0, 1, 0, 0],
    [0, 0, 0, 1, 0, 0],
    [0, 0, 0, 1, 0, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1, 0]
];

var examples = [];

for (var example = 0; example < features.length; example++) {
    examples.push([features[example], labels[example]]);
}

var net = new Network();
net.FullConnectedLayer(10, features[0].length);
net.FullConnectedLayer(5, features[0].length);
net.FullConnectedLayer(labels[0].length - 1);

//net.Train(examples, 0.5, 50000);

//var net = new Network();

net.Deserialize(15, JSON.stringify(save));
//fs.writeFile('./TextSave.json', net.Serialize());

var phrase = data.Bow("");

var out = net.Forwardpropagation(phrase);
var res = data.Classify(out);
console.log(out);

module.exports.Network = Network;
module.exports.Dataset = Dataset;
module.exports.Functions = Functions;
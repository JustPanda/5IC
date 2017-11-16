var WinJS = require('winjs');
var net = require('net');
var network = require('./src/NeuralNetwork/TextClassification');

(function() {

    WinJS.UI.processAll().done(function() {

        var startItem = document.querySelector('.start');

        //var model = new network.Network();
        //var data = new network.Dataset();

        var conn = net.createConnection({ port: 9090 }, () => {
            console.log('connected to server!');
            console.log(document.URL);
            conn.write("chat" + '\n');
            conn.write(document.URL.substring(document.URL.lastIndexOf('?') + 1) + '\n');
        });

        var send = document.getElementById('Send');

        var Send = function() {
            var message = document.getElementById('Message').value;
            var div = document.createElement("div");
            div.align = "right";
            div.style.position = "relative";
            div.innerHTML = message;
            document.getElementById('ChatSpace').appendChild(div);
            conn.write(message + '\n');
        }

        send.addEventListener("click", Send);

        conn.on("data", (data) => {
            console.log(data.toString());
            var div = document.createElement("div");
            div.align = "left";
            div.style.position = "relative";
            div.innerHTML = data.toString();
            document.getElementById('ChatSpace').appendChild(div);
        })
    });

})();
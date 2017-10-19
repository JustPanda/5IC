const webpack=require('webpack'),
    path=require('path');

const APP_DIR=path.resolve(__dirname, 'app');
const BUILD_DIR=path.resolve(__dirname, 'public');

var config = {
    entry: APP_DIR + '/index.jsx',
    output: {
        path: BUILD_DIR,
        filename: 'bundle.js'
    },
    module:{
        loaders:[
            {
                test : /\.jsx$/,
                include : APP_DIR,
                loader : 'babel-loader'
            },
            {
                test: /\.css$/,
                // include: APP_DIR+'/css',
                loader: "style-loader!css-loader"
            }
        ]
    }
};

module.exports=config;

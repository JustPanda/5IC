const webpack=require('webpack'),
    path=require('path');

const APP_DIR=path.resolve(__dirname, 'app');
const BUILD_DIR=path.resolve(__dirname, 'pages/public');

var config = {
    entry: {
        chat: APP_DIR+"/chat/index.jsx",
        login: APP_DIR+"/login/index.jsx",
        registration: APP_DIR+"/registration/index.jsx",
        error: APP_DIR+"/error/index.jsx"
    },
    output: {
        path: BUILD_DIR,
        filename: "[name].entry.js"
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
                loader: "style-loader!css-loader"
            },
            {
                test: /\.(png|jpg)$/,
                loader: 'file-loader?name=/img/[name].[ext]'
            }
        ]
    },
    target: 'electron-renderer'
};

module.exports=config;

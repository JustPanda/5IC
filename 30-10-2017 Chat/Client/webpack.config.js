const webpack = require( 'webpack' ), path = require( 'path' );

const APP_DIR = path.resolve( __dirname, 'Source' );

const BUILD_DIR = path.resolve( __dirname, 'Dist' );


var config = {
    entry: {
        chat: APP_DIR + "/HomeRenderer.js",
        login: APP_DIR + "/LoginRenderer.js",
        registration: APP_DIR + "/SignupRenderer.js"
    },
    output: {
        path: BUILD_DIR,
        filename: "[name].entry.js"
    },
    module: {
        loaders: [
            {
                test: /\.jsx$/,
                include: APP_DIR,
                loader: 'babel-loader'
            },
            {
                test: /\.js$/,
                include: APP_DIR,
                loader: 'babel-loader'
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

module.exports = config;
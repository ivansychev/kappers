var path = require("path");
var HtmlWebpackPlugin = require("html-webpack-plugin");
var devMode = process.env.NODE_ENV !== 'production';

module.exports = {
    mode : devMode ? 'development' : 'production',
    entry: "./src/index.jsx",
    output: {
        path: path.join(__dirname, "/dist"),
        filename: "bundle.js"
    },
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                enforce: "pre",
                loader: "eslint-loader",
                exclude: /node_modules/,
                options: {
                    emitWarning: true,
                    configFile: "./.eslintrc.json"
                }
            },
            {
                test: /\.(js|jsx)$/,
                loader: "babel-loader",
                exclude: /node_modules/,
                options: {
                    //@TODO move to .babelrc
                    presets: [
                        ["@babel/env", {
                            "targets": {
                                "browsers": "last 2 Chrome versions",
                                "node": "current"
                            }
                        }],
                        "@babel/react"
                    ],
                    plugins: [
                        ['@babel/plugin-proposal-class-properties', { "loose": true }]
                    ]
                }
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: "./src/index.html"
        })
    ]
};
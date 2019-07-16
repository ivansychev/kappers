var path = require("path");
var HtmlWebpackPlugin = require("html-webpack-plugin");
var devMode = process.env.NODE_ENV !== 'production';

//TODO: add source map to dev
//TODO: add husky hooks
//TODO: remove comments in config as soon as wiki for ui appears
//TODO: add styles support to project

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
                loader: "eslint-loader", //to lint our code and catch warns and errors before babel transpilation
                exclude: /node_modules/,
                options: {
                    emitWarning: true,
                    configFile: "./.eslintrc.json"
                }
            },
            {
                test: /\.(js|jsx)$/,
                loader: "babel-loader", //to transpile our modern JS to old JS
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
            },
            {
                test: /\.scss$/,
                use: [
                    {
                        loader: "style-loader" // to link and incorporate our style-in-JS in code
                    },
                    {
                        loader: "css-loader" // CSS --> style-in-JS
                    },
                    {
                        loader: "postcss-loader" // CSS --> to better crossplatform CSS
                    },
                    {
                        loader: "sass-loader" // SCSS --> CSS
                    }
                ]
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: "./src/index.html"
        })
    ]
};
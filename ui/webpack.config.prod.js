const path = require('path');
const base = require('./webpack.config');
const MiniCssExtractTextPlugin = require('mini-css-extract-plugin');

module.exports = Object.assign({}, base, {
  watch: false,
  output: {
    filename: '[name].bundle.js',
    libraryTarget: 'umd',
    path: path.resolve(__dirname, 'build'),
    publicPath: '/cah/'
  },
  plugins: base.plugins.concat([
    new MiniCssExtractTextPlugin({
      filename: '[name].[chunkhash].css'
    })
  ]),
  module: {
    rules: [
      {
        test: /\.(js)$/,
        exclude: /node_modules/,
        use: ['babel-loader']
      },
      {
        test: /node_modules\/reactforms\/.*\.(js)$/,
        use: ['babel-loader']
      },
      {
        test: /\.(png|jpe?g|svg|gif|eot|woff2?|ttf)$/i,
        use: [
          {
            loader: 'file-loader'
          }
        ]
      },
      {
        test: /\.html?$/i,
        use: ['html-loader']
      },
      {
        test: /\.css$/,
        use: [
          {
            loader: MiniCssExtractTextPlugin.loader
          },

          {
            loader: 'css-loader',
            options: {
              modules: {
                localIdentName: '[path][name]__[local]'
              }
            }
          }
        ]
      }
    ]
  }
});

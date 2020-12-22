const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  devtool: 'source-map',
  watch: true,
  entry: './src/pages/app.js',
  output: {
    filename: '[name].bundle.js',
    libraryTarget: 'umd',
    path: path.resolve(__dirname, 'build'),
    publicPath: '/cah'
  },
  devServer: {
    hot: true,
    port: 8000,
    host: '0.0.0.0',
    disableHostCheck: true,
    historyApiFallback: true,
    proxy: {
      '/api/cah': 'http://localhost:8080',
      '/api/cah/socket/playerConnection': {
        target: 'ws://localhost:8080',

        ws: true
      }
    }
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: 'src/html/index.html'
    })
  ],
  resolve: {
    alias: {
      react: path.resolve('node_modules/react')
    }
  },
  module: {
    rules: [
      {
        test: /\.(js)$/,
        exclude: /node_modules/,
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
          'style-loader',
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
};

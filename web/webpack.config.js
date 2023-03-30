const path = require('path');
const CopyPlugin = require("copy-webpack-plugin");
const Dotenv = require('dotenv-webpack');

// Get the name of the appropriate environment variable (`.env`) file for this build/run of the app
const dotenvFile = process.env.API_LOCATION ? `.env.${process.env.API_LOCATION}` : '.env';

module.exports = {
  plugins: [
    new CopyPlugin({
      patterns: [
        {
          from: "static_assets", to: "../",
          globOptions: {
            ignore: ["**/.DS_Store"],
          },
        },
      ],
    }),
    new Dotenv({ path: dotenvFile }),
  ],
  optimization: {
    usedExports: true
  },
  entry: {
    viewProfile: path.resolve(__dirname, 'src', 'pages', 'viewProfile.js'),
    createPlaylist: path.resolve(__dirname, 'src', 'pages', 'createPlaylist.js'),
    viewPlaylist: path.resolve(__dirname, 'src', 'pages', 'viewPlaylist.js'),
    searchPlaylists: path.resolve(__dirname, 'src', 'pages', 'searchPlaylists.js'),
    userDashboard: path.resolve(__dirname, 'src', 'pages', 'userDashboard.js'),
    editUserProfile: path.resolve(__dirname, 'src', 'pages', 'editUserProfile.js'),
    inbox: path.resolve(__dirname, 'src', 'pages', 'inbox.js'),
    connexions: path.resolve(__dirname, 'src', 'pages', 'connexions.js'),
    viewMessage: path.resolve(__dirname, 'src', 'pages', 'viewMessage.js'),

  },
  output: {
    path: path.resolve(__dirname, 'build', 'assets'),
    filename: '[name].js',
    publicPath: '/assets/'
  },
  devServer: {
    static: {
      directory: path.join(__dirname, 'static_assets'),
    },
    port: 8000,
    client: {
      // overlay shows a full-screen overlay in the browser when there are js compiler errors or warnings
      overlay: true,
    },
  },
  resolve: {
    fallback: {
      "fs": false,
      "tls": false,
      "net": false,
      "path": false,
      "zlib": false,
      "http": require.resolve("stream-http"),
      "https": require.resolve("https"),
      "https": false,
      "stream": false,
      "crypto": false,
      "https": require.resolve("https-browserify"),
      "crypto-browserify": require.resolve('crypto-browserify'),
    }
  }
}

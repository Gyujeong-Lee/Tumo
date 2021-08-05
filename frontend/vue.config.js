module.exports = {
  transpileDependencies: ["vuetify"],
  devServer: {
    proxy: "http://localhost:8080/api",
  },
};

const express = require("express");
const NacosNamingClient = require("nacos").NacosNamingClient;
const app = express();
const port = 3000;

const logger = {
  info: console.log,
  error: console.error,
  debug: console.log,
  warn: console.warn,
};

const nacosClient = new NacosNamingClient({
  serverList: "10.20.12.28:8848",
  namespace: "public",
  logger: logger,
  username: "nacos",
  password: "nacos",
});

app.get("/", (req, res) => {
  res.send("欢迎来到我的服务！");
});

app.listen(port, async () => {
  console.log(`My Service is running on http://localhost:${port}`);

  setTimeout(async () => {
    try {
      await nacosClient.registerInstance("node-service", {
        ip: "127.0.0.1",
        port: port,
      });
      console.log("Service registered with Nacos");
    } catch (error) {
      console.error("Failed to register service with Nacos:", error);
    }
  }, 1000);
});

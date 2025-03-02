const express = require("express");
const mysql = require("mysql");
const NacosNamingClient = require("nacos").NacosNamingClient;
const connection = require("./database");

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


app.use(express.json());
app.use(express.urlencoded({ extended: true }));

const getTodos = (req, res) => {
  const sql = "SELECT * FROM todos";
  connection.query(sql, (err, results) => {
    if (err) throw err;
    res.json(results);
  });
};

const getTodoById = (req, res) => {
  const id = req.params.id;
  const sql = "SELECT * FROM todos WHERE id = ?";
  connection.query(sql, [id], (err, results) => {
    if (err) throw err;
    res.json(results[0]);
  });
};

const createTodo = (req, res) => {
  const { title, completed } = req.body;


  if (!title) {
    return res.status(400).json({ error: "Title is required" });
  }

  console.log("Received title:", title);
  console.log("Received completed:", completed);

  const completedValue = completed === "true";

  const sql = "INSERT INTO todos (title, completed) VALUES (?, ?)";
  connection.query(sql, [title, completedValue], (err, results) => {
    if (err) {
      console.error("Error inserting todo:", err);
      res
        .status(500)
        .json({ error: "Failed to insert todo", details: err.message });
      return;
    }
    res
      .status(201)
      .json({ id: results.insertId, title, completed: completedValue });
  });
};

const updateTodo = (req, res) => {
  const id = req.params.id;
  const { title, completed } = req.body;


  if (!title) {
    return res.status(400).json({ error: "Title is required" });
  }

  console.log("Updating todo with ID:", id);
  console.log("New title:", title);
  console.log("New completed:", completed);

  const completedValue = completed === "true";

  const sql = "UPDATE todos SET title = ?, completed = ? WHERE id = ?";
  connection.query(sql, [title, completedValue, id], (err, results) => {
    if (err) {
      console.error("Error updating todo:", err);
      res
        .status(500)
        .json({ error: "Failed to update todo", details: err.message });
      return;
    }
    res.status(200).json({ id, title, completed: completedValue });
  });
};

const deleteTodo = (req, res) => {
  const id = req.params.id;

  const sql = "DELETE FROM todos WHERE id = ?";
  connection.query(sql, [id], (err, results) => {
    if (err) {
      console.error("Error deleting todo:", err);
      res
        .status(500)
        .json({ error: "Failed to delete todo", details: err.message });
      return;
    }
    res.status(200).json({ id, message: "Todo deleted successfully" });
  });
};

app.post("/todos", createTodo); 
app.get("/todos", getTodos); 
app.get("/todos/:id", getTodoById); 
app.put("/todos/:id", updateTodo); 
app.delete("/todos/:id", deleteTodo); 

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

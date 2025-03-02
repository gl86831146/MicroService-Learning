const mysql = require("mysql");

const connection = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "mysql",
  database: "user_center",
});

connection.connect((err) => {
  if (err) throw err;
  console.log("Connected to the database!");
});

module.exports = connection;

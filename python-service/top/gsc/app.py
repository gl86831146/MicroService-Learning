import pymysql
import threading
from flask import Flask, jsonify, request
from config import register_service, get_db_connection, send_heartbeat

app = Flask(__name__)

# 配置 MySQL 数据库
db_config = {
    "host": "127.0.0.1",
    "port": 3306,
    "user": "root",
    "password": "mysql",
    "database": "spring-boot",
    "charset": "utf8mb4"
}

@app.route("/")
def hello():
    return "Hello, Nacos with Flask!"

# 查
@app.route("/teacher", methods=["GET"])
def get_teachers():
    sql = "SELECT * FROM teacher"
    with get_db_connection() as conn:
        with conn.cursor(pymysql.cursors.DictCursor) as cursor:
            cursor.execute(sql)
            result = cursor.fetchall()
    return jsonify(result)

# 增
@app.route("/teacher", methods=["POST"])
def add_teacher():
    data = request.json
    name = data.get("name")
    sql = "INSERT INTO teacher (name) VALUES (%s)"
    with get_db_connection() as conn:
        with conn.cursor() as cursor:
            cursor.execute(sql, (name,))
            conn.commit()
    return jsonify({"message": "添加成功！"}), 201

# 改
@app.route("/teacher/<int:teacher_id>", methods=["PUT"])
def update_teacher(teacher_id):
    data = request.json
    name = data.get("name")
    sql = "UPDATE teacher SET name=%s WHERE id=%s"
    with get_db_connection() as conn:
        with conn.cursor() as cursor:
            cursor.execute(sql, (name, teacher_id))
            conn.commit()
    return jsonify({"message": "更新成功！"}), 200

# 删
@app.route("/teacher/<int:teacher_id>", methods=["DELETE"])
def delete_teacher(teacher_id):
    sql = "DELETE FROM teacher WHERE id=%s"
    with get_db_connection() as conn:
        with conn.cursor() as cursor:
            cursor.execute(sql, (teacher_id,))
            conn.commit()
    return jsonify({"message": "删除成功！"}), 200

if __name__ == "__main__":
    register_service()
    # 启动心跳线程
    heartbeat_thread = threading.Thread(target=send_heartbeat)
    heartbeat_thread.daemon = True
    heartbeat_thread.start()
    app.run(host="0.0.0.0", port=5050)  # 修正了这里的格式
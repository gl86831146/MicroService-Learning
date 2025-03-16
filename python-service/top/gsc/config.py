from nacos import NacosClient
import time
import pymysql

# 配置 Nacos 客户端
server_addr = "127.0.0.1:8848"  # Nacos 服务地址
namespace = "public"  # 命名空间 ID
username = "nacos"  # 用户名（如果启用了鉴权）
password = "nacos"  # 密码（如果启用了鉴权）
client = NacosClient(server_addr, namespace=namespace, username=username, password=password)


# 注册服务
def register_service():
    service_name = "python-service"  # 注意这里修改了服务名称，以区分不同的服务
    try:
        client.add_naming_instance(service_name, "127.0.0.1", 5000)  # 注册服务实例
        print(f"服务 {service_name} 已成功注册到 Nacos")
    except Exception as e:
        print(f"注册服务失败：{e}")

# 发送心跳包
def send_heartbeat():
    service_name = "python-service"
    ip = "127.0.0.1"
    port = 5000
    while True:
        try:
            client.send_heartbeat(service_name, ip, port)
            print(f"发送心跳： {time.strftime('%Y-%m-%d %H:%M:%S')}")
        except Exception as e:
            print(f"发送心跳失败：{e}")
            # 如果发送心跳失败，尝试重新注册服务
            register_service()
        time.sleep(5)  # 每隔5秒发送一次心跳

# 数据库连接函数
def get_db_connection():
    db_config = {
        "host": "127.0.0.1",
        "port": 3306,
        "user": "root",
        "password": "123456",
        "database": "spring-boot",
        "charset": "utf8mb4"
    }
    return pymysql.connect(**db_config)


import threading

from flask import Flask, request, send_file
import matplotlib.pyplot as plt
from wordcloud import WordCloud
import io
import config  # 导入 config 模块

app = Flask(__name__)

@app.route('/generate_wordcloud', methods=['POST'])
def generate_wordcloud():
    # 获取请求中的文本数据
    data = request.get_json()
    text = data.get('text')
    if not text:
        return "No text provided", 400

    # 生成词云
    wordcloud = WordCloud(width=800, height=400, background_color='white').generate(text)

    # 将词云保存到内存中
    img = io.BytesIO()
    plt.figure(figsize=(10, 5))
    plt.imshow(wordcloud, interpolation='bilinear')
    plt.axis('off')

    # 保存词云图片到本地
    save_path = 'wordcloud.png'
    plt.savefig(save_path, format='png')
    print(f"词云图片已保存到 {save_path}")

    # 将词云保存到内存中以便发送给客户端
    plt.savefig(img, format='png')
    img.seek(0)

    # 返回生成的词云图片
    return send_file(img, mimetype='image/png')


if __name__ == '__main__':
    config.register_service()  # 在应用启动时注册服务到 Nacos
    # 启动心跳线程
    heartbeat_thread = threading.Thread(target=config.send_heartbeat)
    heartbeat_thread.daemon = True
    heartbeat_thread.start()
    app.run(host="0.0.0.0", port=5000)  # 修正了这里的格式

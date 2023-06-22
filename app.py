from flask import Flask, request

app = Flask(__name__)

@app.route("/")
def hello_world():
    return "<p>Hello, World!</p>"

@app.route("/index.php", methods=["GET"])
def index_php():
    print(request.args.keys())
    return "<p>Hello, World!</p>"

@app.route("/index.php", methods=["POST"])
def index_php_post():
    print(request.args.keys())
    return "<p>Hello, World!</p>"
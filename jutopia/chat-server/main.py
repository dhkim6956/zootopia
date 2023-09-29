from fastapi import FastAPI
from py_eureka_client.cureka_client import EurekaClient
import httpx
import openai

INSTANCE_PORT = 9002
INSTANCE_HOST = "j9c108.p.ssafy.io"

app = FastAPI()

@app.on_event("startup")
async def eureka_init():
    global client
    client = EurekaClient(
        eureka_server=f"http://{INSTANCE_HOST}:8761/eureka",
        app_name="chat-server",
        instance_port=INSTANCE_PORT,
        instance_host=INSTANCE_HOST,
    )
    await client.start()
    
@app.on_event("shutdown")
async def destroy():
    await client.stop()

@app.get("/index")
def index():
    return {"message": "Welcome to chat server"}


from fastapi import FastAPI
from pydantic import BaseModel
from py_eureka_client.eureka_client import EurekaClient
from utils import generate_answer
# import httpx
# import openai

INSTANCE_PORT = 9002
INSTANCE_HOST = "j9c108.p.ssafy.io"

app = FastAPI()

class Question(BaseModel):
    message: str

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

@app.post("/ask")
async def answer(question: Question):
    ans = generate_answer(question.message)
    return {
        "from_server": True,
        "message": ans
    }
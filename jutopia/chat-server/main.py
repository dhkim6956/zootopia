from fastapi import FastAPI
from pydantic import BaseModel
from py_eureka_client.eureka_client import EurekaClient
from utils import generate_answer
from datetime import datetime
# import httpx
# import openai

INSTANCE_PORT = 9002
INSTANCE_HOST = "j9c108.p.ssafy.io"

app = FastAPI()

class Question(BaseModel):
    message: str

class Document(BaseModel):
    role: str
    from_server : bool
    message: str
    time: datetime

class Answer(BaseModel):
    from_server: bool
    message: str
    parsed_time: str

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
    now = datetime.now()
    return { 
            "from_server": True, 
            "message": ans, 
            "parsed_time": f"{'오전' if now.hour < 12 else '오후'} {now.strftime('%I:%M')}" 
            }
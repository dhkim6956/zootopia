import uvicorn
from fastapi import FastAPI
from py_eureka_client.eureka_client import EurekaClient
# from pymongo import MongoClient
# from pydantic import BaseModel
import httpx

# 유레카 관련 변수
INSTANCE_PORT = 9001
INSTANCE_HOST = "http://j9c108.p.ssafy.io"

NAVER_API_URL = "https://openapi.naver.com/v1/search/news.json"
CLIENT_ID = "OCDILfuJhLKAdvqraNNy"
CLIENT_SECRET = "pi8ZEzmb8L"

app = FastAPI()

@app.on_event("startup")
async def eureka_init():
    global client
    client = EurekaClient(
        eureka_server=f"http://{INSTANCE_HOST}:8761/eureka",
        app_name="news-server",
        instance_port=INSTANCE_PORT,
        instance_host=INSTANCE_HOST,
    )
    await client.start()
    
@app.on_event("shutdown")
async def destroy():
    await client.stop()
    
@app.get("/index")
def index():
    return {"message": "Welcome FastAPI Nerds"}

@app.get("/health")
def health_check():
    return {"status": "UP"}

@app.get("/{stock_name}/{display}/{start}/{sort}")
async def fetch_news(stock_name: str, display: int, start: int, sort: str):
    params = {
        "query": stock_name, # 검색할 주식 이름 (전체는 '시황' 으로 검색 추천)
        "display": display, # 검색할 뉴스 개수
        "start": start, # 뉴스 검색 시작점 (1부터 시작, 100까지 가능)
        "sort": sort # 'sim': 정확도 내림차순, 'date': 날짜 내림차순
    }
    headers = {
        "X-Naver-Client-Id": CLIENT_ID,
        "X-Naver-Client-Secret": CLIENT_SECRET
    }
    
    async with httpx.AsyncClient() as client:
        response = await client.get(NAVER_API_URL, params=params, headers=headers)
        
        # Error handling
        if response.status_code != 200:
            raise HTTPException(status_code=response.status_code, detail="Naver API call failed")
    
    return response.json()

# @app.get("/fs/{stock_name}")
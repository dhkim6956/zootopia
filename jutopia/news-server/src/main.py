'''
TODO:
    - uvicorn이나 socket을 이용해 랜덤포트로 돌아가게 하기 -> 일단 보류
    - requirements.txt 작성
    - Spring api-gateway 연결하기
    - 몽고DB에 시계열 DB 만들기
    - main에 우겨넣었는데 모듈화 하기
'''

# import socket
import uvicorn
from fastapi import FastAPI
from py_eureka_client.eureka_client import EurekaClient
from pymongo import MongoClient

# 대문자 변수들을 상황에 따라 조작하기

# EUREKA 관련 변수
INSTANCE_PORT = 9001
INSTANCE_HOST = "j9c108.p.ssafy.io"

# MongoDB 관련 변수
DB_NAME = "jutopia"
COLLECTION_NAME = "news"

app = FastAPI()

# 나중에 CRUD 제대로 할 때 구현하기
# Setting up the database connectivity (from pymongo import MongoClient) 
# def create_db_collections():
#     mongoClient = MongoClient('mongodb://localhost:27017/')
#     try:
#         db = mongoClient.obrs # 얘가 뭐지
#         buyers = db.buyer
#         users = db.login

# eureka 연결
@app.on_event("startup")
async def eureka_init(): 
    global client
    client = EurekaClient(
        eureka_server=f"https://{INSTANCE_HOST}:8761/eureka",
        app_name="fastapi-service",
        instance_port=INSTANCE_PORT,
        instance_host=INSTANCE_HOST,
    )
    await client.start()
    
    
# monbodb 연결
@app.on_event("startup")
async def mongodb_init(): 
    db_client = MongoClient("mongodb://j9c108.p.ssafy.io:27017")
    db = db_client[DB_NAME]
    collection = db[COLLECTION_NAME]
    
# 연결 종료
@app.on_event("shutdown")
async def destroy():
    # close_async_db()
    await client.stop()


# API 작성
@app.get("/index")
def index():
    return {"message": "Welcome FastAPI Nerds"}

@app.get("/test/{user_id}")
async def get_user(user_id: str): # 직렬화 못하는 코드이지만 test 코드이므로 일단 두자
    db_client = MongoClient(f"mongodb://{INSTANCE_HOST}:27017") # 각 API 호출마다 MongoDB에 연결하는 형식. 연결을 최적화하거나 풀링을 해야한다.
    db = db_client[DB_NAME]
    collection = db[COLLECTION_NAME]
    
    user = collection.find_one({"_id": user_id})
    return user

@app.post("/test")
async def create_user(user: dict):
    db_client = MongoClient(f"mongodb://{INSTANCE_HOST}:27017")
    db = db_client[DB_NAME]
    collection = db[COLLECTION_NAME]
    
    collection.insert_one(user)
    return {"message": "User Created successfully"}


# if __name__ == "__main__":
#     uvicorn.run(app, port=INSTANCE_PORT)
from fastapi import APIRouter, HTTPException
from pymongo import MongoClient
from datetime import datetime, timedelta
from pydantic import BaseModel

router = APIRouter()
connection_string = "mongodb://juto:juto1234@mongo1:27017,mongo2:27018,mongo3:27019/?replicaSet=jutopia-repl"

client = MongoClient(connection_string)
db = client['jutopia']
pykrx_collection = db['pykrx']
realtime_collection = db['realtime']

@router.get("/chart/{stock_name}/{time_frame}") # time_frame: day, hour, minute
def get_chart(stock_name: str, time_frame: str):
    now = datetime.now()
    
    if time_frame == "day":
        # 최근 30개의 일별 종가 데이터
        pykrx_data = pykrx_collection.find_one({"_id": stock_name})
        if not pykrx_data or "OHLCV" not in pykrx_data:
            raise HTTPException(status_code=404, detail="pykrx's OHLCV data not found")
        
        daily_data = list(pykrx_data["OHLCV"].items())
        daily_data.sort(key=lambda x: x[0], reverse=True) # 날짜 기준 내림차순 정렬
        latest_data = daily_data[:30]
        
        stocks = [{"stock_name": stock_name, "time": date, "price": data["종가"]} for date, data in latest_data]
        
        # start_date = now - timedelta(days=30)
        # results = realtime_collection.find({
        #     "stock_name": stock_name,
        #     "time": {"$gte": start_date.strftime('%a %b %d %H:%M:%S %Y')}
        # }).sort("time", -1).limit(30)

    elif time_frame == "hour":
        # 지난 24시간 동안의 시간별 마지막 데이터
        start_date = now - timedelta(hours=24)
        results = realtime_collection.find({
            "stock_name": stock_name,
            "time": {"$gte": start_date.strftime('%a %b %d %H:%M:%S %Y')}
        }).sort("time", -1).limit(24)

    elif time_frame == "minute":
        # 지난 60분 동안의 분별 마지막 데이터
        start_date = now - timedelta(minutes=60)
        results = realtime_collection.find({
            "stock_name": stock_name,
            "time": {"$gte": start_date.strftime('%a %b %d %H:%M:%S %Y')}
        }).sort("time", -1).limit(60)

    else:
        raise HTTPException(status_code=400, detail="Invalid time_frame value")

    # 데이터 변환
    if time_frame != "day":
        stocks_temp = list(results)
        stockes = []
        for stock in stocks_temp:
            stock_info = {
                "stock_name": stock["stock_name"],
                "time": stock["time"],
                "price": stock["현재 주식 가격"]
            }
            stocks.append(stock_info)

    return stocks
from fastapi import APIRouter, HTTPException
from fastapi.responses import JSONResponse
from pymongo import MongoClient
from datetime import datetime, timedelta
from pydantic import BaseModel

router = APIRouter()
connection_string = "mongodb://juto:juto1234@mongo1:27017,mongo2:27018,mongo3:27019/?replicaSet=jutopia-repl"

client = MongoClient(connection_string)
db = client['jutopia']
pykrx_collection = db['pykrx']
realtime_collection = db['realtime']
test_collection = db['test']

@router.get("/dbtest")
def dbtest():
    return test_collection.find()

# stock_name_param: samsung, hanhwa, naver, sm, hyundai
# time_frame: day, hour, minute
@router.get("/chart/{stock_name_param}/{time_frame}") 
def get_chart(stock_name_param: str, time_frame: str):
    stock_name = ''
    if (stock_name_param == 'samsung'):
        stock_name = '삼성전자'
    elif (stock_name_param == 'hanhwa'):
        stock_name = '한화'
    elif (stock_name_param == 'hyundai'):
        stock_name = '현대차'
    elif (stock_name_param == 'naver'):
        stock_name = 'NAVER'
    elif (stock_name_param == 'sm'):
        stock_name = '에스엠'
    now = datetime.now()
    
    if time_frame == "day":
        # 최근 30개의 일별 종가 데이터
        pykrx_data = pykrx_collection.find_one({"_id": stock_name})
        if not pykrx_data or "OHLCV" not in pykrx_data:
            raise HTTPException(status_code=404, detail="pykrx's OHLCV data not found")
        
        daily_data = list(pykrx_data["OHLCV"].items())
        daily_data.sort(key=lambda x: x[0], reverse=True) # 날짜 기준 내림차순 정렬
        latest_data = daily_data[:30]
        
        stocks = [{"회사명": stock_name, "시간": date, "현재 주식 가격": data["종가"]} for date, data in latest_data]
        
        # start_date = now - timedelta(days=30)
        # results = realtime_collection.find({
        #     "회사명": stock_name,
        #     "시간": {"$gte": start_date.strftime('%a %b {0:02} %H:%M:%S %Y')}
        # }).sort("시간", -1).limit(30)

    elif time_frame == "hour":
        # 지난 24시간 동안의 시간별 마지막 데이터
        start_date = now - timedelta(hours=24)
        results = realtime_collection.find({
            "회사명": stock_name
            # "시간": {"$gte": start_date.strftime('%a %b  %d %H:%M:%S %Y')}
        }).sort("시간", -1).limit(24)

    elif time_frame == "minute":
        # 지난 60분 동안의 분별 마지막 데이터
        start_date = now - timedelta(minutes=60)
        print(f"debug start_date : {start_date}")
        results = realtime_collection.find({
            "회사명": stock_name
            # "시간": {"$gte": start_date.strftime('%a %b  %d %H:%M:%S %Y')}
        }).sort("시간", -1).limit(60)
        print(f"debug results : {results}")
        print(f"debug list(results) : {list(results)}")

    else:
        raise HTTPException(status_code=400, detail="Invalid time_frame value")

    # 데이터 변환
    if time_frame != "day":
        print(f"debug time_frame : {time_frame}")
        stocks_temp = list(results)
        print(f"debug stocks_temp : {stocks_temp}")
        stocks = []
        for stock in stocks_temp:
            print(f"debug stock in stocks_temp : {stock}")
            stock_info = {
                "회사명": stock["회사명"],
                "시간": stock["시간"],
                "현재 주식 가격": stock["현재 주식 가격"]
            }
            stocks.append(stock_info)
        print(f"debug stocks before return : {stocks}")
    return stocks

@router.get("/stocks")
def get_latest_stocks():
    company_names = ['삼성전자', '현대차', 'NAVER', '에스엠', '한화']

    pipeline = [
        {
            "$match": {
                "회사명": {
                    "$in": company_names
                }
            }
        },
        {
            "$sort": {
                "시간": -1
            }
        },
        {
            "$group": {
                "_id": "$회사명",
                "data": {
                    "$push": {
                        "currentPrice": "$현재 주식 가격",
                        "timestamp": "$시간"
                    }
                }
            }
        },
        {
            "$project": {
                "stockName": "$_id",
                "nowMoney": {
                    "$arrayElemAt": ["$data.currentPrice", 0]
                },
                "prevMoney": {
                    "$arrayElemAt": ["$data.currentPrice", 1]
                }
            }
        }
    ]

    try:
        result = list(realtime_collection.aggregate(pipeline))
        # '_id' 제거
        for item in result:
            item.pop("_id", None)
        return JSONResponse(content=result)
    except Exception as e:
        return HTTPException(detail=str(e), status_code=500)
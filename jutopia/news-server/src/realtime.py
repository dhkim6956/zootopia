from fastapi import APIRouter, HTTPException
from fastapi.responses import JSONResponse
from pymongo import MongoClient
from datetime import datetime, timedelta
from pydantic import BaseModel
from pykrx import stock
import pandas as pd

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

# ticker: 005930, 000880, 005380, 035420, 041510
# time_frame: day, hour, minute
@router.get("/chart/{ticker}/{time_frame}") 
def get_chart(ticker: str, time_frame: str):
    stock_name = ''
    if (ticker == '005930'):
        stock_name = '삼성전자'
    elif (ticker == '000880'):
        stock_name = '한화'
    elif (ticker == '005380'):
        stock_name = '현대차'
    elif (ticker == '035420'):
        stock_name = 'NAVER'
    elif (ticker == '041510'):
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
        
        # DataFrame 생성 후, 인덱스 설정 및 정렬
        df = pd.DataFrame(latest_data, columns=["timestamp", "data"])
        df['timestamp'] = pd.to_datetime(df['timestamp'])
        df.set_index('timestamp', inplace=True)
        df.sort_index(inplace=True)
        
        # 결과 반환을 위한 Dictionary 생성
        result = {
            "sign": {},
            "name": {},
            "price": {},
            "price_change_prevday": {},
            "percent": {}
        }
        
        # 데이터 파싱 및 적재
        for index, row in df.iterrows():
            timestamp_str = index.strftime('%m월 %d일')
            result["name"][timestamp_str] = stock_name
            result["price"][timestamp_str] = row['data']['종가']
            result["sign"][timestamp_str] = None
            result["price_change_prevday"][timestamp_str] = None
            result["percent"][timestamp_str] = None
        
        return result
        
        # 최근 30개의 일별 종가 데이터
        # pykrx_data = pykrx_collection.find_one({"_id": stock_name})
        # if not pykrx_data or "OHLCV" not in pykrx_data:
        #     raise HTTPException(status_code=404, detail="pykrx's OHLCV data not found")
        
        # daily_data = list(pykrx_data["OHLCV"].items())
        # daily_data.sort(key=lambda x: x[0], reverse=True) # 날짜 기준 내림차순 정렬
        # latest_data = daily_data[:30]
        
        # stocks = [{"회사명": stock_name, "시간": date, "현재 주식 가격": data["종가"]} for date, data in latest_data]
        
        # # start_date = now - timedelta(days=30)
        # # results = realtime_collection.find({
        # #     "회사명": stock_name,
        # #     "시간": {"$gte": start_date.strftime('%a %b {0:02} %H:%M:%S %Y')}
        # # }).sort("시간", -1).limit(30)

    elif time_frame == "hour": # 지난 24시간간 1시간 단위 종가 데이터
        results = realtime_collection.find({ # results의 타입은 cursor -> MongoDB find()의 결과
            "회사명": stock_name,
            "시간" : {"$gte": datetime.now() - timedelta(hours=24)} # (now - 24h) ~ now
        })
        df = pd.DataFrame(list(results)) # list(cursor)를 df로 변환
        
        if "_id" in df.columns: # ObjectId 는 직렬화 불가능하기 _id 컬럼 삭제
            df.drop(columns=["_id"], inplace=True)
        
        df['시간'] = pd.to_datetime(df['시간']) # ISODate 타입을 datetime 타입으로 변환
        df.set_index('시간', inplace=True) # 시간을 인덱스로 설정
        resampled_data = df.resample('1H').last() # 1시간 단위로 resample
        cleaned_data = resampled_data[-24:] # 최근 24개의 데이터만 추출
        
        cleaned_data.rename(columns= {"부호": "sign", 
                            "회사명": "name", 
                            "현재 주식 가격":"price", 
                            "전일대비 변화 가격":"price_change_prevday", 
                            "퍼센트":"percent"
                            }, inplace=True)
        
        cleaned_data.index = cleaned_data.index.strftime('%m/%d %H시')
                
        return cleaned_data.to_dict() # df를 dict로 변환하여 반환

    elif time_frame == "minute": # 지난 60분간 1분 단위 종가 데이터
        results = realtime_collection.find({ # results의 타입은 cursor -> MongoDB find()의 결과
            "회사명": stock_name,
            "시간": {"$gte": datetime.now() - timedelta(minutes=60)} # (now - 60m) ~ now
        })
        df = pd.DataFrame(list(results)) # list(cursor)를 df로 변환
        
        if "_id" in df.columns: # ObjectId 는 직렬화 불가능하기 _id 컬럼 삭제
            df.drop(columns=["_id"], inplace=True)
        
        df['시간'] = pd.to_datetime(df['시간']) # ISODate 타입을 datetime 타입으로 변환
        df.set_index('시간', inplace=True) # 시간을 인덱스로 설정
        resampled_data = df.resample('1T').last() # 1분 단위로 resample
        cleaned_data = resampled_data[-60:] # 최근 60개의 데이터만 추출
        
        cleaned_data.rename(columns= {"부호": "sign", 
                            "회사명": "name", 
                            "현재 주식 가격":"price", 
                            "전일대비 변화 가격":"price_change_prevday", 
                            "퍼센트":"percent"
                            }, inplace=True)
        
        cleaned_data.index = cleaned_data.index.strftime('%H:%M') # 시간 파싱
        
        return cleaned_data.to_dict() # df를 dict로 변환하여 반환
        

    else:
        raise HTTPException(status_code=400, detail="Invalid time_frame value")

    # 데이터 변환
    # if time_frame != "day":
    #     stocks_temp = list(results)
    #     print(f"debug stocks_temp : {stocks_temp}")
    #     stocks = []
    #     for stock in stocks_temp:
    #         print(f"debug stock in stocks_temp : {stock}")
    #         stock_info = {
    #             "회사명": stock["회사명"],
    #             "시간": stock["시간"],
    #             "현재 주식 가격": stock["현재 주식 가격"]
    #         }
    #         stocks.append(stock_info)
    #     print(f"debug stocks before return : {stocks}")
    # return stocks

@router.get("/stocks/")
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

@router.get("/stocks/{ticker}")
def get_latest_stocks(ticker: str):
    ticker_to_name = {
        '005930': '삼성전자',
        '005380': '현대차',
        '035420': 'NAVER',
        '041510': '에스엠',
        '000880': '한화'
    }
    
    company_name = ticker_to_name.get(ticker)
    if not company_name:
        raise HTTPException(status_code=404, detail=f"No data found for ticker: {ticker}")

    pipeline = [
        {
            "$match": {
                "회사명": company_name
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
        # Check if a result was found
        if not result:
            raise HTTPException(status_code=404, detail=f"No data found for ticker: {ticker}")
        # Return the first item in the result
        stock_data = result[0]
        stock_data.pop("_id", None)
        return JSONResponse(content=stock_data)
    except Exception as e:
        return HTTPException(detail=str(e), status_code=500)
from fastapi import APIRouter, HTTPException
import httpx

NAVER_API_URL = "https://openapi.naver.com/v1/search/news.json"
CLIENT_ID = "OCDILfuJhLKAdvqraNNy"
CLIENT_SECRET = "pi8ZEzmb8L"

router = APIRouter()

@router.get("/{stock_name}/{display}/{start}/{sort}")
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
import time
from selenium.webdriver.common.by import By
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
import os
from datetime import datetime
from pymongo import MongoClient

connection_string = "mongodb://juto:juto1234@mongo1:27017,mongo2:27018,mongo3:27019/?replicaSet=jutopia-repl"

client = MongoClient(connection_string)
db = client['jutopia']
realtime_collection = db['realtime']

clear = lambda: os.system('cls')
options = Options()
options.add_argument('headless')
driver = webdriver.Chrome(options=options)
driver.get('https://finance.naver.com/')
time.sleep(2)

flag = ""
find_list = ["005930","005380","000880","041510","035420"]
stocks_price = []
company_list = []
total = {}

def flags(to_exday):
    print('flags')
    to_exday_noup = to_exday.find_elements(By.CLASS_NAME,"no_up")
    if not to_exday_noup:
        flag = "하락"
    else:
        flag = "상승"
    flag_mapping = {
        "상승": "no_up",
        "하락": "no_down"
    }
    flag = flag_mapping.get(flag,flag)
    return flag

def plus_minus_flags(flag):
    print('plus_minus_flags')
    flag_mapping = {
        "no_up": "+",
        "no_down": "-"
    }
    flag = flag_mapping.get(flag,flag)
    return flag

def reverse_flags(flag):
    print('reverse_flags')
    flag_mapping = {
        "no_up": "상승",
        "no_down": "하락",
        "+": "상승",
        "-": "하락"
    }
    flag = flag_mapping.get(flag,flag)
    return flag

def to_exday_parser(to_exday):
    print('to_exday_parser')
    result_list = []
    flag = flags(to_exday)
    to_exday_noup = to_exday.find_elements(By.CLASS_NAME,flag)
    for class_name in to_exday_noup:
        data_list = class_name.find_elements(By.TAG_NAME,"span")
        for data in data_list:
            result_list.append(data.text)
    reverse_flags(flag)
    return result_list
        
def currently_stock_price(to_day,to_exday):
    print('currently_stock_price')
    flag = flags(to_exday)
    stock_price_list = []
    tag_name_list = to_day.find_element(By.CLASS_NAME,flag)
    word_token = tag_name_list.find_elements(By.TAG_NAME,"span")
    for word in word_token:
        stock_price_list.append(word.text)
        price = "".join(stock_price_list)
    stock_price_list.append(price)
    reverse_flags(flag)
    return price
        
def calculate_daily_change(to_exday):
    print('calcluate_daily_change')
    flag = flags(to_exday)
    flag = plus_minus_flags(flag)
    data_list = to_exday_parser(to_exday)
    end_index = data_list.index(flag)
    price = ''.join(data_list[1:end_index])
    reverse_flags(flag)
    return price
def sign(to_exday):
    print('sign')
    flag = flags(to_exday)
    sign = plus_minus_flags(flag)
    reverse_flags(flag)
    return sign

def stock_price_percent_change(to_exday):
    print('debug: stock_price_percent_change')
    flag = flags(to_exday)
    flag = plus_minus_flags(flag)
    data_list = to_exday_parser(to_exday)
    start_index = data_list.index(flag) + 1
    end_index = data_list.index('%')
    price = ''.join(data_list[start_index:end_index])
    reverse_flags(flag)
    return price

def currently_time():
    print('debug: currently_time')
    timestemp = time.strftime('%c', time.localtime(time.time()))
    return timestemp

def get_compnay_name():
    time.sleep(20)
    for n,i in enumerate(find_list):
        search_box = driver.find_element(By.NAME,'query')
        time.sleep(0.8)
        search_box.send_keys(i)
        time.sleep(0.8)
        search_box.submit()
        time.sleep(0.8)
        company = driver.find_elements(By.XPATH,"//*[@id='middle']/div[1]/div[1]/h2/a")
        to_day = driver.find_element(By.CLASS_NAME,"no_today")
        to_exday = driver.find_element(By.CLASS_NAME,"no_exday")
        for company_name in company:
            company_name = company_name.text
            if company_name not in company_list:
                company_list.append(company_name)
            flags(to_exday)
            total.update({
                '부호':sign(to_exday), 
                '회사명':company_name,
                '현재 주식 가격':currently_stock_price(to_day,to_exday),
                '전일대비 변화 가격':calculate_daily_change(to_exday),
                '퍼센트':stock_price_percent_change(to_exday),
                '시간': currently_time()
                })
            print(total)
            
            # MongoDB에 저장
            realtime_collection.insert_one(total.copy())
            
            # with open("realtime.txt", "a", encoding="utf-8") as f:
            #     f.write(str(total))
            #     f.write(",\n")
def start():
    get_compnay_name()
    
while True:
    print('while True')
    try :
        start()
            
    except Exception as e:
        print(f"Error: {e}")

        time.sleep(1)

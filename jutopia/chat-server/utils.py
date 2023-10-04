import openai
from bs4 import BeautifulSoup
import requests
import re
from transformers import PreTrainedTokenizerFast, BartForConditionalGeneration

openai.api_key = "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"

# 파싱용 변수
punct = "/-'?!.,#$%\'()*+-/:;<=>@[\\]^_`{|}~" + '""“”’' + '∞θ÷α•à−β∅³π‘₹´°£€\×™√²—–&'
punct_mapping = {"‘": "'", "₹": "e", "´": "'", "°": "", "€": "e", "™": "tm", "√": " sqrt ", "×": "x", "²": "2", "—": "-", "–": "-", "’": "'", "_": "-", "`": "'", '“': '"', '”': '"', '“': '"', "£": "e", '∞': 'infinity', 'θ': 'theta', '÷': '/', 'α': 'alpha', '•': '.', 'à': 'a', '−': '-', 'β': 'beta', '∅': '', '³': '3', 'π': 'pi', }

def clean(text, punct, mapping):
    for p in mapping:
        text = text.replace(p, mapping[p])

    for p in punct:
        text = text.replace(p, f' {p} ')

    specials = {'\u200b': ' ', '…': ' ... ', '\ufeff': '', 'करना': '', 'है': ''}
    for s in specials:
        text = text.replace(s, specials[s])

    return text.strip()

def clean_str(text):
    pattern = '([a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+)' # E-mail제거
    text = re.sub(pattern=pattern, repl='', string=text)
    pattern = '(http|ftp|https)://(?:[-\w.]|(?:%[\da-fA-F]{2}))+' # URL제거
    text = re.sub(pattern=pattern, repl='', string=text)
    pattern = '([ㄱ-ㅎㅏ-ㅣ]+)'  # 한글 자음, 모음 제거
    text = re.sub(pattern=pattern, repl='', string=text)
    pattern = '<[^>]*>'         # HTML 태그 제거
    text = re.sub(pattern=pattern, repl='', string=text)
    pattern = '[^\w\s\n]'         # 특수기호제거
    text = re.sub(pattern=pattern, repl='', string=text)
    text = re.sub('[-=+,#/\?:^$.@*\"※~&%ㆍ!』\\‘|\(\)\[\]\<\>`\'…》]','', string=text)
    text = re.sub('\n', '.', string=text)
    return text

# ---------------- funcs for api start line ---------------- 

def generate_answer(input: str):
    messages = [
        {
            "role": "system",
            "content": "다음 질문에 대해 초등학생이 이해하기 쉬운 내용과 말투로 답해줘. 초등학생이 좋아하게 이모지도 함께 넣어줘."
        }
    ]
    
    # mongoDB에서 대화를 받아와서 같은 유저의 대화라면 messages.append 로 시간순으로 이어붙인다.
    
    messages.append({"role": "user", "content": f"{input}"})
    completion = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=messages
    )
    reply = completion.choices[0].message.content
    return reply

def generate_newssum_answer(input: str):
    messages = [
        {
            "role" : "system",
            "content": "다음 뉴스를 초등학생이 이해하기 쉽게, 초등학생이 이해하기 쉬운 말투로 요약해줘. 초등학생이 좋아하게 이모지도 함께 넣어줘."
        }
    ]
    
    messages.append({"role": "user", "content": f"{input}"})
    completion = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=messages
    )
    reply = completion.choices[0].message.content
    return reply

def news_scrape(url: str):
    response = requests.get(url)
    html = response.text
    soup = BeautifulSoup(html, "html.parser")
    article_body = soup.select_one("#newsct_article")
    
    if article_body:
        return article_body.text.strip()
    else:
        return "본문을 찾을 수 없습니다."

def news_summary(url: str):
    print(f"news_summary : {url}")
    tokenizer = PreTrainedTokenizerFast.from_pretrained("ainize/kobart-news")
    model = BartForConditionalGeneration.from_pretrained("ainize/kobart-news")
    
    news_text = news_scrape(url).replace("\n", " ")
    
    print(f"news_text2 : {news_text}")
    print(f"news_text2 type : {type(news_text)}")
    
    # news_text = clean_str(news_text)
    
    input_ids = tokenizer.encode(news_text, return_tensors="pt")
    print(f"input_ids : {input_ids}")
    print(f"input_ids size: {input_ids.size()}")
    
    input_ids = input_ids[:, :1024]
        
    print(f"input_ids 2: {input_ids}")
    print(f"input_ids size 2: {input_ids.size()}")
    
    summary_text_ids = model.generate(
        input_ids=input_ids,
        bos_token_id=model.config.bos_token_id,
        eos_token_id=model.config.eos_token_id,
        length_penalty=1.0, #길이에 대한 penalty, 1보다 작은경우 더 짧은 문장 생성 유도 1보다 크면 긴문장 유도
        max_length=256, #요약문의 최대 길이 설정
        min_length=0, #요약문의 최소 길이 설정
        num_beams=4, ##문장 생성시 다음 단어를 탐색하는 영역의 개수
    )
    print(f"summary_text_idx : {summary_text_ids}")
    print(f"news_summary return: {tokenizer.decode(summary_text_ids[0], skip_special_tokens=True)}")    
    return tokenizer.decode(summary_text_ids[0], skip_special_tokens=True)  


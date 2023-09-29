import openai

openai.api_key = "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"

def generate_answer(input: str):
    messages = [
        {
            "role": "user",
            "content": """다음 질문에 대해 초등학생이 이해하기 쉬운 내용과 말투로 답해줘."""
        }
    ]
    
    messages.append({"role": "user", "content": f"{input}"})
    completion = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=messages
    )
    reply = completion.choices[0].message.content
    return reply
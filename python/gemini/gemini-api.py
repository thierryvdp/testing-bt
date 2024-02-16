import pathlib
import textwrap
import os

# documentation https://ai.google.dev/tutorials/python_quickstart

# pip install -q -U google-generativeai
# get api key https://aistudio.google.com/app/apikey
# attendre pas encore dispo en France
import google.generativeai as genai

from IPython.display import display
from IPython.display import Markdown


def to_markdown(text):
  text = text.replace('•', '  *')
  return Markdown(textwrap.indent(text, '> ', predicate=lambda _: True))

# Used to securely store your API key
from google.colab import userdata

# Or use `os.getenv('GOOGLE_API_KEY')` to fetch an environment variable.
# GOOGLE_API_KEY=userdata.get('GOOGLE_API_KEY')
GOOGLE_API_KEY=os.getenv('GEMINI_KEY')

genai.configure(api_key=GOOGLE_API_KEY)


for m in genai.list_models():
  if 'generateContent' in m.supported_generation_methods:
    print(m.name)

model = genai.GenerativeModel('gemini-pro')

response = model.generate_content("What is the meaning of life?")
to_markdown(response.text)
response.prompt_feedback

response = model.generate_content("pourquoi le ciel est bleu ?", stream=True)
for chunk in response:
  print(chunk.text)
  print("_"*80)

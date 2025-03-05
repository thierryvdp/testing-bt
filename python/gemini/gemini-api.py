import pathlib
import textwrap
import os

# https://ai.google.dev/pricing?hl=fr
# documentation https://ai.google.dev/tutorials/python_quickstart
#               https://ai.google.dev/gemini-api/docs/get-started/tutorial?hl=fr&lang=python

# pip install wheel setuptools pip --upgrade
# pip install -q -U google-generativeai
# pip install google-colab (pas sur que ce soit obligatoire ... en plus erreur : ERROR: Could not find a version that satisfies the requirement google-colab (from versions: none)
#                                                                                ERROR: No matching distribution found for google-colab
# get api key https://aistudio.google.com/app/apikey
# https://aistudio.google.com/app/apikey
# projet 774215552930
# https://ai.google.dev/gemini-api/docs/get-started/tutorial?hl=fr&lang=python

#
# FailedPrecondition: 400 Gemini API free tier is not available in your country.
# Please enable billing on your project in Google AI Studio.

import google.generativeai as genai

from IPython.display import display
from IPython.display import Markdown


def to_markdown(text):
  text = text.replace('•', '  *')
  return Markdown(textwrap.indent(text, '> ', predicate=lambda _: True))

# Used to securely store your API key
# from google.colab import userdata

# Or use `os.getenv('GOOGLE_API_KEY')` to fetch an environment variable.
# GOOGLE_API_KEY=userdata.get('GOOGLE_API_KEY')
GOOGLE_API_KEY=os.getenv('GEMINI_KEY')

genai.configure(api_key=GOOGLE_API_KEY)

# liste les modèles disponibles
for m in genai.list_models():
  if 'generateContent' in m.supported_generation_methods:
    print(m.name)

# model = genai.GenerativeModel('gemini-pro')
model = genai.GenerativeModel('gemini-1.5-flash')

response = model.generate_content("What is the meaning of life?")
to_markdown(response.text)
response.prompt_feedback

response = model.generate_content("pourquoi le ciel est bleu ?", stream=True)
for chunk in response:
  print(chunk.text)
  print("_"*80)

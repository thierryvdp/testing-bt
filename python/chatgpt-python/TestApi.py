from openai import OpenAI
import os

# Configurer une clé d'API valide
OpenAI.api_key = os.environ['OPENAI_API_KEY']
client = OpenAI(
  organization='org-9CiK5hsQrnIMFCHC74N00MbR',
)

# Demander à l'utilisateur de saisir une recherche
prompt = input("Tape ta question : ")

stream = client.chat.completions.create(
    model="gpt-3.5-turbo-0613",
    messages=[{"role": "user", "content": prompt}],
    stream=True,
)
for chunk in stream:
    if chunk.choices[0].delta.content is not None:
        print(chunk.choices[0].delta.content, end="")
        

# Envoyer une requête à l'API de GPT

#response = openai.Completion.create(
#    engine="text-davinci-003",
#    prompt=prompt,
#    max_tokens=2048,
#    n=1,
#    stop=None,
#    temperature=0.5,
#)

# Afficher la réponse
#print(response)
#
#print(response["choices"][0]["text"])

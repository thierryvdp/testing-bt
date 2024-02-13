from openai import OpenAI
import speech_recognition as sr
import pyttsx3
import os

# Configurer une clé d'API valide
OpenAI.api_key = os.environ['OPENAI_API_KEY']
client = OpenAI()

# Initialiser le recognizer
r = sr.Recognizer()

# Initialiser le moteur de synthèse vocale
engine = pyttsx3.init()


#meta_prompt = "Act as an expert Prompt Engineer.\
#I'll give you an initial prompt.\
#Reason step by step to improve it.\
#Write the final prompt as an elegant template with clear sections.\
#Make sure you produce a ready-to-use prompt.\
#The final prompt must start with '###Improved Prompt###'" 
# the last instruction is about having a stable format that you can manipulate

meta_prompt = "Tu es un ingenieur expert dans l'écriture de prompt.\
Je vais te donner un premier prompt.\
Résonne étape par étape pour l'améliorer.\
Ecrit un prompt final dans un modèle élégant avec des sections claires.\
Soit sur de produire un prompt prêt à l'emploi.\
Le prompt final devra commencer par '###SuperPrompt###'" 
# the last instruction is about having a stable format that you can manipulate


while True:
    # Ecouter pour la commande vocale
    with sr.Microphone() as source:
        print("Ecoute...")
        audio = r.listen(source)

    # Transcrire la commande vocale
    try:
        prompt = r.recognize_google(audio, language="fr-FR")
        print("Tu as dit: " + prompt)
        if prompt == "exit":
            break
    except sr.UnknownValueError:
        print("Je n'ai pas compris")
        continue
    except sr.RequestError as e:
        print("Erreur de service; {0}".format(e))
        continue

    # Envoyer une requête à l'API de GPT
    print("Appel de l'API ...")
    stream = client.chat.completions.create(model="gpt-3.5-turbo-0613", max_tokens=1000, messages=[{"role": "system", "content": meta_prompt}, {"role": "user", "content": prompt}], stream=True, )
    response=''
    for chunk in stream:
        if chunk.choices[0].delta.content is not None:
            response = response + chunk.choices[0].delta.content
            # print(chunk.choices[0].delta.content, end="")

    print("Reponse Complete : "+response)
    # Find the starting index of the desired substring
    start_index = response.find("### Improved Prompt ###")

    # Check if the substring is found
    if start_index != -1:
       # Extract the substring from the starting index to the end
       new_prompt = response[start_index:]
    else:
       # Handle the case where the substring is not found
       new_prompt = prompt
       
    # Envoyer une requête à l'API de GPT
    print("Appelle API : "+prompt)
    stream = client.chat.completions.create(model="gpt-3.5-turbo-0613", max_tokens=1000, messages=[{"role": "system", "content": "Tu es un assistant très cool qui parle comme Jessie Pinkman de Breaking Bad en francais"}, {"role": "user", "content": new_prompt}], stream=True, )
    response=''
    for chunk in stream:
        if chunk.choices[0].delta.content is not None:
            response = response + chunk.choices[0].delta.content
            # print(chunk.choices[0].delta.content, end="")


    # Afficher la réponse
    # print(response["choices"][0]["text"])
    print("=======================================")
    print(response)
    print("=======================================")
    # engine.say(response["choices"][0]["text"])
    #engine.say(response)
    #engine.runAndWait()



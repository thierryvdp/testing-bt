from openai import OpenAI
import speech_recognition as sr
import pyttsx3
import os
from datetime import datetime

# Configurer une clé d'API valide
OpenAI.api_key = os.environ['OPENAI_API_KEY']
client = OpenAI()

# Initialiser le recognizer
r = sr.Recognizer()

# Initialiser le moteur de synthèse vocale
engine = pyttsx3.init()

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
        if prompt == "quitter":
            break
        if prompt == "sortir":
            break
        if prompt == "terminer":
            break
        if prompt == "fin":
            break
    except sr.UnknownValueError:
        print("Je n'ai pas compris")
        continue
    except sr.RequestError as e:
        print("Erreur de service; {0}".format(e))
        continue
    with open('d:/tmp/log/voice.log', 'a') as fichier:
        # Ajoute du texte à la fin du fichier
        fichier.write(datetime.now().strftime("%Y-%m-%d %H:%M:%S")+" Humain:\n")
        fichier.write(prompt+"\n")

    # Envoyer une requête à l'API de GPT
    stream = client.chat.completions.create(model="gpt-3.5-turbo-0613", messages=[{"role": "user", "content": prompt}], stream=True, )
    response=''
    for chunk in stream:
        if chunk.choices[0].delta.content is not None:
            response = response + chunk.choices[0].delta.content

    # Afficher la réponse
    print(response)
    with open('voice.log', 'a') as fichier:
        # Ajoute du texte à la fin du fichier
        fichier.write(datetime.now().strftime("%Y-%m-%d %H:%M:%S")+" IA:\n")
        fichier.write(response+"\n")
    engine.say(response)
    engine.runAndWait()

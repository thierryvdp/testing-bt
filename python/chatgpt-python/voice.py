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
    stream = client.chat.completions.create(model="gpt-3.5-turbo-0613", messages=[{"role": "user", "content": prompt}], stream=True, )
    response=''
    for chunk in stream:
        if chunk.choices[0].delta.content is not None:
            response = response + chunk.choices[0].delta.content
            # print(chunk.choices[0].delta.content, end="")

    

    # Afficher la réponse
    # print(response["choices"][0]["text"])
    print(response)
    # engine.say(response["choices"][0]["text"])
    engine.say(response)
    engine.runAndWait()

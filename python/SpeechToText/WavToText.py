import speech_recognition as sr

# Initialise le reconnaisseur
r = sr.Recognizer()

# Chemin vers votre fichier audio WAV
audio_file = "chemin/vers/votre/fichier.wav"

# Utilise le fichier audio comme source
with sr.AudioFile(audio_file) as source:
    # Enregistre l'audio dans une variable
    audio_data = r.record(source)
    # Essaie de reconnaître l'audio en utilisant Google Web Speech API
    try:
        text = r.recognize_google(audio_data, language='fr-FR') # Pour le français
        print("Texte transcrit : \n" + text)
    except sr.UnknownValueError:
        print("Google Web Speech API n'a pas pu comprendre l'audio")
    except sr.RequestError as e:
        print(f"Impossible de faire une demande à Google Web Speech API; {e}")

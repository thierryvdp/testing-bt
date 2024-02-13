import pyttsx3

# doc officielle
# https://buildmedia.readthedocs.org/media/pdf/pyttsx3/latest/pyttsx3.pdf
engine = pyttsx3.init()
voices = engine.getProperty('voices')
for voice in voices:
    print('voice:'+voice.id)
    engine.setProperty('voice', voice.id)
    engine.say('Le renard rapide saute au dessus du chien fou.')
    engine.say('The quick brown fox jumped over the lazy dog.')
    engine.runAndWait()

# more : pour ajouter d'autres voix
# https://puneet166.medium.com/how-to-added-more-speakers-and-voices-in-pyttsx3-offline-text-to-speech-812c83d14c13

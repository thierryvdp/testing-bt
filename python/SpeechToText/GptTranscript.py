from openai import OpenAI
import os
from datetime import datetime

# Configurer une clé d'API valide
OpenAI.api_key = os.environ['OPENAI_API_KEY']
client = OpenAI()

fichier_in='TAMALE.mp3'

mp3_folder='d:/tmp/Ghana/mp3'
txt_folder='d:/tmp/Ghana/txt'
fichier_mp3=mp3_folder+'/'+fichier_in
fichier_txt=txt_folder+'/'+fichier_in.replace('.mp3','.txt')
print('-----------------------------STARTING---------------------------------------------')
audio_file = open(fichier_mp3, "rb")
print('------>'+fichier_mp3)
transcript = client.audio.transcriptions.create(
  model="whisper-1",
  file=audio_file
)
print(transcript.text)
with open(fichier_txt, 'a') as fichier_out:
    # Ajoute du texte à la fin du fichier
    fichier_out.write(transcript.text)
print('-------------------------------DONE-----------------------------------------------')

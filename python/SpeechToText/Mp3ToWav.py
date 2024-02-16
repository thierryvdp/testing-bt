# pip install pydub
# pip install ffmpeg-python
# pip install SpeechRecognition
# Download ffmpeg file into your computer and install it.
import os
import speech_recognition as sr
from pydub import AudioSegment
AudioSegment.converter = "C:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe"
AudioSegment.ffmpeg = "C:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe"
AudioSegment.ffprobe ="C:\\Program Files\\ffmpeg\\bin\\ffprobe.exe"


# Spécifiez le chemin du répertoire dont vous souhaitez lister le contenu
chemin_repertoire_mp3 = 'd:/tmp/Ghana/mp3'
chemin_repertoire_wav = 'd:/tmp/Ghana/wav'

# Liste le contenu du répertoire
contenu = os.listdir(chemin_repertoire_mp3)

print("Contenu du répertoire :", chemin_repertoire_mp3)
for fichier_mp3 in contenu:
    fichier_wav = fichier_mp3.replace('.mp3','.wav')
    print(fichier_mp3+' to '+fichier_wav)
    
    audio = AudioSegment.from_mp3(chemin_repertoire_mp3+'/'+fichier_mp3)
    
    # Conversion de stéréo en mono
    audio_mono = audio.set_channels(1)

    # Modifier la fréquence d'échantillonnage à 8000 Hz et l'encodage à 8 bits
    audio_mono_8k = audio_mono.set_frame_rate(8000).set_sample_width(1)

    # audio.export(chemin_repertoire_wav+'/'+fichier_wav, format="wav")
    
    # Exportez en utilisant a-Law
    audio.export(chemin_repertoire_wav+'/'+fichier_wav, format="wav", parameters=["-acodec", "pcm_alaw"])



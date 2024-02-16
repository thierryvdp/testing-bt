import os
from pydub import AudioSegment
from math import ceil

# Chemin vers votre fichier WAV
chemin_repertoire_wav = 'd:/tmp/Ghana/wav'
chemin_repertoire_wav_decoupe = 'd:/tmp/Ghana/split_wav'
# Liste le contenu du répertoire
contenu = os.listdir(chemin_repertoire_wav)

print("Traitement du répertoire :", chemin_repertoire_wav)
for fichier_wav in contenu:
    # Charge le fichier WAV
    print(chemin_repertoire_wav+'/'+fichier_wav)
    audio = AudioSegment.from_wav(chemin_repertoire_wav+'/'+fichier_wav)

    # Durée de chaque morceau en millisecondes (50 secondes * 1000 millisecondes/secondes)
    duree_morceau = 50 * 1000

    # Calcule le nombre de morceaux
    nb_morceaux = ceil(len(audio) / duree_morceau)

    # Boucle pour découper et sauvegarder chaque morceau
    for i in range(nb_morceaux):
        debut = i * duree_morceau
        fin = debut + duree_morceau
        morceau = audio[debut:fin]
        # Sauvegarde le morceau dans un nouveau fichier
        nom_fichier_morceau = chemin_repertoire_wav_decoupe+'/'+fichier_wav.replace('.wav','') + f"_{i+1}.wav"
        morceau.export(nom_fichier_morceau, format="wav")
        print('\t'+nom_fichier_morceau)

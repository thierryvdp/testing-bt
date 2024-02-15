from pydub import AudioSegment
from math import ceil

# Chemin vers votre fichier WAV
fichier_wav = "chemin/vers/votre/fichier.wav"

# Charge le fichier WAV
audio = AudioSegment.from_wav(fichier_wav)

# Durée de chaque morceau en millisecondes (10 secondes * 1000 millisecondes/secondes)
duree_morceau = 10 * 1000

# Calcule le nombre de morceaux
nb_morceaux = ceil(len(audio) / duree_morceau)

# Boucle pour découper et sauvegarder chaque morceau
for i in range(nb_morceaux):
    debut = i * duree_morceau
    fin = debut + duree_morceau
    morceau = audio[debut:fin]
    # Sauvegarde le morceau dans un nouveau fichier
    nom_fichier_morceau = f"morceau_{i+1}.wav"
    morceau.export(nom_fichier_morceau, format="wav")
    print(f"Morceau sauvegardé: {nom_fichier_morceau}")

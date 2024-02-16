from pydub import AudioSegment
# pip install numpy
import numpy as np
# pip install scipy
from scipy.signal import butter, lfilter

# marche pas, son horrible

def butter_lowpass(cutoff, fs, order=5):
    nyq = 0.5 * fs
    normal_cutoff = cutoff / nyq
    b, a = butter(order, normal_cutoff, btype='low', analog=False)
    return b, a

def butter_lowpass_filter(data, cutoff, fs, order=5):
    b, a = butter_lowpass(cutoff, fs, order=order)
    y = lfilter(b, a, data)
    return y

chemin_repertoire_wav_filter = 'd:/tmp/Ghana/filter_wav'
chemin_repertoire_wav_decoupe = 'd:/tmp/Ghana/split_wav'

# Charger le fichier WAV
audio = AudioSegment.from_file(chemin_repertoire_wav_decoupe+"/1voyage_1.wav")

# Convertir l'audio en échantillons numériques
samples = np.array(audio.get_array_of_samples())

# Paramètres du filtre
fs = audio.frame_rate  # Fréquence d'échantillonnage
cutoff = 4000  # Fréquence de coupure du filtre passe-bas

# Appliquer le filtre passe-bas
filtered_samples = butter_lowpass_filter(samples, cutoff, fs)

# Convertir les échantillons filtrés en audio
filtered_audio = AudioSegment(filtered_samples.tobytes(), frame_rate=fs, sample_width=audio.sample_width, channels=audio.channels)

# Sauvegarder l'audio filtré
filtered_audio.export(chemin_repertoire_wav_filter+"/1voyage_1.wav", format="wav")



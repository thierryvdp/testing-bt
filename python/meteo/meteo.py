import requests
import json
import numpy as np
import csv
import pandas as pd

# URL de l'API
target_date = "20230409"
url = "https://api.weather.com/v2/pws/history/hourly?stationId=ILASAL49&format=json&units=m&apiKey=85ee1fc2d76c4cecae1fc2d76cfcec17&date="+target_date

# Faire une requête pour obtenir les données JSON
response = requests.get(url)
# Convertir la chaîne JSON en dictionnaire Python data = json.loads(json_str)
data = response.json()



# Examiner la structure des données pour extraire les informations pertinentes
# Supposons que nous voulons extraire les informations sur la température (temp)
observations = data.get('observations', [])
observations_keys = observations[0].keys()
metric_keys = observations[0].get('metric').keys()

entete = []
for key in observations_keys:
    if key != 'metric':
        entete.append(key)
for key in metric_keys:
    entete.append(key)

with open('/Users/thierry/Documents/git/testing-bt/python/meteo/data/data'+target_date+'.csv', 'w') as fichier_csv:
    # Créer un objet writer (écriture) avec ce fichier
    writer = csv.writer(fichier_csv, delimiter=',')
    # entete
    writer.writerow(entete)
    # Parcourir les observations
    for observation in observations:
        ligne = []
        for key in observations_keys:
            if key != 'metric':
                ligne.append(observation.get(key))
        for key in metric_keys:
            ligne.append(observation.get('metric').get(key))
        writer.writerow(ligne)

with open('/Users/thierry/Documents/git/testing-bt/python/meteo/data/data'+target_date+'.csv', 'r') as fichier_csv:
    meteo = pd.read_csv(fichier_csv, sep=',', engine='python', encoding='utf-8')
print(meteo.shape)
print(meteo.columns)

# Extraire les données pertinentes et les stocker dans une liste
# temp_list = []

# for observation in observations:
#     temp_list.append(observation)

# Convertir la liste en tableau NumPy
# temp_array = np.array(temp_list)

# Afficher le tableau NumPy
# print(temp_array)

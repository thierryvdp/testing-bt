import requests
import json
import numpy as np
import csv
import pandas as pd
import time
from datetime import date, timedelta

def charge_data(target_date)
    url = url_base+target_date
    
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
    
    
    with open(chemin+'data'+target_date+'.csv', 'w') as fichier_csv:
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


#    chemin="/dev-tools/gitlab-wrk/testing-bt/python/meteo/data/"
chemin="/tmp/data/"
# URL de l'API
target_date = "20230409"
station="ILESOU15"
apiKey="85ee1fc2d76c4cecae1fc2d76cfcec17"
url_base = "https://api.weather.com/v2/pws/history/hourly?stationId="+station+"&format=json&units=m&apiKey="+apiKey+"&date="


# boucle du 21/02/2023 au 17/05/2024
date_debut = date(2023, 2, 21)
date_fin = date(2023, 2, 24)
#date_fin = date(2024, 5, 17)
date_courante = date_debut
while date_courante <= date_fin:
    datalpha = print(date_courante.strftime("%Y%m%d"))
    print datalpha
    charge_data(datalpha)
    time.sleep(1)
    date_courante += timedelta(days=1)


#with open(chemin+'data'+target_date+'.csv', 'r') as fichier_csv:
#    meteo = pd.read_csv(fichier_csv, sep=',', engine='python', encoding='utf-8')
#print(meteo.shape)
#print(meteo.columns)

# Extraire les données pertinentes et les stocker dans une liste
# temp_list = []

# for observation in observations:
#     temp_list.append(observation)

# Convertir la liste en tableau NumPy
# temp_array = np.array(temp_list)

# Afficher le tableau NumPy
# print(temp_array)

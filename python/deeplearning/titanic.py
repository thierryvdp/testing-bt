import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

# charger une feuille excel
data = pd.read_excel('deeplearning/datasets/titanic.xls')
# format des données
print(data.shape)
# noms des colonnes
print(data.columns)

# eliminer des colonnes
data = data.drop(['name', 'sibsp', 'parch', 'ticket', 'fare', 'cabin', 'embarked', 'boat', 'body', 'home.dest'], axis=1)

# statistiques de base pour chaque colonne
print(data.describe())

# il manque des données pour l'age, on peux les remplacer
# mais ça corrompe le dataset ...
# data.fillna(data['age'].mean)

# il manque des données pour l'age, on peux eliminer les lignes
print(data.shape)
data = data.dropna(axis=0)
print(data.shape)
# du coup les stats vont changer
print(data.describe())

# regroupement
print(data['pclass'].value_counts())
data['pclass'].value_counts().plot.bar() # ca utilise matplotlib
# alors faut show()
plt.show()

# regroupement
print(data.groupby(['sex']).mean())
print(data.groupby(['sex','pclass']).mean())

data = data.set_index('name')
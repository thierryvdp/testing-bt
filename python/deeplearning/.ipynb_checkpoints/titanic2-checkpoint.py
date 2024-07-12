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
data = data.drop(['sibsp', 'parch', 'ticket', 'fare', 'cabin', 'embarked', 'boat', 'body', 'home.dest'], axis=1)

# utiliser le nom comme index au lieu des numeros
data = data.set_index('name')
print(data.head())

# data['age'] donne la serie
# 0:10 donne indexing dans la serie
#data['age'][0:10]

# selection des mineurs
#data = data[ data['age'] <18 ]
#print(data)

#print(data[data['age'] < 18]['pclass'].value_counts())
# stats regroupement pour les mineurs
#print(data[data['age'] < 18].groupby(['sex','pclass']).mean(numeric_only=True))

#print(data['age'].value_counts())
# modifier colonne age pour avoir 4 catégories: age<20 20>age>30 30>age>40  age>40
#data.loc[data['age'] <= 20, 'age']=0
#data.loc[ (data['age'] > 20) & (data['age'] <= 30), 'age']=1
#data.loc[ (data['age'] > 30) & (data['age'] <= 40), 'age']=2
#data.loc[data['age'] > 40, 'age']=3
#print(data.head())
#print(data['age'].value_counts())

def category_ages(age):
    if age <= 20:
        return '<20 ans'
    elif (age > 20) & (age <= 30):
        return '20-30 ans'
    elif (age > 30) & (age <= 40):
        return '30-40 ans'
    else:
        return '40+ ans'

data2=data['age'].map(category_ages)
print(data2)

# data['sex'].map({'male':0, 'female':1})
# data['sex'].replace(['male', 'female'],[0, 1])
# data['sex'].astype('category').cat.codes


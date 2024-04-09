import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.linear_model import LinearRegression
from sklearn.neighbors import KNeighborsClassifier


def survie(model, pclass=3, sex=0, age=26):
    X=np.array([pclass, sex, age]).reshape(1,3)
    print('prediction pour un voyageur en classe ', pclass, ' sexe ', sex, ' age ', age, ' prediction ',model.predict(X))
    print('probabilité ',model.predict_proba(X))

# charger une feuille excel
titanic = pd.read_excel('deeplearning/datasets/titanic.xls')
# format des données
print(titanic.shape)
# noms des colonnes
print(titanic.columns)


# garder que les colonnes qui nous interresse
titanic = titanic[['survived','pclass','sex','age']]
# virer les lignes sans datas
titanic.dropna(axis=0, inplace=True)
# remplacer les datas alpha par des chiffres
titanic['sex'].replace(['male', 'female'],[0, 1], inplace=True)
print(titanic.head())

y = titanic['survived']
X=titanic.drop('survived', axis=1)

model = KNeighborsClassifier(n_neighbors=5)
model.fit(X,y)
print('precision du modèle ',model.score(X,y))
survie(model,pclass=1,sex=0,age=60)

best_neighbor=0
best_score=0
neighbors=[]
scores=[]

for i in range(1,10):
    model = KNeighborsClassifier(n_neighbors=i)
    model.fit(X,y)
    score=model.score(X,y)
    neighbors.append(i)
    scores.append(score)
    if score > best_score :
        best_score = score
        best_neighbor = i

plt.plot(neighbors,scores,label='scores')
plt.show()

print('best_neighbor ',best_neighbor)
print('best_score ',best_score)

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

# exercice 1 decouverte du meilleur modele
# best_neighbor=0
# best_score=0
# neighbors=[]
# scores=[]

# for i in range(1,10):
#     model = KNeighborsClassifier(n_neighbors=i)
#     model.fit(X,y)
#     score=model.score(X,y)
#     neighbors.append(i)
#     scores.append(score)
#     if score > best_score :
#         best_score = score
#         best_neighbor = i

# plt.plot(neighbors,scores,label='scores')
# plt.show()

# print('best_neighbor ',best_neighbor)
# print('best_score ',best_score)

from sklearn.model_selection import train_test_split
from sklearn.model_selection import GridSearchCV
from sklearn.model_selection import learning_curve

# exercice 2 decouverte du meilleur modele
# solution avec validation curve
# crér un train_set et un test set
# constitution des échantillon de test et de validation
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=5)
print(X_train.shape)
print(X_test.shape)

# avec GridSearchCV trouver les meilleurs params n_neighbors metrics weights
# dictionaire des parametres avec leur variation
parametres = {'n_neighbors':np.arange(1, 20), 'metric':['euclidean', 'manhattan']}
# fabrication de la grille et modele de test
grid = GridSearchCV(KNeighborsClassifier(), param_grid=parametres, cv=5)
# entrainement du modele
grid.fit(X_train,y_train)
# score du meilleur modele
print(grid.best_score_)
# parametres du meilleur modele
print(grid.best_params_)
# meilleur modele
model=grid.best_estimator_
print(model.score(X_test,y_test))

# est-ce que collecter plus de données serait utile ?
N, train_score, val_score = learning_curve(model, X_train, y_train, train_sizes = np.linspace(0.1, 1, 10), cv=5)
print(N)
plt.plot(N, train_score.mean(axis=1), label='train')
plt.plot(N, val_score.mean(axis=1), label='validation')
plt.xlabel('train_size')
plt.legend()
plt.show()


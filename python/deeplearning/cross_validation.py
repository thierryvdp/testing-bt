import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import load_iris
from sklearn.neighbors import KNeighborsClassifier

from sklearn.model_selection import train_test_split
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import validation_curve
from sklearn.model_selection import GridSearchCV


# chargement des données
iris=load_iris()
X=iris.data
y=iris.target

# constitution des échantillon de test et de validation
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=5)
print(X_train.shape)
print(X_test.shape)

# visualisation des données
# plt.figure(figsize=(12,4))

# plt.subplot(121)
# plt.scatter(X_train[:,0], X_train[:,1], c=y_train, alpha=0.8)
# plt.title('train set')

# plt.subplot(122)
# plt.scatter(X_test[:,0], X_test[:,1], c=y_test, alpha=0.8)
# plt.title('test set')

# entrainement et test du modele
# model = KNeighborsClassifier(n_neighbors=6)
# model.fit(X_train,y_train)
# print(model.score(X_test,y_test))

# cross validation technique
# val_score=[]
# for k in range(1, 50):
#     score=cross_val_score(KNeighborsClassifier(k), X_train, y_train, cv=5, scoring='accuracy').mean()
#     val_score.append(score)

# plt.plot(val_score)
# plt.title('validation score')


# validation curve
model = KNeighborsClassifier()
k = np.arange(1, 50)
train_score, val_score = validation_curve(model, X_train, y_train, param_name='n_neighbors', param_range=k, cv=5)



print('val_score.shape:',val_score.shape)
print('val_score:',val_score)
print('val_score.mean():',val_score.mean(axis=1))

plt.plot(k,val_score.mean(axis=1), label='validation')
plt.plot(k,train_score.mean(axis=1), label='train')
plt.ylabel('score')
plt.xlabel('n_neighbors')
plt.legend()


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

plt.show()




from sklearn.model_selection import KFold, LeaveOneOut
cv = KFold(5, random_state=0)
print(cross_val_score(KNeighborsClassifier, X, y, cv=cv))

from sklearn.model_selection import SuffleSplit
cv = SuffleSplit(4, test_size=0.2) # nb iterations, taille paquet 20%
print(cross_val_score(KNeighborsClassifier, X, y, cv=cv))




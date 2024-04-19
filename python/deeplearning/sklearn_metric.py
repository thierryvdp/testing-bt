# import numpy as np
# import matplotlib.pyplot as plt
# from sklearn.metrics import *

# y = np.array([1])
# y_pred = np.array([1])
# print('MAE:',mean_absolute_error(y, y_pred))
# print('MSE',mean_squared_error(y, y_pred))

# from sklearn.preprocessing import LabelEncoder
# y = np.array(['chat','chien','chat','oiseau'])
# encoder = LabelEncoder()
# encoder.fit(y)
# print(encoder.classes_)
# print(encoder.transform(y))
# print(encoder.inverse_transform(np.array([1,2,0,0])))

# from sklearn.preprocessing import OrdinalEncoder
# X = np.array([['Chat','Poils'],
#              ['Chien','Poils'],
#              ['Chat','Poils'],
#              ['Oiseau','Plumes']])
# encoder=OrdinalEncoder()
# encoder.fit(X)
# print(encoder.categories_)

# from sklearn.preprocessing import LabelBinarizer
# y = np.array(['chat','chien','chat','oiseau'])
# # sparse_output=True pour compresser le stokage des données de matrice binaire
# encoder = LabelBinarizer(sparse_output=True)
# print(encoder.fit_transform(y))

# from sklearn.preprocessing import MinMaxScaler
# X = np.array([[70],[80],[120]])
# scaler = MinMaxScaler()
# print(scaler.fit_transform(X))
# X_test = np.array([[90]])
# print(scaler.transform(X_test))

# from sklearn.datasets import load_iris
# iris = load_iris()
# X = iris.data
# X_minmax = MinMaxScaler().fit_transform(X)
# plt.scatter(X[:,2],X[:,3],c='blue')
# plt.scatter(X_minmax[:,2],X_minmax[:,3],c='orange')
# plt.legend()
# plt.show()

# from sklearn.preprocessing import StandardScaler
# X = np.array([[70],[80],[120]])
# scaler = StandardScaler()
# print(scaler.fit_transform(X))
# [[-0.9258201 ] [-0.46291005] [ 1.38873015]]

# from sklearn.preprocessing import RobustScaler
# X = np.array([[70],[80],[120]])
# scaler = RobustScaler()
# print(scaler.fit_transform(X))
# [[-0.4]  [ 0. ] [ 1.6]]

# from sklearn.pipeline import make_pipeline
# from sklearn.preprocessing import StandardScaler
# from sklearn.linear_model import SGDClassifier
# from sklearn.datasets import load_iris
# from sklearn.model_selection import train_test_split
# # chargement des données
# iris=load_iris()
# X=iris.data
# y=iris.target
# # constitution des échantillon de test et de validation
# X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=5)
# print(X_train.shape)
# print(X_test.shape)
# # pipeline
# model = make_pipeline(StandardScaler(), SGDClassifier())
# model.fit(X_train, y_train)
# print(model.predict(X_test))

from sklearn.datasets import load_iris
from sklearn.model_selection import train_test_split
from sklearn.pipeline import make_pipeline
from sklearn.model_selection import GridSearchCV
from sklearn.preprocessing import PolynomialFeatures
from sklearn.preprocessing import StandardScaler
from sklearn.linear_model import SGDClassifier
# chargement des données
iris=load_iris()
X=iris.data
y=iris.target
# constitution des échantillon de test et de validation
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=5)
# pipeline
model = make_pipeline(PolynomialFeatures(), StandardScaler(), SGDClassifier(random_state=0))
print(model)
params = {
    'polynomialfeatures__degree' : [2, 3, 4],
    'sgdclassifier__penalty' : ['l1', 'l2']
}
grid = GridSearchCV(model, param_grid=params, cv=4)
grid.fit(X_train, y_train)
print(grid.best_params_)
print(grid.score(X_test,y_test))

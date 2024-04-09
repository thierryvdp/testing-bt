import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import load_iris
from sklearn.neighbors import KNeighborsClassifier

from sklearn.model_selection import train_test_split


iris=load_iris()
X=iris.data
y=iris.target

X_tra


print(X.shape)
plt.scatter(X[:,0], X[:,1], c=y, alpha=0.8)
plt.show()

# model = KNeighborsClassifier(n_neighbors=6)
# model.fit(X_train, y_train)

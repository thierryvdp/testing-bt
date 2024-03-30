from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np
from sklearn.datasets import load_iris

iris=load_iris()
x=iris.data # pour chaque observation (ligne) on a 4 colonnes
                   # longueur pétale
                   # largeur pétale
                   # longueur sépale
                   # largeur sépale
y=iris.target # quelle classe qui correspond 0 1 2
names = list(iris.target_names)

print(f'x contient {x.shape[0]} exemples et {x.shape[1]} variables')
print(f'il y a {np.unique(y).size} classes')

# Dans ce cas on est obligé d’utiliser la programmation orienté objet POO de matplotlib.
ax = plt.axes(projection='3d')
ax.scatter(x[:, 0], x[:, 1], x[:, 2], c=y)
plt.show()

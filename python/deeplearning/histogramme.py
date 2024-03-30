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

# hist(dataset, bins=nombres de barres)
# plt.hist(x[:, 0], bins=30)
# plt.hist(x[:, 1], bins=30)

plt.hist2d(x[:, 0], x[:, 1], cmap='Blues')
plt.xlabel('long sepal')
plt.ylabel('larg sepal')
plt.colorbar()
plt.show()


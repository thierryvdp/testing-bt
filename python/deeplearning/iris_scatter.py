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

# on prend en x toutes les lignes et la colonne 0
# on prend en y toutes les lignes et la colonne 1
plt.scatter(x[:, 0], x[:, 1], c=y, alpha=0.5, s=10) # alpha=transparence
                                                    # s=taille point
plt.xlabel('long sépal')
plt.ylabel('larg sépal')
plt.show()


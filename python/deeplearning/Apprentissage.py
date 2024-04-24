from sklearn.datasets import make_blobs
from sklearn.cluster import KMeans
import numpy as np
import matplotlib.pyplot as plt
X, y = make_blobs(n_samples=100, centers=3, cluster_std=0.4, random_state=0)


# model = KMeans(n_clusters=3, n_init=10, max_iter=200, init='K-Mean++')
# K-Mean++ pour initialiser les centroids sur des points du dataset très éloignés les uns des autres
model = KMeans(n_clusters=3)
print(model.fit(X))
print(model.predict(X))
# affichage des données en coloriant suivant le cluster d'appartenance
plt.scatter(X[:,0], X[:,1], c=model.predict(X))
# affichage du centre du cluster
print('centres',model.cluster_centers_)
plt.scatter(model.cluster_centers_[:,0], model.cluster_centers_[:,1], c='r')
print(model.score(X))
print(model.inertia_)
plt.show()

inertia = []
K_range = range(1, 20)
for k in K_range:
    model = KMeans(n_clusters=k).fit(X)
    inertia.append(model.inertia_)

plt.plot(K_range, inertia)
plt.xlabel('nombre de clusters')
plt.ylabel('Cout du modele (Inertia)')


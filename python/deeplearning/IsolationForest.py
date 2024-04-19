from sklearn.datasets import make_blobs
import numpy as np
import matplotlib.pyplot as plt
from sklearn.ensemble import IsolationForest  

X, y = make_blobs(n_samples=50, centers=1, cluster_std=0.1, random_state=0)
X[-1,:] = np.array([2.25, 5])

model = IsolationForest(contamination=0.01)
print(model.fit(X))

plt.scatter(X[:,0], X[:, 1], c=model.predict(X))
plt.show()

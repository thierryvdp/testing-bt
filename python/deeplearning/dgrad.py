import numpy as np
import matplotlib.pyplot as plt
# pip install scikit-learn
from sklearn.datasets import make_blobs
from sklearn.metrics import accuracy_score
import utilities as ut


X, y = make_blobs(n_samples=100, n_features=2, centers=2, random_state=0)
y = y.reshape((y.shape[0], 1))

print('dimensions de X:', X.shape)
print('dimensions de y:', y.shape)
print(X)
print(y)

W, b = ut.artificial_neuron(X, y)


new_plant = np.array([2, 1])

x0 = np.linspace(-1, 4, 100)
x1 = (-W[0] * x0 -b) / W[1]

plt.scatter(X[:,0], X[:,1], c=y, cmap='summer')
plt.scatter(new_plant[0], new_plant[1], c='r')
plt.plot(x0, x1, c='orange', lw=3)
plt.show()

ut.predict(new_plant, W, b)


import matplotlib.pyplot as plt
import utilities as ut
from sklearn.datasets import make_blobs



X, y = make_blobs(n_samples=100, n_features=2, centers=2, random_state=0)
y = y.reshape((y.shape[0], 1))
print('dimension X:', X.shape)
print('dimension y:', y.shape)

# plt.scatter(X[:,0], X[:,1],c=y,cmap='summer')
# plt.show()

poids, biais, loss_list = ut.artificial_neuron(X, y)
plt.plot(loss_list)
plt.show()


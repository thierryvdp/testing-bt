import matplotlib.pyplot as plt
from sklearn.ensemble import IsolationForest  
from sklearn.datasets import load_digits

digits = load_digits()
images = digits.images
X = digits.data # shape (1797, 64) 1797 images de 64 bits
y = digits.target


from sklearn.decomposition import PCA

model = PCA(n_components=2)
model.fit(X)

x_pca = model.transform(X)
plt.scatter(x_pca[:,0], x_pca[:,1], c=y)
plt.colorbar()

# plt.figure()
# plt.xlim(-30, 30)
# plt.ylim(-30, 30)

# for i in range(100):
#     plt.text(x_pca[i,0], x_pca[i,1], str(y[i]))

plt.show()

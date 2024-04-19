import matplotlib.pyplot as plt
from sklearn.ensemble import IsolationForest  
from sklearn.datasets import load_digits

digits = load_digits()
images = digits.images
X = digits.data # shape (1797, 64) 1797 images de 64 bits
y = digits.target

model = IsolationForest(random_state=0, contamination=0.02)
model.fit(X)
outliers = model.predict(X) == -1 # ce sont les erreurs à filtrer

plt.figure(figsize=(12, 3))
for i in range(10):
  plt.subplot(1, 10, i+1)
  plt.imshow(images[outliers][i])
  plt.title(y[outliers][i])
plt.show()

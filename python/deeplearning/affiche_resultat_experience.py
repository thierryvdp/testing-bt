import matplotlib.pyplot as plt
import numpy as np

def graphique(data):
    n = len(data)
    plt.figure(figsize=(12, 8))
    for k, i in zip(data.keys(), range(1, n+1)):
        plt.subplot(n, 1, i)
        plt.plot(data[k])
        plt.title(k)
    plt.show()

# dico d'experiences
dataset = {f"experience {i}": np.random.randn(100) for i in range (4)}
graphique(dataset)

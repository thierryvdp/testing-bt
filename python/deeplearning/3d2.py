from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np

f = lambda x, y: np.sin(x) + np.cos(x+y)
X = np.linspace(0, 5, 100)
Y = np.linspace(0, 5, 100)
X, Y = np.meshgrid(X, Y)
Z = f(X, Y)

# ax = plt.axes(projection='3d')
# ax.plot_surface(X, Y, Z, cmap='plasma')

# courbe de niveau de la surface 3D avec 20 niveaux et en noir
# plt.contour(X, Y, Z, 20, colors='black')
plt.contourf(X, Y, Z, 20, cmap='RdGy')
plt.colorbar()
plt.show()

from scipy import signal
import numpy as np
import matplotlib.pyplot as plt


x = np.linspace(0, 20, 100)
y = x + 4*np.sin(x) + np.random.randn(x.shape[0])
plt.plot(x, y)

# eliminer la tendance lineaire de la courbe
new_y = signal.detrend(y)
plt.plot(x, new_y)

plt.show()

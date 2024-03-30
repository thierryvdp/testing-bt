from scipy import optimize
import matplotlib.pyplot as plt
import numpy as np

# f(x) = ax3 + bx2 + cx + d
def f(x):
    return x**2 + 15*np.sin(x)    

x = np.linspace(-10, 10, 100)
plt.plot(x, f(x), lw=3, zorder=-1)

x0=-5
result=optimize.minimize(f, x0=x0).x
plt.scatter(result, f(result), s=100, c='r', zorder=1)
plt.scatter(x0, f(x0), s=200, marker='+', c='g', zorder=1)


plt.show()
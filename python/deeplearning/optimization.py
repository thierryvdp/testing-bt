from scipy import optimize
import matplotlib.pyplot as plt
import numpy as np

# f(x) = ax3 + bx2 + cx + d
def f(x, a, b, c, d):
    return a*x**3 + b*x**2 + c*x + d    

x = np.linspace(0, 2, 100)
y = 1/3*x**3 - 3/5 * x**2 + 2 + np.random.randn(x.shape[0])/20
plt.scatter(x, y, s=10)

params, param_covariance = optimize.curve_fit(f, x, y)
print('parametres\n', params)
print('covariance des parametres\n',param_covariance)

plt.plot(x, f(x, params[0], params[1], params[2], params[3]))

plt.show()
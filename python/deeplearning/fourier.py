from scipy import fftpack
import numpy as np
import matplotlib.pyplot as plt

x = np.linspace(0, 30, 1000)
y = 3*np.sin(x) + 2*np.sin(5*x)+np.sin(10*x)
# plt.plot(x, y)

# fourier
fourier = fftpack.fft(y)
freq = fftpack.fftfreq(y.size)
power = np.abs(fourier)
plt.plot(np.abs(freq),power)

plt.show()

from scipy import fftpack
import numpy as np
import matplotlib.pyplot as plt

x = np.linspace(0, 30, 1000)
y = 3*np.sin(x) + 2*np.sin(5*x)+np.sin(10*x)+np.random.random(x.shape[0])*10
# plt.plot(x, y)

# fourier: spectre
fourier = fftpack.fft(y)
freq = fftpack.fftfreq(y.size)
power = np.abs(fourier)
# plt.plot(np.abs(freq),power)

# fourier: filtrage du spectre
fourier[power<400] = 0
# plt.plot(np.abs(freq),np.abs(fourier))

# fourier inverse
filtered_signal = fftpack.ifft(fourier)
plt.figure(figsize=(12, 8))
plt.plot(x, y, lw=0.5)
plt.plot(x, filtered_signal, lw=3)

plt.show()
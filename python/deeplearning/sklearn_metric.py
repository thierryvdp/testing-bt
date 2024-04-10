import numpy as np
import matplotlib.pyplot as plt
from sklearn.metrics import *

y = np.array([1])
y_pred = np.array([1])

print('MAE:',mean_absolute_error(y, y_pred))
print('MSE',mean_squared_error(y, y_pred))


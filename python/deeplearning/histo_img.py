import matplotlib.pyplot as plt
import numpy as np
from scipy import misc
face = misc.face(gray=True)
# plt.imshow(face, cmap='gray')
# repartition des different niveaux de gris dans l'image
plt.hist(face.ravel(),bins=255)
plt.show()

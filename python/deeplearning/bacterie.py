import matplotlib.pyplot as plt
import os
import numpy as np
from scipy import ndimage

print('====================================================')
print(os.getcwd())


image = plt.imread('deeplearning/bacterie.png')
print(image.shape)
image = image[:,:,0]
print(image.shape)
#plt.imshow(image, cmap='gray')

# on fait une copie de l'image
image_2 = np.copy(image)
# l'histogramme permet de savoir quelle couleur nous interesse
# plt.hist(image_2.ravel(), bins=255)

# on generer une matrice vrai/faux pour identifier mes pixelsinterressants
image = image < 0.6
# on filtre un peu les bruits eventuels
open_x = ndimage.binary_opening(image)

# on identifie des zones en leur mettant un label
label_image, n_labels = ndimage.label(open_x)
print(n_labels)

# on peut compter les pixels de chaque zone identifiée
sizes = ndimage.sum(open_x, label_image, range(n_labels))

plt.scatter(range(n_labels), sizes, c='orange')
plt.show()



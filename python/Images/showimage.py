# pip install Pillow
from PIL import Image

# Chemin vers votre fichier image
chemin_image = 'F:/PHOTO/DCIM_Thierry/201706__/IMG_0001.JPG'

# Ouvrir l'image
image = Image.open(chemin_image)

# Afficher l'image
image.show()

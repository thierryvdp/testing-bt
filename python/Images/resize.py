from PIL import Image
import os

def resize_with_border(input_file,input_filedir, output_filedir, final_size=(1125, 1125)):
    
    input_image_path=input_filedir+'/'+input_file
    output_image_path=output_filedir+'/'+input_file
    
    # Charger l'image
    image = Image.open(input_image_path)
    
    # Redimensionner l'image originale
    image = image.resize(final_size)  
    image.save(output_image_path)
   

# Exemple d'utilisation

# Chemin vers votre repertoire
chemin_repertoire_in = 'D:/tmp/images/sophie-jeux2/original'
chemin_repertoire_out = 'D:/tmp/images/sophie-jeux2/resize'
final_size=(1125, 1125)
# Liste le contenu du répertoire
contenu_in = os.listdir(chemin_repertoire_in)

print("Traitement du répertoire :", chemin_repertoire_in)
for fichier_in in contenu_in:
    resize_with_border(fichier_in, chemin_repertoire_in, chemin_repertoire_out, final_size)

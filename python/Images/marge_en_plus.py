from PIL import Image
import os

def resize_with_border(input_file,input_filedir, output_filedir, final_size=(1125, 1125)):
    
    input_image_path=input_filedir+'/'+input_file
    output_image_path=output_filedir+'/'+input_file
    
    # Charger l'image
    image = Image.open(input_image_path)
    
    # Calculer la nouvelle taille de l'image pour qu'elle soit centrée dans le carré final
    original_width, original_height = image.size
    #ratio = min(final_size[0] / original_width, final_size[1] / original_height)
    #new_width = int(original_width * ratio)
    #new_height = int(original_height * ratio)
    
    # Redimensionner l'image originale (conserve le ratio)
    #image = image.resize((new_width, new_height), Image.ANTIALIAS)
    
    # Créer une nouvelle image avec un fond blanc
    new_image = Image.new("RGB", final_size, "white")
    
    # Calculer les positions pour centrer l'image originale dans le fond
    #top_left_x = (final_size[0] - new_width) // 2
    #top_left_y = (final_size[1] - new_height) // 2

    top_left_x = (final_size[0] - original_width) // 2
    top_left_y = (final_size[1] - original_height) // 2
    
    # Coller l'image originale redimensionnée sur le fond blanc
    new_image.paste(image, (top_left_x, top_left_y))
    
    # Sauvegarder la nouvelle image
    new_image.save(output_image_path)

# Exemple d'utilisation

# Chemin vers votre repertoire
chemin_repertoire_in = 'D:/tmp/images/sophie-jeux1/original'
chemin_repertoire_out = 'D:/tmp/images/sophie-jeux1/modifie'
final_size=(1125, 1125)
# Liste le contenu du répertoire
contenu_in = os.listdir(chemin_repertoire_in)

print("Traitement du répertoire :", chemin_repertoire_in)
for fichier_in in contenu_in:
    resize_with_border(fichier_in, chemin_repertoire_in, chemin_repertoire_out, final_size)

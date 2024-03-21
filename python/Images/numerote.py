from PIL import Image, ImageDraw, ImageFont
import os

def add_text_to_image(input_file, input_filedir, output_filedir, text, font_path, font_size, position, color, back):
    
    image_path=input_filedir+'/'+input_file
    output_path=output_filedir+'/'+input_file
    
    # Charger l'image
    image = Image.open(image_path)
    draw = ImageDraw.Draw(image)

    # Charger la police
    font = ImageFont.truetype(font_path, font_size)
    font2 = ImageFont.truetype(font_path, font_size+2)
    
    # Calculer la position du texte en bas à gauche
    # text_width, text_height = draw.textsize(text, font=font)
    x = position[0]
    y = image.height - position[1]

    # Ajouter le texte à l'image
    draw.text((x-1, y-1), text, fill=back, font=font2)
    draw.text((x, y), text, fill=color, font=font)

    # Sauvegarder l'image modifiée
    image.save(output_path)

# Chemin vers votre repertoire
chemin_repertoire_in = 'D:/tmp/images/sophie-jeux2/resize'
chemin_repertoire_out = 'D:/tmp/images/sophie-jeux2/numerote'
# Liste le contenu du répertoire
contenu_in = os.listdir(chemin_repertoire_in)

# police
font_path = 'C:/Windows/Fonts/FRSCRIPT.TTF'  # Ajustez ce chemin
font_size = 150
position = (50, 200)  # Décalage depuis le bas gauche
color = 'white'  # Couleur du texte
back = 'black'  # Couleur du texte

i=0

print("Traitement du répertoire :", chemin_repertoire_in)
for fichier_in in contenu_in:
    i=i+1
    add_text_to_image(fichier_in, chemin_repertoire_in, chemin_repertoire_out, str(i), font_path, font_size, position, color, back)

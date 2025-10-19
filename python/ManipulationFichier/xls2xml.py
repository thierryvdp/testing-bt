import openpyxl
import xml.etree.ElementTree as ET

chemin_repertoire = 'c:/Users/Thierry/Google Drive/Maison/PoleEmploi/2025/'


def xlsx_to_simple_xml(input_file, output_file):
    # Charger le fichier Excel
    workbook = openpyxl.load_workbook(input_file, data_only=True)
    root = ET.Element("workbook")

    # Parcourir chaque feuille
    for sheet in workbook.worksheets:
        sheet_elem = ET.SubElement(root, "sheet", name=sheet.title)

        for row in sheet.iter_rows(values_only=True):
            row_elem = ET.SubElement(sheet_elem, "row")
            for cell in row:
                cell_text = "" if cell is None else str(cell)
                ET.SubElement(row_elem, "cell").text = cell_text

    # Créer l'arbre XML
    tree = ET.ElementTree(root)
    tree.write(output_file, encoding="utf-8", xml_declaration=True)

# Exemple d'utilisation
xlsx_to_simple_xml(chemin_repertoire+"TriParPoste.xlsx", chemin_repertoire+"TriParPoste.xml")

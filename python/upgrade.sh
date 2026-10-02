#!/bin/bash
# List all installed packages
echo -e "Appuyez sur [Enter] pour calculer la liste des Packages à mettre à jour\n"
read -e
echo -e "Please Wait\n"
packages=$(pip3 list --outdated| cut -d " " -f 1)
echo -e "Liste calculée\n"
echo -e "Appuyez sur [Enter] pour continuer\n"
read -e
# Upgrade each package
for package in $packages
do
# script end
echo -e "=====================================\n"
echo -e "====== PACKAGE ["
echo -e $package
echo -e "] ======\n=====================================\n"
    pip3 install --upgrade $package
# script end
echo -e "=====================================\n"
echo -e "====== PACKAGE ["
echo -e $package
echo -e "] DONE ======\n=====================================\n"
echo -e "Appuyez sur [Enter] pour continuer\n"
read -e
done

# script end
echo -e "\n\nAppuyez un touche pour continuer\n"
# wait for user input to end script
read -e

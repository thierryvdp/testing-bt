#!/bin/bash
# List all installed packages
packages=$(pip3 list --outdated| cut -d " " -f 1)
# Upgrade each package
for package in $packages
do
    pip3 install --upgrade $package
done

# script end
echo -e "\n\nAppuyez un touche pour continuer\n"
# wait for user input to end script
read -e

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
chemin_fichier_csv = 'deeplearning/datasets/corona.csv'
pd.set_option('display.max_row', 111)
pd.set_option('display.max_column', 111)
# url = 'https://raw.githubusercontent.com/MachineLearnia/Python-Machine-Learning/master/Dataset/dataset.csv'
# data = pd.read_csv(url, index_col=0, encoding = "ISO-8859-1")
# data.to_csv(chemin_fichier_csv, index=False)

data = pd.read_csv(chemin_fichier_csv)
print(data.head())


import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import csv
import seaborn as sns

# https://github.com/mwaskom/seaborn-data pour recup iris.csv
# iris = pd.read_csv('deeplearning/datasets/iris.csv')
# print(iris.head())
# sns.pairplot(iris, hue = 'species')
# plt.show()

titanic = sns.load_dataset('titanic')
titanic.drop(['alone', 'alive', 'who', 'adult_male', 'embark_town', 'class'], axis=1, inplace=True)
titanic.dropna(axis=0, inplace=True)
print(titanic.head())
# sns.pairplot(titanic)
# sns.catplot(x='pclass', y='age', data=titanic)
# sns.catplot(x='pclass', y='age', data=titanic, hue='sex')
# sns.boxplot(x='pclass', y='age', data=titanic, hue='sex')
# sns.displot(titanic['fare'])
# sns.jointplot(x='age', y='fare', data=titanic)
# sns.jointplot(x='age', y='fare', data=titanic, kind='kde')

titanic['sex'].replace(['male', 'female'],[0, 1],inplace=True)

#print(titanic['embarked'].value_counts())
titanic['embarked'].replace(['C', 'Q', 'S'],[1, 2, 3],inplace=True)

#print(titanic['deck'].value_counts())
titanic['deck'].replace(['A','B','C','D','E','F','G'],[1,2,3,4,5,6,7],inplace=True)

print(titanic.head())

sns.heatmap(titanic.corr())

plt.show()
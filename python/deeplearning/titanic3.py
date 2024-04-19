import numpy as np
import matplotlib.pyplot as plt
from sklearn.pipeline import make_pipeline
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import StandardScaler
from sklearn.preprocessing import OneHotEncoder
from sklearn.linear_model import SGDClassifier
from sklearn.compose import make_column_transformer
import seaborn as sns
# import des datas
titanic = sns.load_dataset('titanic')
print(titanic.head())
y = titanic['survived']
X = titanic.drop('survived', axis=1)
# classification des données
numerical_features = ['pclass', 'age', 'fare']
categorical_features = ['sex', 'deck', 'alone']
# construction des pipelines
numerical_pipeline = make_pipeline(SimpleImputer(), StandardScaler())
categorical_pipeline = make_pipeline(SimpleImputer(strategy='most_frequent'), OneHotEncoder())
# fabrication du column transformer
preprocessor = make_column_transformer((numerical_pipeline, numerical_features),
                                      (categorical_pipeline, categorical_features))
# fabrication du pipeline
model = make_pipeline(preprocessor, SGDClassifier())
# traitement des données
print(model.fit(X, y))


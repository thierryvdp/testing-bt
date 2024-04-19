import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import load_iris

iris = load_iris()
X = iris.data
y = iris.target


# plt.plot(X)
# plt.legend(iris.feature_names)
# plt.show()

# Transformer VarianceThreshold
# from sklearn.feature_selection import VarianceThreshold
# print(X.var(axis=0))
# selector = VarianceThreshold(threshold=0.2)
# print(selector.fit_transform(X))
# # true pour les variables qui ont été sélectionné
# print(selector.get_support())
# print(iris.feature_names)
# print(np.array(iris.feature_names)[selector.get_support()])

# Transformer SelectKBest
# from sklearn.feature_selection import SelectKBest, chi2
# print(chi2(X,y))
# # les 2 meilleures variables (k=2)
# selector = SelectKBest(chi2, k=2)
# print(selector.fit_transform(X, y))
# # true pour les variables qui ont été sélectionné
# print(selector.get_support())
# print(np.array(iris.feature_names)[selector.get_support()])

# SelectFromModel
# from sklearn.feature_selection import SelectFromModel
# from sklearn.linear_model import SGDClassifier
# selector = SelectFromModel(SGDClassifier(random_state=0), threshold='mean')
# print(selector.fit_transform(X, y))
# print(selector.get_support())
# print(np.array(iris.feature_names)[selector.get_support()])

# # explication comment selectionner les variables
# # coef trouvés
# print(selector.estimator_.coef_)
# # moyenne de chaque variable
# print(selector.estimator_.coef_.mean(axis=0))
# # moyenne des moyennes de chaque variable
# print(selector.estimator_.coef_.mean(axis=0).mean())
# # on selectionnera les variables dont 
# # la moyenne est supérieure à la moyenne des moyennes
# print(selector.get_support())
# print(np.array(iris.feature_names)[selector.get_support()])

# SelectFromModel
from sklearn.feature_selection import RFE, RFECV
from sklearn.linear_model import SGDClassifier
# step=nombre de variables à éliminer après une iteration
# min_features_to_select=nombre de variables à conserver à la fin des iterations
# cv=nombre d'iterations ?
selector = RFECV(SGDClassifier(random_state=0), step=1, min_features_to_select=2, cv=5)
print(selector.fit_transform(X, y))
# classement des variables par interet
print(selector.ranking_)
# perf du modele à chaque iteration
print(selector.grid_scores_)


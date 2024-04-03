import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import csv

# https://github.com/mwaskom/seaborn-data pour recup iris.csv

iris = pd.read_csv('deeplearning/datasets/iris.csv')
print(iris.head())

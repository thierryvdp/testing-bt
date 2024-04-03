import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

# charger une feuille excel
bitcoin = pd.read_csv('deeplearning/datasets/BTC-EUR.csv', index_col='Date', parse_dates=True)
ethereum = pd.read_csv('deeplearning/datasets/ETH-EUR.csv', index_col='Date', parse_dates=True)

#assemblage
btc_eth = pd.merge(bitcoin, ethereum, on='Date', how='inner', suffixes=('_btc','_eth'))
print(btc_eth.head())
btc_eth[['Close_btc','Close_eth']].plot(subplots=True, figsize=(12, 8))
print(btc_eth[['Close_btc','Close_eth']].corr())


plt.legend()
plt.show()

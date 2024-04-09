import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

# charger une feuille excel
bitcoin = pd.read_csv('deeplearning/datasets/BTC-EUR.csv', index_col='Date', parse_dates=True)
#          Date        Open        High         Low       Close   Adj Close    Volume
# 0  2014-09-17  359.546204  361.468506  351.586884  355.957367  355.957367  16389166     
# 1  2014-09-18  355.588409  355.505402  319.789459  328.539368  328.539368  26691849     
# 2  2014-09-19  328.278503  330.936707  298.921021  307.761139  307.761139  29560103     
# print(bitcoin.head())

#bitcoin['Close'].plot(figsize=(9,6))

# 3 graphiques resample 
#bitcoin.loc['2019','Close'].plot(label='cours du jour', lw=1)
#bitcoin.loc['2019','Close'].resample('ME').mean().plot(label='Moy Mois', lw=1, ls=':', alpha=0.8)
#bitcoin.loc['2019','Close'].resample('W').mean().plot(label='Moy Week', lw=1, ls='--', alpha=0.8)

# agregation de valeurs
#m=bitcoin.loc['2019','Close'].resample('W').agg(['mean', 'std', 'min', 'max'])
#plt.figure(figsize=(12,8))
#m['mean']['2019'].plot(label='moy week')
#plt.fill_between(m.index, m['max'], m['min'], alpha=0.2, label='min-max hebdo')

# rolling
# bitcoin.loc['2019-09','Close'].plot(label='cours du jour', lw=1)
# bitcoin.loc['2019','Close'].rolling(window=7, center=False).mean().plot(label='moy roulante', lw=1)
# mobile exponentielle
# for i in np.arange(0.2, 1, 0.2):
#     bitcoin.loc['2019-09','Close'].ewm(alpha=i).mean().plot(label='moy alpha-'+str(i), lw=1)

# exo strat
data = bitcoin.copy()
data['Buy'] = np.zeros(len(data))
data['Sell'] = np.zeros(len(data))

data['RollingMax'] = data['Close'].shift(1).rolling(window=28).max()
data['RollingMin'] = data['Close'].shift(1).rolling(window=28).min()
data.loc[data['RollingMax'] < data['Close'], 'Buy'] = 1
data.loc[data['RollingMin'] < data['Close'], 'Sell'] = -1

start='2019'
end='2019'
fig, ax = plt.subplots(2, figsize=(12,8), sharex=True)

ax[0].plot(data['Close'][start:end])
ax[0].plot(data['RollingMin'][start:end])
ax[0].plot(data['RollingMax'][start:end])
ax[0].legend(['close','min','max'])

ax[1].plot(data['Buy'][start:end], c='g')
ax[1].plot(data['Sell'][start:end], c='r')
ax[1].legend(['buy','sell'])

plt.show()

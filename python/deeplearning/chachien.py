import matplotlib.pyplot as plt
import utilities as ut
import os

def spec(dataset):
    print('dataset')
    print('Type:',type(dataset))
    print('Shape:',dataset.shape)

def norm_aplati(dataset):
    # 1. normaliser les datas 0-255 -> 0-1
    dataset = dataset/255
    # 2. aplatir -> dataset.shape[0]x64x64 -> 4096
    dataset = dataset.reshape(dataset.shape[0], 4096)
    return dataset


print('====================================================')
print(os.getcwd())

X_train, y_train, X_test, y_test = ut.load_data()
spec(X_train)
spec(y_train)
spec(X_test)
spec(y_test)

# 1. normaliser les datas 0-255 -> 0-1
# 2. aplatir -> 64x64 -> 4096
X_train = norm_aplati(X_train)
X_test = norm_aplati(X_test)

# 3. entrainement (peut être utiliser sklearn au lieu de notre log_loss)
poids, biais, log_loss = ut.artificial_neuron(X_train, y_train)

# 4. tracer la courbe d'apprentissage ; nb si notre log_loss marche mal
# utiliser celle de sklearn du module metrics
plt.plot(log_loss)
plt.show()

# 5. evaluer le modele sur le test
print(ut.predict(X_test[0], poids, biais))



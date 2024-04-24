import matplotlib.pyplot as plt
import utilities as ut
import os

def spec(dataset):
    print('dataset')
    print('Type:',type(dataset))
    print('Shape:',dataset.shape)

def norm_aplati(dataset):
    # 1. aplatir -> dataset.shape[0]x64x64 -> 4096
    # 2. normaliser les datas 0-255 -> 0-1
    # formule normale : (X - Xmin)/(Xmax - Xmin)
    # un peu facile ...  Xmin=0 Xmax=255 donc  dataset_wrk = dataset/255
    # return dataset.reshape(dataset_wrk.shape[0], 4096).max()
    # return dataset.reshape(dataset_wrk.shape[0], dataset_wrk.shape[1] * dataset_wrk.shape[2]).max()
    # return dataset.reshape(dataset.shape[0], -1) / dataset.max()
    # max marche pas car il faut garder le max du train set pour normaliser de la même façon
    return dataset.reshape(dataset.shape[0], -1) / 254


print('====================================================')
print(os.getcwd())

X_train, y_train, X_test, y_test = ut.load_data()
spec(X_train)
spec(y_train)
spec(X_test)
spec(y_test)

# 1. normaliser les datas 0-255 -> 0-1
# 2. aplatir -> 64x64 -> 4096
X_train_reshape = norm_aplati(X_train)
X_test_reshape = norm_aplati(X_test)

# 3. entrainement (peut être utiliser sklearn au lieu de notre log_loss)
poids, biais, log_loss, accuracy_list, test_loss, test_accuracy = ut.artificial_neuron(X_train_reshape, y_train, X_test_reshape, y_test, learning_rate=0.01, n_iter=10000)

# 4. tracer la courbe d'apprentissage ; nb si notre log_loss marche mal
# utiliser celle de sklearn du module metrics
plt.figure(figsize=(12,4))
plt.subplot(1,2,1)
plt.plot(log_loss, label='train loss')
plt.plot(test_loss, label='test loss')
plt.legend()
plt.subplot(1,2,2)
plt.plot(accuracy_list, label='train accuracy')
plt.plot(test_accuracy, label='test  accuracy')
plt.legend()
plt.show()

# 5. evaluer le modele sur le test
print(ut.predict(X_test_reshape[0], poids, biais))



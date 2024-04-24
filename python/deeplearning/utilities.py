#pip install h5py
import h5py
import numpy as np
from sklearn.metrics import log_loss, accuracy_score
import matplotlib.pyplot as plt

def load_data():
    train_dataset = h5py.File('deeplearning/datasets/trainset.hdf5', "r")
    X_train = np.array(train_dataset["X_train"][:]) # your train set features
    y_train = np.array(train_dataset["Y_train"][:]) # your train set labels

    test_dataset = h5py.File('deeplearning/datasets/testset.hdf5', "r")
    X_test = np.array(test_dataset["X_test"][:]) # your train set features
    y_test = np.array(test_dataset["Y_test"][:]) # your train set labels
    
    return X_train, y_train, X_test, y_test

def init_poids_biais(x_dataset):
    poids = np.random.randn(x_dataset.shape[1], 1)
    biais = np.random.randn(1)
    return (poids, biais)

def model(x_dataset, poids, biais):
    sortie = x_dataset.dot(poids) + biais
    proba_activation = 1 / (1 + np.exp(-sortie))
    return proba_activation

def log_loss(activation, y_dataset):
   return 1 / len(y_dataset) * np.sum( -y_dataset * np.log(activation) - (1 - y_dataset) * np.log(1 - activation))

def gradients(activation, x_dataset, y_dataset):
    dW = 1 / len(y_dataset) * np.dot(x_dataset.T, activation - y_dataset)
    db = 1 / len(y_dataset) * np.sum(activation - y_dataset)
    return (dW, db)

def update(delta_poids, delta_biais, poids, biais, learning_rate):
    poids = poids - learning_rate * delta_poids
    biais = biais - learning_rate * delta_biais
    return (poids, biais)

def artificial_neuron(x_dataset, y_dataset, learning_rate = 0.1, n_iter = 100):
    #initialiser W, b
    poids, biais = init_poids_biais(x_dataset)
    # etude du cout qui doit diminuer ...
    loss_list = []
    for i in range(n_iter):
        activation= model(x_dataset, poids, biais)
        loss_list.append(log_loss(activation, y_dataset))
        dW, db = gradients(activation, x_dataset, y_dataset)
        poids, biais = update(dW, db, poids, biais, learning_rate)   
    # y_pred = predict(x_dataset, poids, biais)
    # print(accuracy_score(y_dataset, y_pred))
    # plt.plot(loss_list)
    # plt.show()
    return poids, biais, loss_list

def predict(x_dataset, poids, biais):
    proba_activation = model(x_dataset, poids, biais)
    return proba_activation >= 0.5

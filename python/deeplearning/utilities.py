#pip install h5py
import h5py
import numpy as np
from sklearn.metrics import log_loss, accuracy_score
import matplotlib.pyplot as plt
from tqdm import tqdm

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
    # l'exponentielle fait des overflow ...
    # cela rempli la matrice d'activation avec des 1 et des 0
    proba_activation = 1 / (1 + np.exp(-sortie))
    return proba_activation

def log_loss(activation, y_dataset):
    # on ajoute un epsilon pour eviter les 1 et 0 de la matrice d'activation
   epsilon = 1e-15
   return 1 / len(y_dataset) * np.sum( -y_dataset * np.log(activation + epsilon) - (1 - y_dataset) * np.log(1 - activation  + epsilon))

def gradients(activation, x_dataset, y_dataset):
    dW = 1 / len(y_dataset) * np.dot(x_dataset.T, activation - y_dataset)
    db = 1 / len(y_dataset) * np.sum(activation - y_dataset)
    return (dW, db)

def update(delta_poids, delta_biais, poids, biais, learning_rate):
    poids = poids - learning_rate * delta_poids
    biais = biais - learning_rate * delta_biais
    return (poids, biais)

def artificial_neuron(x_dataset, y_dataset, x_test_dataset, y_test_datatest, learning_rate = 0.1, n_iter = 100):
    #initialiser W, b
    poids, biais = init_poids_biais(x_dataset)
    # etude du cout qui doit diminuer ...
    loss_list = []
    accuracy_list = []
    # etude de test
    test_loss = []
    test_accuracy = []
    
    for i in tqdm(range(n_iter)):
        # activation
        activation= model(x_dataset, poids, biais)
        if i %10 == 0:
            # calcul du cout du trainset
            loss_list.append(log_loss(activation, y_dataset))
            # calcul de l'accuracy du trainset
            y_pred = predict(x_dataset, poids, biais)
            accuracy_list.append(accuracy_score(y_dataset, y_pred))
            
            # calcul du cout du testset
            activationTest= model(x_test_dataset, poids, biais)
            test_loss.append(log_loss(activationTest, y_test_datatest))
            # calcul de l'accuracy du testset
            y_pred = predict(x_test_dataset, poids, biais)
            test_accuracy.append(accuracy_score(y_test_datatest, y_pred))
            
        # mise à jour
        dW, db = gradients(activation, x_dataset, y_dataset)
        poids, biais = update(dW, db, poids, biais, learning_rate)
    return poids, biais, loss_list, accuracy_list, test_loss, test_accuracy

def predict(x_dataset, poids, biais):
    proba_activation = model(x_dataset, poids, biais)
    return proba_activation >= 0.5

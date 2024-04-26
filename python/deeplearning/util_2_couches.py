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
# n0 nombres d'entrées
# n1 nombres de neurones de la couche 1
# n2 nombres de neurones de la couche 2 (sortie)
def init_poids_biais(n0, n1, n2):
    poids1 = np.random.randn(n1, n0)
    biais1 = np.random.randn(n1, 1)
    poids2 = np.random.randn(n2, n1)
    biais2 = np.random.randn(n2, 1)
    parametres = {
        'W1':poids1,
        'b1':biais1,
        'W2':poids2,
        'b2':biais2
    }
    return parametres

def forward_propagation(x_dataset, parametres):
    W1 = parametres['W1']
    b1 = parametres['b1']
    W2 = parametres['W2']
    b2 = parametres['b2']
    Z1 = W1.dot(x_dataset) + b1
    A1 = 1 / (1 + np.exp(-Z1))
    # l'exponentielle fait des overflow ...
    # cela rempli la matrice d'activation avec des 1 et des 0
    Z2 = W2.dot(A1) + b2
    A2 = 1 / (1 + np.exp(-Z2))
    activations={
        'A1':A1,
        'A2':A2
    }
    return activations

def log_loss(activation, y_dataset):
    # on ajoute un epsilon pour eviter les 1 et 0 de la matrice d'activation
   epsilon = 1e-15
   return 1 / len(y_dataset) * np.sum( -y_dataset * np.log(activation + epsilon) - (1 - y_dataset) * np.log(1 - activation  + epsilon))

def back_propagation(x_dataset, y_dataset, activations, parametres):
    A1=activations['A1']
    A2=activations['A2']
    W2=parametres['W2']
    m = y_dataset.shape[1]
    
    dZ2 = A2 - y_dataset
    dW2 = 1 / m * dZ2.dot(A1.T)
    db2 = 1 / m * np.sum(dZ2, axis=1, keepdims=True)

    dZ1 = np.dot(W2.T, dZ2) * A1 * (1 - A1)
    dW1 = 1 / m * dZ1.dot(x_dataset.T)
    db1 = 1 / m * np.sum(dZ1, axis=1, keepdims=True)
    
    gradients = {
        'dW1', dW1,
        'db1', db1,
        'dW2', dW2,
        'db2', db2
    }

    return gradients

def update(gradients, parametres, learning_rate):
    W1 = parametres['W1']
    b1 = parametres['b1']
    W2 = parametres['W2']
    b2 = parametres['b2']

    dW1 = gradients['dW1']
    db1 = gradients['db1']
    dW2 = gradients['dW2']
    db2 = gradients['db2']

    W1 = W1 - learning_rate * dW1
    b1 = b1 - learning_rate * db1
    W2 = W2 - learning_rate * dW2
    b2 = b2 - learning_rate * db2

    W1 = parametres['W1']
    b1 = parametres['b1']
    W2 = parametres['W2']
    b2 = parametres['b2']

    return parametres

def predict(x_dataset, parametres):
    activations = forward_propagation(x_dataset, parametres)
    A2 = activations['A2']
    return A2 >= 0.5

def neural_network(x_dataset, y_dataset, x_test_dataset, y_test_datatest, learning_rate = 0.1, n_iter = 100):
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


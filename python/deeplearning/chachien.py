
import utilities as ut

X_train, y_train, X_test, y_test = ut.load_data()

print('X_train')
print('Type:',type(X_train))
print('Shape:',X_train.shape)

# 1. normaliser les datas 0-255 -> 0-1
# 2. aplatir -> 64x64 -> 4096
# 3. entrainement (peut être utiliser sklearn au lieu de notre log_loss)
# 4. tracer la courbe d'apprentissage
# 5. evaluer le modele sur le test

# poids, biais = ut.artificial_neuron(X_train, y_train)


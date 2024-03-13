# %matplotlib inline
import torch
import numpy as np

data = [[1, 2, 3],[4, 5, 6],[7, 8, 9],[0, 1, 2]]

# initializing a tensor from data
x_data = torch.tensor(data)
print(f"Tensor x_data value: \n {x_data} \n")

# from NumPy array
# Since, numpy 'np_array' and tensor 'x_np' share the same memory location
# changing the value for one will change the other
np_array = np.array(data)
x_np = torch.from_numpy(np_array)

print(f"Numpy np_array value: \n {np_array} \n")
print(f"Tensor x_np value: \n {x_np} \n")

np.multiply(np_array, 2, out=np_array)

print(f"Numpy np_array after * 2 operation: \n {np_array} \n")
print(f"Tensor x_np value after modifying numpy array: \n {x_np} \n")

# from another tensor
x_ones = torch.ones_like(x_data) # retains the properties of x_data
print(f"Ones Tensor: \n {x_ones} \n")

x_rand = torch.rand_like(x_data, dtype=torch.float) # overrides the datatype of x_data
print(f"Random Tensor: \n {x_rand} \n")

# with random or constant values
shape = (2,3)
rand_tensor = torch.rand(shape)
ones_tensor = torch.ones(shape)
zeros_tensor = torch.zeros(shape)

print(f"Random Tensor: \n {rand_tensor} \n")
print(f"Ones Tensor: \n {ones_tensor} \n")
print(f"Zeros Tensor: \n {zeros_tensor}")

# attributes
print(f"Shape of tensor: {zeros_tensor.shape}")
print(f"Datatype of tensor: {zeros_tensor.dtype}")
print(f"Device tensor is stored on: {zeros_tensor.device}")



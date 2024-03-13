import torch
import numpy as np

my_data = [[1., 1., 1.],[2., 2., 2.],[3., 3., 3.],[4., 4., 4.]]
np_array = np.array(my_data)
tensor = torch.from_numpy(np_array)

# We move our tensor to the GPU if available
if torch.cuda.is_available():
  tensor = tensor.to('cuda')

# attributes
print(f"Shape of tensor: {tensor.shape}")
print(f"Datatype of tensor: {tensor.dtype}")
print(f"Device tensor is stored on: {tensor.device}")
print(tensor)
print('--------------------------')
# manipulation
print('First row: ',tensor[0])
print('First column: ', tensor[:, 0])
print('Last column:', tensor[..., -1])
# tensor[:,1] = 0
# print(tensor)

# join tensors
t1 = torch.cat([tensor, tensor, tensor], dim=1)
print(t1)

# arithmetique

# This computes the matrix multiplication between two tensors.
# y1, y2, y3 will have the same value
y1 = tensor @ tensor.T
y2 = tensor.matmul(tensor.T)

y3 = torch.rand_like(tensor)
torch.matmul(tensor, tensor.T, out=y3)


# This computes the element-wise product. 
# z1, z2, z3 will have the same value
z1 = tensor * tensor
z2 = tensor.mul(tensor)

z3 = torch.rand_like(tensor)
torch.mul(tensor, tensor, out=z3)

print(tensor)
print(y1)
print(z1)

# single element tensor
agg = tensor.sum()
agg_item = agg.item()  
print(agg_item, type(agg_item))

# inplace operation
print(tensor, "\n")
tensor.add_(5)
print(tensor)

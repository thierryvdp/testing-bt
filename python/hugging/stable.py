# 1.	Désinstaller torch si déjà installé sans cuda
#     pip unstall torch
# 2.	Download cuda depuis le site nvidia développeur
#     https://developer.nvidia.com/cuda-zone#
# 3.	Vérifier la version de cuda
#     nvcc –version
#     nvcc: NVIDIA (R) Cuda compiler driver
#     Copyright (c) 2005-2024 NVIDIA Corporation
#     Built on Tue_Feb_27_16:28:36_Pacific_Standard_Time_2024
#     Cuda compilation tools, release 12.4, V12.4.99
#     Build cuda_12.4.r12.4/compiler.33961263_0
# 4.	Install torch
#     https://pytorch.org/get-started/locally/
#     Choisir les bonnes options pour avoir la ligne de commande 
#     pip3 install torch torchvision torchaudio --index-url https://download.pytorch.org/whl/cu121
# 5.	Vérifier l’install
#     import torch
#     print("torch : "+torch.__version__)
#     print(“CUDA:”+torch.cuda.is_available())
#
# pip install diffusers
# pip install transformers
# PYTORCH_CUDA_ALLOC_CONF=expandable_segments:True
# HF_HOME=D:\cache\huggingface

from diffusers import StableDiffusionPipeline
import torch

# pip install Pillow
from PIL import Image

# https://huggingface.co/docs/datasets/cache
model_id = "runwayml/stable-diffusion-v1-5"

print('pipe=diffuseur.from_pretrained')
pipe = StableDiffusionPipeline.from_pretrained(model_id, torch_dtype=torch.float16)
print('pipe=pipe.to(cuda)')
pipe = pipe.to("cuda")

prompt = "Une armée d'anges qui volent au dessus de la ville."
chemin_image="d:/tmp/images/stable/astronaut_rides_horse.png"

print('image_in=pipe')
image_in = pipe(prompt, height=1024, width=1024).images[0]  
print('save image')
image_in.save(chemin_image)

image_out = Image.open(chemin_image)
print('show image')
image_out.show()

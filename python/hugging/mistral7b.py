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
# pip install --upgrade huggingface_hub

# pip install 'huggingface_hub[tensorflow]'
# pip install 'huggingface_hub[cli,torch]'
# pip install transformers
# PYTORCH_CUDA_ALLOC_CONF=expandable_segments:True
# HF_HOME=D:\cache\huggingface


from transformers import AutoModelForCausalLM, AutoTokenizer
import torch

device = "cuda" # the device to load the model onto

print('model=AutoModelForCausalLM.from_pretrained')
model = AutoModelForCausalLM.from_pretrained("mistralai/Mistral-7B-Instruct-v0.2", torch_dtype=torch.float16)
print('AutoTokenizer.from_pretrained')
tokenizer = AutoTokenizer.from_pretrained("mistralai/Mistral-7B-Instruct-v0.2")

messages = [
    {"role": "user", "content": "What is your favourite condiment?"},
    {"role": "assistant", "content": "Well, I'm quite partial to a good squeeze\
         of fresh lemon juice. It adds just the right amount of zesty\
             flavour to whatever I'm cooking up in the kitchen!"},
    {"role": "user", "content": "Do you have mayonnaise recipes?"}
]

print('encode=tokenizer.apply_chat_template')
encodeds = tokenizer.apply_chat_template(messages, return_tensors="pt")

print('model_inputs = encodeds.to(device)')
model_inputs = encodeds.to(device)
print('model.to(device)')
model.to(device)

print('generated_ids = model.generate')
# generated_ids = model.generate(model_inputs, max_new_tokens=1000, do_sample=True)
generated_ids = model.generate(model_inputs, max_new_tokens=40, do_sample=True)
print('decoded = tokenizer.batch_decode')
decoded = tokenizer.batch_decode(generated_ids)
print(decoded)

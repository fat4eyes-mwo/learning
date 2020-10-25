'''
Created on 24 Oct 2020

@author: Neil
'''

fin = open("test.in", "r")
s = fin.read()
print(s)
fin.close()

fin = open("test.in", "r")
lines = fin.readlines()
for l in lines:
    print(l, end="")
fin.close()
print()

fin = open("test.in", "rb")
filebytes = fin.read()
print(filebytes)
fin.close()

fout = open("test.out", "w")
fout.write(s)
fout.close()

fout = open("test.bin", "wb")
fout.write(filebytes)
fout.close()

with open("test.in", "r") as f:
    s = f.read()
    print(s)
    
with open("test.bin2", "wb") as f:
    f.write(filebytes)
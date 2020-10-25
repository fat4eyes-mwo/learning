'''
Created on 22 Oct 2020

@author: Neil
'''
n = 1000
testgen = (x for x in range(0,n))


fout = open("generator.out", "wt")

for x in testgen:
    testgen2 = (x for x in range(0,n))
    for y in testgen2:
        prod = x * y
        if (prod % 1000 == 0 and prod > 0): 
            print(prod, file=fout)

fout.close()
'''
Created on 24 Oct 2020

@author: Neil
'''
from _collections import deque

print("lists")
a = [1, 2, 3, 4, 5]
a.append(6)
print(a)
print(a.pop())
print(a)
del(a[1:3])
print(a)

#slicing
print("slicing")
s = [1,2,3,4,5];
print(s[-6:6])
print(s[0:len(s):2])
print(4 in s)
print(20 in s)

#list stuff
print("list stuff")
l = [1,2,3,4,5]
print(l)
l.pop(2)
print(l)
l.append(6)
print(l)
l.extend([7,8])
print(l)
l.remove(4)
print(l)
print(l.index(5))
#print(l.index(4)) #error, not just -1

#deque for better performance
print("dequeue")
q = deque([1,2,3,4])
print(q)
q.appendleft(0)
q.append(5)
print(q)
q.extendleft([-2,-1])
q.extend([6,7])
print(q)

#list comprehension
print("list comprehension")
squares = list(map((lambda x, y : x * y), range(0,5), range(0,10)))
while squares :
    s = squares.pop()
    print(s, end=" ")
print()

#more list comprehension
#I don't like how they used for-in as an operator. Should have added another keyword
squares = [x * y for x in range(0, 3) for y in range(3,6)]
print(squares)

print(x * y for x in range(0, 3) for y in range(3,6))
nums = [x for x in range(7,10)] #this is a list comprehension (specifically the syntax [xxx for ...], ie a x for ... DIRECTLY inside []. They should have just used a fucking function.
print(nums)
nums = (x for x in range(25,30)) #this just makes a generator
print(nums)

#also stupid. They changed for into a FUCKING OPERATOR! Why not just use a new fucking keyword!?!?!?!?
xgen = (x for x in range(0,3))
print(list(xgen)) #is a list made from the generator
ygen = (y for y in range(3,6))
print([ygen]) #somehow is NOT a fucking list. FUCKING STUPID. Is actually just a list containing the generator

#zip
for a, b in zip(["hey", "hey", "hey"], ["ho", "ho", "ho"]):
    print (a + b)
    
#sequence operations
print("sequence operations")
s = list(range(0,6))
r = list(s) + [6,7,8,9]
print(s); print(r)
print(r[::2])
print(s * 3)
print((s*3).count(3))
q = s * 2
q[0] = 10
print(q)

#slice reference test
print("slice reference")
s = [[1], [2], [3]]
r = s[0:2]
print(s); print(r)
r[0][0] = 5
print(s); print(r)

#mutable operations tests
print("mutable operations")
s = list(range(0, 10))
s[4:7] = map(lambda x : x*2, s[4:7])
print(s)
s[4:7] = [100]
print(s)
print(s.pop(4))
print(s)

s = [3,2,6,8,4,9]
s.sort()
print(s)
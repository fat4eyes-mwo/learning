from math import floor

#basics
print("Hello world!")
for x in range(0, 5):
    print(x)

#control flow
print("Hello again")
for x in [2,3,7,9]:
    print(x)

for x in range(0, 3):
    a = 1
    b = 2 - x
    if a - b < 0:
        print(b)
    elif a - b > 0:
        print(a)
    else:
        print(0)
        
#booleans
print("booleans")
t = True
f = False
if []:
    print("Never")
elif ['foo']:
    print("Always")
else:
    print("Never2")

#basic functions
print("basic functions")
def factorial(n:'int') -> 'int':
    if n <= 1 :
        return 1
    else :
        return n * factorial(n-1)

def fib(n:'int') -> 'int':
    if n == 0 :
        return 0
    elif n == 1 :
        return 1
    else :
        return fib(n-1) + fib(n-2)

for n in range(0, 11):
    print(factorial(n))
print(factorial.__annotations__);

for n in range(0, 11):
    print(fib(n), end=" ")
print()
print(fib.__annotations__)

#decorators
print("decorators")
def string_star_decorator(func) :
    def star_decorator(s): 
        value = func(s)
        return "***" + str(value) + "***"
    return star_decorator

def string_plus_decorator(func) :
    def plus_decorator(s):
        value = func(s);
        return value[:floor(len(value)/2)] + "+++" + value[floor(len(value)/2):]
    return plus_decorator

@string_star_decorator
@string_plus_decorator
def the_string(s):
    return s

print(the_string("foobar"))

#function parameter insanity
print("function parameters")
def func(p1, p2, /, p3, p4, p5):
    print("%d %d %d %d %d" % (p1, p2, p3, p4, p5))
    return

func(1, 2, p5=5, p3=3, p4=4)
#func(p2=2, p1=1, p5=5, p3=3, p4=4) #error

def func2(*, p1, p2, p3):
    print(str(p1) + str(p2) + str(p3))
    
#func2(1,2,3) #error
func2(p3=3, p2=2, p1=1)

#tuples
print("tuples")
print(tuple([1,2,3]))
(x,y,z) = tuple([1,2,3])
print(x) ; print(y); print(z)

#exceptions
print("exceptions")
try:
#     x = 12/0
    x=1
except Exception as err:
    print(err)
else:
    print("No error")
    
a = 1 if True else 2
print(a)


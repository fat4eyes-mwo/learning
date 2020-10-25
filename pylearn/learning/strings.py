'''
Created on 24 Oct 2020

@author: Neil
'''

def main():
    print("String tests")

    
    s = "Hello %s, I am %d years old" % ("world", 12)
    print(s)
    
    s = "lowercase"
    print(s.capitalize())
    print(s.lower())
    print(s.upper())
    
    s = "".join(["Mary","Had", "A", "Little", "Lamb"])
    print(s)
    
    s = "    This is the best thing   "
    print(s.strip())
    print(s.strip(' Tg'))
    

main()
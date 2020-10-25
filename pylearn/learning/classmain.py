'''
Created on 22 Oct 2020

@author: Neil
'''

class TestClass(object):
    '''
    classdocs
    '''
    #RULE OF THUMB: DONT USE CLASS VARIABLES
    sx = 0
    sy = 0
    slist = []

    def __init__(self, x, y):
        '''
        Constructor
        '''
        (self.x, self.y) = (x,y)
        (self.sx, self.sy) = (x*2, y*2) #NOTE: These are SEPARATE instance variables from the class variables above
    
    def print_vals(self):
        print([self.x,self.y])
        

def main():
    c = TestClass(1, 2)
    c.print_vals()
    d = TestClass(3, 4)
    d.print_vals()
    print ([c.sx, c.sy])
    print ([d.sx, d.sy])

    TestClass.slist = [9,0]
    
    print ([c.sx, c.sy])
    print ([d.sx, d.sy])
    print (c.slist)
    print (d.slist)

    print(dir(c))
    print(TestClass)
    
main()
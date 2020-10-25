'''
Created on 25 Oct 2020

@author: Neil
'''
#NOTE: the packages were initially named modules.test, but modules apparently is already another package somewhere. Be careful of naming conventions
class TestModuleClass(object):
    '''
    classdocs
    '''


    def __init__(self, f):
        '''
        Constructor
        '''
        self.f = f
        
    def __str__(self):
        return "TestModuleClass(%s)" % self.f;
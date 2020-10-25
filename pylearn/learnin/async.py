'''
Created on 23 Oct 2020

@author: Neil
'''

import asyncio
import time

WAIT_TIME = 5

async def async_wait(ident) :
    print("async_wait start id %d" % ident)
    await asyncio.sleep(WAIT_TIME, id)
    print("async_wait end id %d" % ident)

async def wait_test():
    waitlist = tuple(map(async_wait, range(0,10)))
    print(waitlist)
    await asyncio.gather(*waitlist) #note: * unpacks waitlist into individual arguments

async def async_gen(ident):
    print("gen start id %d" % ident)
    yield ident
    await asyncio.sleep(WAIT_TIME) 
    print("gen end id %d" % ident)

async def generator_test():
    async def wrapper(ident):
        async for ident in async_gen(ident):
            print("gen id: %d" % ident)
            
    wrapperlist = tuple(map(wrapper, range(10,20)))    
    print(wrapperlist)
    await asyncio.gather(*wrapperlist)

def main():
    asyncio.run(wait_test())
    asyncio.run(generator_test())
    
if __name__ == '__main__':
    print("Start async")
    tstart = time.perf_counter_ns()
    
    main()
    
    print("End async")
    print("Time: %d.10 ms" % ((time.perf_counter_ns() - tstart)/1000000))
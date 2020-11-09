'''
Created on 27 Oct 2020

@author: Neil
'''
import random
import time

def mergesort(arr):
    if len(arr) <= 1:
        return arr
    if len(arr) == 2:
        if arr[0] > arr[1]:
            temp = arr[0]
            arr[0] = arr[1]
            arr[1] = temp
        return arr
    else:
        split_idx = len(arr) // 2
        left_arr = mergesort(arr[0:split_idx])
        right_arr = mergesort(arr[split_idx:])
        ret_arr = []
        while len(left_arr) > 0 and len(right_arr) > 0:
            if (left_arr[len(left_arr)-1] > right_arr[len(right_arr)-1]):
                ret_arr.append(left_arr.pop())
            else:
                ret_arr.append(right_arr.pop())
        left_arr.reverse()
        right_arr.reverse()
        ret_arr = ret_arr + left_arr + right_arr
        ret_arr.reverse()
        return ret_arr 
    
for size in [10000, 100000, 1000000]:
    print("Size: %d" % size)
    shuffled = list(range(size))
    random.shuffle(shuffled)
    
    tstart = time.perf_counter_ns()
    retarr = mergesort(shuffled)
    print(retarr[0:10], "... %d entries" % len(retarr))
    print("Mergesort time: %d.10 ms" % ((time.perf_counter_ns() - tstart)/1000000))
    
    shuffled = list(range(size))
    random.shuffle(shuffled)
    tstart = time.perf_counter_ns()
    shuffled.sort()
    print(shuffled[0:10], "... %d entries" %len(shuffled))
    print("Builtin sort time: %d.10 ms" % ((time.perf_counter_ns() - tstart)/1000000))
    print("---\n")
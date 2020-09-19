import numpy as np
import serial

def get_arr():
    n = 100
    arr = np.zeros(8*n).reshape(n, 8)
    my_list = []
    list_a = arr.tolist()
    for i in range(len(list_a)):
        for j in range(len(list_a[i])):
            my_list.append(list_a[i][j])

    return my_list

def hope():
    ser = serial.Serial('com3', 115200)
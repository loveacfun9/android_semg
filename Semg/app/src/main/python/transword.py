# !/usr/bin/env python
# -*- coding: utf-8 -*-

import struct


# 字符 -> 16进制数据
# 字节类型的数据变成16进制数据
# b'<s>' -> '3c 73 3e'
def strtohex(string):
    result = ''
    for i in string:
        #         print(i,type(i))
        #         hvol = ord(i)                  # 返回十进制值
        #         hhex = '%02x' % hvol
        hhex = '%02x' % i
        result = result + hhex + ' '
    return result.strip()


# 字符（16进制格式显示的数据）-> 16进制的字节数据
# '00 1a 3f' -> b'\x00\x1a\x3f'
def hexconvert(string):
    new_list = str(string).strip().split(' ')  # strip()去除首尾空格、split(' ')将str转为list,步长为空格
    fin = ''
    fin = fin.encode('utf-8')
    for i in range(len(new_list)):
        fin = fin + struct.pack('B', int(new_list[i], 16))  # 16进制数转10进制数，再转换为Byte类型
    return fin


# 字符（16进制表示的32位浮点数） -> 10进制数
# '00 00 cc 3f' -> 1.59375
def hextonum(string):
    # 16进制转2进制
    num_2 = ''
    new_list = str(string).strip().split(' ')
    new_list.reverse()
    for l in new_list:
        temp = bin(int(l, 16))[2:]
        if len(temp) != 8:
            temp = '0' * (8 - len(temp)) + temp
        num_2 = num_2 + temp

    # 符号位、阶码、尾数
    s2 = num_2[0]
    e2 = num_2[1:9]
    m2 = num_2[9:]
    # print s2, e2, m2

    # 计算小数部分(10进制)
    if len(m2) != 0:
        temp = 0
        for i in range(1, len(m2) + 1):
            temp = temp + int(m2[i - 1]) * (2 ** (-1 * i))
        latter10 = temp
    else:
        latter10 = 0
    # print latter10

    # 计算阶数
    e10 = int(e2, 2) - 127

    # 计算公式
    num_10 = ((-1) ** int(s2)) * (1 + latter10) * (2 ** e10)

    return num_10

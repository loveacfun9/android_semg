import serial
from transword import hextonum, strtohex, hexconvert
import numpy as np




# 指令2:查询状态02
instruct2 = '3C 3C 01 00 00 00 00 00 00 00 06 06 06 06 06 06 02 48 48 48 48 48 36 4F 00 00 3E 3E'
# 指令6:待机06
instruct6 = '3C 3C 01 00 00 00 00 00 00 00 06 06 06 06 06 06 06 48 48 48 48 48 36 4F 00 00 3E 3E'
# 指令D:请求发送诊断数据0D
instructD = '3C 3C 01 00 00 00 00 00 00 00 06 06 06 06 06 06 0D 48 48 48 48 48 36 4F 00 00 3E 3E'
# 指令E:停止发送诊断数据0E
instructE = '3C 3C 01 00 00 00 00 00 00 00 06 06 06 06 06 06 0E 48 48 48 48 48 36 4F 00 00 3E 3E'

# 连接串口
ser = serial.Serial('com3', 115200)
print(ser.portstr)                  # 串口号

# 读取诊断数据（含标签）
my_insD = hexconvert(instructD)
ser.write(my_insD)

# n个预测结果中取最频繁出现的值，粗略判定为状态值
n = 100

# 初始化数组n行8列，8列代表着一个数据的八个特征值
arr = np.zeros(8*n).reshape(n, 8)

# 读取全部的32Byte待机数据
mystr = ser.read(3)
data3 = strtohex(mystr)
while data3 == '3c 3c 02':
    ser.read(29)
    mystr = ser.read(3)
    data3 = strtohex(mystr)

# 数据变为92Byte的诊断数据
if data3 == '3c 3c 05':
    ser.read(89)
    # 循环n次的计数值
    count = 0
    while count != n:
        mystr = ser.read(92)
        str_hex = strtohex(mystr)
        alldata = str_hex[48:143]
        # 循环八次的计数值
        j = 0
        for i in range(0, 96, 12):
            singledata = alldata[i:i + 11]
            num_10 = hextonum(singledata)
            # 为数组赋值
            arr[count][j] = num_10
            j += 1
        count = count + 1


# arr即为100个数据的数组
my_list = []
list_a = arr.tolist()
for i in range(len(list_a)):
    for j in range(len(list_a[i])):
        my_list.append(list_a[i][j])

# 通过串口发送待机命令
my_ins6 = hexconvert(instruct6)
n = ser.write(my_ins6)
# 结束提示
print("over")

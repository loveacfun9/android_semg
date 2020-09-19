from java import jclass
from transword import hexconvert
import serial

# 指令6:待机06
instruct6 = '3C 3C 01 00 00 00 00 00 00 00 06 06 06 06 06 06 06 48 48 48 48 48 36 4F 00 00 3E 3E'

# 连接串口
ser = serial.Serial('com3', 115200)
print(ser.portstr)                  # 串口号

my_ins6 = hexconvert(instruct6)
n = ser.write(my_ins6)
print("success!")

import easygui
import csv
import random
import string
import json

csvfile = open("passwordData.csv", "r")
passwordFile = csv.reader(csvfile, delimiter=',')

def coder(message):
    characters = list(message)

    alphNum = {}

    alphabet = string.ascii_lowercase
    letters = list(alphabet)

    numbers = []

    for x in range(1, 27):
        numbers.append(x)

    for x in range(0, 26):
        alph = alphabet[x]
        num = numbers[x]

        alphNum[alph] = num

    code = alphNum
    Alphabet = alphNum.keys()
    Numbers = alphNum.values()

    codeList = []

    for character in characters:
        if character == ' ':
            codeList.append(27)
        for letter in Alphabet:
            if character == letter:
                encodedNum = alphNum[letter]
                codeList.append(encodedNum)
                break
            else:
                continue

    changeableCode = codeList
    numIterations = len(codeList)

    while numIterations != 45:
        codeList.append(27)
        numIterations = numIterations + 1

    Coding_Matrix = (4, 3, 1, 2, 1, 0, 3, 4, 2)

    Message = codeList

    V1 = Message[0]
    V2 = Message[1]
    V3 = Message[2]
    V4 = Message[3]
    V5 = Message[4]
    V6 = Message[5]
    V7 = Message[6]
    V8 = Message[7]
    V9 = Message[8]
    V10 = Message[9]
    V11 = Message[10]
    V12 = Message[11]
    V13 = Message[12]
    V14 = Message[13]
    V15 = Message[14]
    V16 = Message[15]
    V17 = Message[16]
    V18 = Message[17]
    V19 = Message[18]
    V20 = Message[19]
    V21 = Message[20]
    V22 = Message[21]
    V23 = Message[22]
    V24 = Message[23]
    V25 = Message[24]
    V26 = Message[25]
    V27 = Message[26]
    V28 = Message[27]
    V29 = Message[28]
    V30 = Message[29]
    V31 = Message[30]
    V32 = Message[31]
    V33 = Message[32]
    V34 = Message[33]
    V35 = Message[34]
    V36 = Message[35]
    V37 = Message[36]
    V38 = Message[37]
    V39 = Message[38]
    V40 = Message[39]
    V41 = Message[40]
    V42 = Message[41]
    V43 = Message[42]
    V44 = Message[43]
    V45 = Message[44]

    Co1 = [V1, V2, V3]
    Co2 = [V4, V5, V6]
    Co3 = [V7, V8, V9]
    Co4 = [V10, V11, V12]
    Co5 = [V13, V14, V15]
    Co6 = [V16, V17, V18]
    Co7 = [V19, V20, V21]
    Co8 = [V22, V23, V24]
    Co9 = [V25, V26, V27]
    Co10 = [V28, V29, V30]
    Co11 = [V31, V32, V33]
    Co12 = [V34, V35, V36]
    Co13 = [V37, V38, V39]
    Co14 = [V40, V41, V42]
    Co15 = [V43, V44, V45]

    Row1 = [4, 2, 3]
    Row2 = [3, 1, 4]
    Row3 = [1, 0, 2]

    M1_1 = Row1[0] * Co1[0] + Row1[1] * Co1[1] + Row1[2] * Co1[2]
    M1_2 = Row2[0] * Co1[0] + Row2[1] * Co1[1] + Row2[2] * Co1[2]
    M1_3= Row3[0] * Co1[0] + Row3[1] * Co1[1] + Row3[2] * Co1[2]
    M1_4 = Row1[0] * Co2[0] + Row1[1] * Co2[1] + Row1[2] * Co2[2]
    M1_5 = Row2[0] * Co2[0] + Row2[1] * Co2[1] + Row2[2] * Co2[2]
    M1_6 = Row3[0] * Co2[0] + Row3[1] * Co2[1] + Row3[2] * Co2[2]
    M1_7 = Row1[0] * Co3[0] + Row1[1] * Co3[1] + Row1[2] * Co3[2]
    M1_8 = Row2[0] * Co3[0] + Row2[1] * Co3[1] + Row2[2] * Co3[2]
    M1_9 = Row3[0] * Co3[0] + Row3[1] * Co3[1] + Row3[2] * Co3[2]
    M1_10 = Row1[0] * Co4[0] + Row1[1] * Co4[1] + Row1[2] * Co4[2]
    M1_11 = Row2[0] * Co4[0] + Row2[1] * Co4[1] + Row2[2] * Co4[2]
    M1_12 = Row3[0] * Co4[0] + Row3[1] * Co4[1] + Row3[2] * Co4[2]
    M1_13 = Row1[0] * Co5[0] + Row1[1] * Co5[1] + Row1[2] * Co5[2]
    M1_14 = Row2[0] * Co5[0] + Row2[1] * Co5[1] + Row2[2] * Co5[2]
    M1_15 = Row3[0] * Co5[0] + Row3[1] * Co5[1] + Row3[2] * Co5[2]
    M1_16 = Row1[0] * Co6[0] + Row1[1] * Co6[1] + Row1[2] * Co6[2]
    M1_17 = Row2[0] * Co6[0] + Row2[1] * Co6[1] + Row2[2] * Co6[2]
    M1_18 = Row3[0] * Co6[0] + Row3[1] * Co6[1] + Row3[2] * Co6[2]
    M1_19 = Row1[0] * Co7[0] + Row1[1] * Co7[1] + Row1[2] * Co7[2]
    M1_20 = Row2[0] * Co7[0] + Row2[1] * Co7[1] + Row2[2] * Co7[2]
    M1_21 = Row3[0] * Co7[0] + Row3[1] * Co7[1] + Row3[2] * Co7[2]
    M1_22 = Row1[0] * Co8[0] + Row1[1] * Co8[1] + Row1[2] * Co8[2]
    M1_23 = Row2[0] * Co8[0] + Row2[1] * Co8[1] + Row2[2] * Co8[2]
    M1_24 = Row3[0] * Co8[0] + Row3[1] * Co8[1] + Row3[2] * Co8[2]
    M1_25 = Row1[0] * Co9[0] + Row1[1] * Co9[1] + Row1[2] * Co9[2]
    M1_26 = Row2[0] * Co9[0] + Row2[1] * Co9[1] + Row2[2] * Co9[2]
    M1_27 = Row3[0] * Co9[0] + Row3[1] * Co9[1] + Row3[2] * Co9[2]
    M1_28 = Row1[0] * Co10[0] + Row1[1] * Co10[1] + Row1[2] * Co10[2]
    M1_29 = Row2[0] * Co10[0] + Row2[1] * Co10[1] + Row2[2] * Co10[2]
    M1_30 = Row3[0] * Co10[0] + Row3[1] * Co10[1] + Row3[2] * Co10[2]
    M1_31 = Row1[0] * Co11[0] + Row1[1] * Co11[1] + Row1[2] * Co11[2]
    M1_32 = Row2[0] * Co11[0] + Row2[1] * Co11[1] + Row2[2] * Co11[2]
    M1_33 = Row3[0] * Co11[0] + Row3[1] * Co11[1] + Row3[2] * Co11[2]
    M1_34 = Row1[0] * Co12[0] + Row1[1] * Co12[1] + Row1[2] * Co12[2]
    M1_35 = Row2[0] * Co12[0] + Row2[1] * Co12[1] + Row2[2] * Co12[2]
    M1_36 = Row3[0] * Co12[0] + Row3[1] * Co12[1] + Row3[2] * Co12[2]
    M1_37 = Row1[0] * Co13[0] + Row1[1] * Co13[1] + Row1[2] * Co13[2]
    M1_38 = Row2[0] * Co13[0] + Row2[1] * Co13[1] + Row2[2] * Co13[2]
    M1_39 = Row3[0] * Co13[0] + Row3[1] * Co13[1] + Row3[2] * Co13[2]
    M1_40 = Row1[0] * Co14[0] + Row1[1] * Co14[1] + Row1[2] * Co14[2]
    M1_41 = Row2[0] * Co14[0] + Row2[1] * Co14[1] + Row2[2] * Co14[2]
    M1_42 = Row3[0] * Co14[0] + Row3[1] * Co14[1] + Row3[2] * Co14[2]
    M1_43 = Row1[0] * Co15[0] + Row1[1] * Co15[1] + Row1[2] * Co15[2]
    M1_44 = Row2[0] * Co15[0] + Row2[1] * Co15[1] + Row2[2] * Co15[2]
    M1_45 = Row3[0] * Co15[0] + Row3[1] * Co15[1] + Row3[2] * Co15[2]

    rangeIndex = [0, 3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39, 42]

    CodedMessage = [M1_1, M1_2, M1_3, M1_4, M1_5, M1_6, M1_7, M1_8, M1_9, M1_10, M1_11, M1_12, M1_13, M1_14, M1_15, M1_16, M1_17, M1_18, M1_19, M1_20, M1_21, M1_22, M1_23, M1_24, M1_25, M1_26, M1_27, M1_28, M1_29, M1_30, M1_31, M1_32, M1_33, M1_34, M1_35, M1_36, M1_37, M1_38, M1_39, M1_40, M1_41, M1_42, M1_43, M1_44, M1_45]
    newMessage = str(CodedMessage).strip('[]')
    
    return newMessage

#csvfile = open("passwordData.csv", "r")
#codedPasswords = csv.reader(csvfile, delimiter=',')

#lines = []

#for line in passwordFile:
#    for item in line:
#        segment = []
#        segment.append(item)
#   lines.append(segment)

#csvfile.close()

f = open("codedPasswords.csv", "a")
passwordFile = csv.reader(csvfile, delimiter=',')

for line in passwordFile:

    #for item in lines:
     #   if item == line:
      #      break
       # break

    fName = line[0]
    codedfName = str(coder(fName))
    lName = line[1]
    codedlName = str(coder(lName))
    ID = line[2]
    teacher = line[3]

    f.write(str(codedfName) + "," + " " + "," + str(codedlName) + "," + " " + "," + str(ID) + "," + str(teacher))
    f.write("\n")

f.close()        
print "PROGRAM EXECUTED" 




    

    




    


    





        
            
    




    
    


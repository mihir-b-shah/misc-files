import easygui
import math
from fractions import Fraction
import string

easygui.msgbox("Welcome to the polynomial solver. Note: this solver works only for rational roots.")

coefficients = ['A', 'B', 'C', 'D',]
msg = 'Enter the coefficients of your third-degree equation.'
title = 'Cubic Solver'

userInput = easygui.multenterbox(msg, title, coefficients)

A = int(userInput[0])
B = int(userInput[1])
C = int(userInput[2])
D = int(userInput[3])

if A <= 0:
    Apos = -A
else:
    Apos = A
if D <= 0:
    Dpos = -D
else:
    Dpos = D

def factors(x):

    myFactors = []

    for i in range(1, x + 1):

        if x % i == 0:
            myFactors.append(i)

        else:
            pass

    return myFactors

rawA = factors(Apos)
rawD = factors(Dpos)

for item in rawA:
    item = int(item)

for item in rawD:
    item = int(item)

factors = []

for den in rawA:
    for num in rawD:

        num = float(num)
        den = float(den)

        newNum = num/den

        intorNot = newNum.is_integer()

        if intorNot == True:
            newNum = int(newNum)
            factors.append(newNum)

        if intorNot == False:

            num = int(num)
            den = int(den)
            
            fraction = Fraction(num,den)
            factors.append(fraction)

myneglist = [ -x for x in factors]

for item in myneglist:
    factors.append(item)

def Synthetic_Division(A, B, C, D, K):
    drop2 = float(A) * float(K)
    final2 = float(B) + float(drop2)
    drop3 = float(final2) * float(K)
    final3 = float(C) + float(drop3)
    drop4 = float(final3) * float(K)
    final4 = float(D) + float(drop4)

    return [A, final2, final3, final4]

factor = 0.0
a = 0.0
b = 0.0
c = 0.0

for item in factors:
    myList = Synthetic_Division(A, B, C, D, item)

    if float(myList[3]) == float(0.0):

        a = float(myList[0])
        b = float(myList[1])
        c = float(myList[2])
        factor = float(item)

        break

    else:
        continue

discriminant = float(b**2 - 4*a*c)

if discriminant >= 0:

    Root_1 = ((-b + math.sqrt(discriminant))/2*a)
    Root_2 = ((-b - math.sqrt(discriminant))/2*a)

    Root_1 = round(Root_1, 1)
    Root_2 = round(Root_2, 1)

msg1 = ("Roots are: " + str(Root_1) + " ," + str(Root_2) + " ," + str(factor))
msg2 = ("Roots are: " + str(factor) + " and two complex roots.")
title = ("Cubic Polynomial Root Finder")

if discriminant >= 0:
    easygui.msgbox(msg1, title)

if discriminant < 0:
    easygui.msgbox(msg2, title)

        

    




                 
                 

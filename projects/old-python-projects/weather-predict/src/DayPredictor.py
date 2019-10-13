#Imports necessary modules

import datetime
import json
import easygui
import math

x = easygui.buttonbox("Continue?",
                      choices = ['Continue', 'Cancel'])

while x == 'Continue':

    #Initializes and Defines Needed Dictionaries

    monthInformation = {}
    dayInformation = {}

    #Opens data file so information can be utilized

    f = open("weatherDataNew", "r")
    winterDatabase = json.load(f)

    #Collects user input and changes it to str format

    userInputLevel1 = easygui.enterbox("What month do you want information for (starting) (ex. Jan)?")
    userInputLevel2 = str(easygui.enterbox("Which day  - note - Feb 29, leap day, doesn't work.(ex. 01 or 29)?"))

    #Performs all operations using months, derives day database

    months = winterDatabase.keys()
    monthInformation = winterDatabase[userInputLevel1]
    days = monthInformation.keys()

    #Gets day and year informaton

    dayInformation = monthInformation[userInputLevel2]
    years = dayInformation.keys()

    #Initializes the lists

    dayHighs = []
    dayLows = []
    dayPrecip = []

    #Retrieves the yearly information

    for year in years:
        yearInfo = dayInformation[year]

        high = yearInfo['HighTemp']
        low = yearInfo['LowTemp']
        precip = yearInfo['Precipitation']

        high = int(high)
        low = int(low)
        precip = int(precip)

        #Appends the information and populates the list

        dayHighs.append(high)
        dayLows.append(low)
        dayPrecip.append(precip)

    #Calculates the average    

    sumHighs = math.fsum(dayHighs)
    sumLows = math.fsum(dayLows)
    sumPrecip = math.fsum(dayPrecip)

    lenHighs = len(dayHighs)
    lenLows = len(dayLows)
    lenPrecip = len(dayPrecip)
    
    avgHighs = sumHighs/lenHighs
    avgLows = sumLows/lenLows
    avgPrecip = sumPrecip/lenPrecip

    #Initializes the SD lists

    Oxs1 = []
    Oys2 = []
    Ozs3 = []

    #Retrieves the SDs

    for x in range(0, len(years)):
        point11 = dayHighs[x]
        point22 = dayLows[x]
        point33 = dayPrecip[x]

        OriginalOx1 = (point11 - avgHighs)**2
        OriginalOy2 = (point22 - avgLows)**2
        OriginalOz3 = (point33 - avgPrecip)**2

        #Appends the SDs

        Oxs1.append(OriginalOx1)
        Oys2.append(OriginalOy2)
        Ozs3.append(OriginalOz3)

    #Calculates the variance
        
    OrigVarX1 = math.fsum(Oxs1)
    OrigVarY2 = math.fsum(Oys2)
    OrigVarZ3 = math.fsum(Ozs3)

    VarX1 = OrigVarX1/len(Oxs1)
    VarY2 = OrigVarY2/len(Oys2)
    VarZ3 = OrigVarZ3/len(Ozs3)

    #Calculates the SD

    Ox1 = math.sqrt(VarX1)
    Oy2 = math.sqrt(VarY2)
    Oz3 = math.sqrt(VarZ3)

    #Calculates the 1 SD on both sides of the mean
     
    lbHigh = round(avgHighs - Ox1)
    ubHigh = round(avgHighs + Ox1)

    lbLow = round(avgLows - Oy2)
    ubLow = round(avgLows + Oy2)
    
    lbPrecip = round(avgPrecip *100 - Oz3)
    ubPrecip = round(avgPrecip *100 + Oz3)

    #Gives user information

    avgPrecip = avgPrecip * 100

    if avgPrecip <= 50:
        easygui.msgbox("The high for the day of " + str(userInputLevel1) + "-" + str(userInputLevel2)+ " is between " + str(lbHigh) + " and " + str(ubHigh) + "\n" + "The low is between " + str(lbLow) + " and " + str(ubLow) + "\n" + "The rain chance is between " + str(lbPrecip) + " and " + str(ubPrecip) + "\n" + "It will probably not rain.")
    else:
        easygui.msgbox("The high for the day of " + str(userInputLevel1) + "-" + str(userInputLevel2)+ " is between " + str(lbHigh) + " and " + str(ubHigh) + "\n" + "The low is between " + str(lbLow) + " and " + str(ubLow) + "\n" + "The rain chance is between " + str(lbPrecip) + " and " + str(ubPrecip) + "\n" + "It will probably rain.")

    x = easygui.buttonbox("Continue?",
                      choices = ['Continue', 'Cancel'])

    if x == 'Cancel':
        break
    if x == 'Continue':
        continue

    

#Imports all modules needed

import math
import datetime
import easygui
import json

x = easygui.buttonbox("Choose.",
                      choices = ['Continue', 'Cancel'])

while x == 'Continue':

    #Opens the file to access information

    f = open('weatherData', 'r')
    winterDatabase = json.load(f)

    #Initializes all variable dictionaries

    xList = {}
    yList = {}
    cList = {}
    hList = {}

    #Gets list of every day-month pair

    keyList = winterDatabase.keys()

    #Populates the 4 variable dicrionaries

    for dateKey in keyList:
        yearDatabase = winterDatabase[dateKey]
        yearKeyList = yearDatabase.keys()
        for yearKey in yearKeyList:
            infoDatabase = yearDatabase[yearKey]
            infoKeyList = infoDatabase.keys()
            information1 = infoDatabase["Pressure"]
            xList[str(dateKey) + "-" + str(yearKey)] = information1
            information2 = infoDatabase["Precipitation"]
            yList[str(dateKey) + "-" + str(yearKey)] = information2
            cloudCover = infoDatabase["CloudCover1-10"]
            cList[str(dateKey) + "-" + str(yearKey)] = cloudCover
            humidity = infoDatabase["Humidity"]
            hList[str(dateKey) + "-" + str(yearKey)] = humidity

    #Closes the file

    f.close()

    #Initializes all dictionaries in which to collect info
        
    dict1 = {}
    dict2 = {}
    dict3 = {}
    dict4 = {}
    dict5 = {}
    dict6 = {}
    dict7 = {}
    dict8 = {}
    dict9 = {}
    dict10 = {}
    dict11 = {}
    dict12 = {}
    dict13 = {}
    dict14 = {}

    userInput = easygui.enterbox("Enter pressure in (inHg)")
    userInput = float(userInput)

    dateList = xList.keys()
    for date in dateList:
        info = xList[date]
        info = float(info)
        if info >= 29.5 and info <= 29.6:
            info = str(info)
            dict1[date] = info
        if info >= 29.6 and info <= 29.7:
            info = str(info)
            dict2[date] = info
        if info >= 29.7 and info <= 29.8:
            info = str(info)
            dict3[date] = info
        if info >= 29.8 and info <= 29.9:
            info = str(info)
            dict4[date] = info
        if info >= 29.9 and info <= 30:
            info = str(info)
            dict5[date] = info
        if info >= 30 and info <= 30.1:
            info = str(info)
            dict6[date] = info
        if info >= 30.1 and info <= 30.2:
            info = str(info)
            dict7[date] = info
        if info >= 30.2 and info <= 30.3:
            info = str(info)
            dict8[date] = info
        if info >= 30.3 and info <= 30.4:
            info = str(info)
            dict9[date] = info
        if info >= 30.4 and info <= 30.5:
            info = str(info)
            dict10[date] = info
        if info >= 30.5 and info <= 30.6:
            info = str(info)
            dict11[date] = info
        if info >= 30.6 and info <= 30.7:
            info = str(info)
            dict12[date] = info
        if info >= 30.7 and info <= 30.8:
            info = str(info)
            dict13[date] = info
        if info >= 30.8 and info <= 30.9:
            info = str(info)
            dict14[date] = info

    dict1Values = dict1.values() 
    dict2Values = dict2.values() 
    dict3Values = dict3.values()
    dict4Values = dict4.values()
    dict5Values = dict5.values()
    dict6Values = dict6.values()
    dict7Values = dict7.values()
    dict8Values = dict8.values()
    dict9Values = dict9.values()
    dict10Values = dict10.values()
    dict11Values = dict11.values()
    dict12Values = dict12.values()
    dict13Values = dict13.values()
    dict14Values = dict14.values()

    dict1Keys = dict1.keys()
    dict2Keys = dict2.keys() 
    dict3Keys = dict3.keys()
    dict4Keys = dict4.keys()
    dict5Keys = dict5.keys()
    dict6Keys = dict6.keys()
    dict7Keys = dict7.keys()
    dict8Keys = dict8.keys()
    dict9Keys = dict9.keys()
    dict10Keys = dict10.keys()
    dict11Keys = dict11.keys()
    dict12Keys = dict12.keys()
    dict13Keys = dict13.keys()
    dict14Keys = dict14.keys()

    dict1RainValues = []
    dict2RainValues = []
    dict3RainValues = []
    dict4RainValues = []
    dict5RainValues = []
    dict6RainValues = []
    dict7RainValues = []
    dict8RainValues = []
    dict9RainValues = []
    dict10RainValues = []
    dict11RainValues = []
    dict12RainValues = []
    dict13RainValues = []
    dict14RainValues = []

    for key in dict1Keys:
        rainValue = yList[key]
        dict1RainValues.append(rainValue)
    for key in dict2Keys:
        rainValue = yList[key]
        dict2RainValues.append(rainValue)
    for key in dict3Keys:
        rainValue = yList[key]
        dict3RainValues.append(rainValue)
    for key in dict4Keys:
        rainValue = yList[key]
        dict4RainValues.append(rainValue)
    for key in dict5Keys:
        rainValue = yList[key]
        dict5RainValues.append(rainValue)
    for key in dict6Keys:
        rainValue = yList[key]
        dict6RainValues.append(rainValue)
    for key in dict7Keys:
        rainValue = yList[key]
        dict7RainValues.append(rainValue)
    for key in dict8Keys:
        rainValue = yList[key]
        dict8RainValues.append(rainValue)
    for key in dict9Keys:
        rainValue = yList[key]
        dict9RainValues.append(rainValue)
    for key in dict10Keys:
        rainValue = yList[key]
        dict10RainValues.append(rainValue)
    for key in dict11Keys:
        rainValue = yList[key]
        dict11RainValues.append(rainValue)
    for key in dict12Keys:
        rainValue = yList[key]
        dict12RainValues.append(rainValue)
    for key in dict13Keys:
        rainValue = yList[key]
        dict13RainValues.append(rainValue)
    for key in dict14Keys:
        rainValue = yList[key]
        dict14RainValues.append(rainValue)

    if userInput >= 29.5 and userInput <= 29.6:
        for x in range(0, len(dict1RainValues)):
            dict1RainValues[x] = float(dict1RainValues[x])
        sum1 = math.fsum(dict1RainValues)
        len1 = len(dict1RainValues)
        generatedChance1 = float(sum1/len1)
        percentChanceOriginal1 = generatedChance1*100
        percentChancep = round(percentChanceOriginal1)
    if userInput >= 29.6 and userInput <= 29.7:
        for x in range(0, len(dict2RainValues)):
            dict2RainValues[x] = float(dict2RainValues[x])
        sum2 = math.fsum(dict2RainValues)
        len2 = len(dict2RainValues)
        generatedChance2 = float(sum2/len2)
        percentChanceOriginal2 = generatedChance2*100
        percentChancep = round(percentChanceOriginal2)
    if userInput >= 29.7 and userInput <= 29.8:
        for x in range(0, len(dict3RainValues)):
            dict3RainValues[x] = float(dict3RainValues[x])
        sum3 = math.fsum(dict3RainValues)
        len3 = len(dict3RainValues)
        generatedChance3 = float(sum3/len3)
        percentChanceOriginal3 = generatedChance3*100
        percentChancep = round(percentChanceOriginal3)
    if userInput  >= 29.8 and userInput <= 29.9:
        for x in range(0, len(dict4RainValues)):
            dict4RainValues[x] = float(dict4RainValues[x])
        sum4 = math.fsum(dict4RainValues)
        len4 = len(dict4RainValues)
        generatedChance4 = float(sum4/len4)
        percentChanceOriginal4 = generatedChance4*100
        percentChancep = round(percentChanceOriginal4)
    if userInput  >= 29.9 and userInput <= 30:
        for x in range(0, len(dict5RainValues)):
            dict5RainValues[x] = float(dict5RainValues[x])
        sum5 = math.fsum(dict5RainValues)
        len5 = len(dict5RainValues)
        generatedChance5 = float(sum5/len5)
        percentChanceOriginal5 = generatedChance5*100
        percentChancep = round(percentChanceOriginal5)
    if userInput  >= 30 and userInput <= 30.1:
        for x in range(0, len(dict6RainValues)):
            dict6RainValues[x] = float(dict6RainValues[x])
        sum6 = math.fsum(dict6RainValues)
        len6 = len(dict6RainValues)
        generatedChance6 = float(sum6/len6)
        percentChanceOriginal6 = generatedChance6*100
        percentChancep = round(percentChanceOriginal6)
    if userInput  >= 30.1 and userInput <= 30.2:
        for x in range(0, len(dict7RainValues)):
            dict7RainValues[x] = float(dict7RainValues[x])
        sum7 = math.fsum(dict7RainValues)
        len7 = len(dict7RainValues)
        generatedChance7 = float(sum7/len7)
        percentChanceOriginal7 = generatedChance7*100
        percentChancep = round(percentChanceOriginal7)
    if userInput  >= 30.2 and userInput <= 30.3:
        for x in range(0, len(dict8RainValues)):
            dict8RainValues[x] = float(dict8RainValues[x])
        sum8 = math.fsum(dict8RainValues)
        len8 = len(dict8RainValues)
        generatedChance8 = float(sum8/len8)
        percentChanceOriginal8 = generatedChance8*100
        percentChancep = round(percentChanceOriginal8)
    if userInput  >= 30.3 and userInput <= 30.4:
        for x in range(0, len(dict9RainValues)):
            dict9RainValues[x] = float(dict9RainValues[x])
        sum9 = math.fsum(dict9RainValues)
        len9 = len(dict9RainValues)
        generatedChance9 = float(sum9/len9)
        percentChanceOriginal9 = generatedChance9*100
        percentChancep = round(percentChanceOriginal9)
    if userInput  >= 30.4 and userInput <= 30.5:
        for x in range(0, len(dict10RainValues)):
            dict10RainValues[x] = float(dict10RainValues[x])
        sum10 = math.fsum(dict10RainValues)
        len10 = len(dict10RainValues)
        generatedChance10 = float(sum10/len10)
        percentChanceOriginal10 = generatedChance10*100
        percentChancep = round(percentChanceOriginal10)
    if userInput  >= 30.5 and userInput <= 30.6:
        for x in range(0, len(dict11RainValues)):
            dict11RainValues[x] = float(dict11RainValues[x])
        sum11 = math.fsum(dict11RainValues)
        len11 = len(dict11RainValues)
        generatedChance11 = float(sum11/len11)
        percentChanceOriginal11 = generatedChance11*100
        percentChancep = round(percentChanceOriginal11)
    if userInput  >= 30.6 and userInput <= 30.7:
        for x in range(0, len(dict12RainValues)):
            dict12RainValues[x] = float(dict12RainValues[x])
        sum12 = math.fsum(dict12RainValues)
        len12 = len(dict12RainValues)
        generatedChance12 = float(sum12/len12)
        percentChanceOriginal12 = generatedChance12*100
        percentChancep = round(percentChanceOriginal12)
    if userInput  >= 30.7 and userInput <= 30.8:
        for x in range(0, len(dict13RainValues)):
            dict13RainValues[x] = float(dict13RainValues[x])
        sum13 = math.fsum(dict13RainValues)
        len13 = len(dict13RainValues)
        generatedChance13 = float(sum13/len13)
        percentChanceOriginal13 = generatedChance13*100
        percentChancep = round(percentChanceOriginal13)
    if userInput  >= 30.8 and userInput <= 30.9:
        for x in range(0, len(dict14RainValues)):
            dict14RainValues[x] = float(dict14RainValues[x])
        sum14 = math.fsum(dict14RainValues)
        len14 = len(dict14RainValues)
        generatedChance14 = float(sum14/len14)
        percentChanceOriginal14 = generatedChance14*100
        percentChancep = round(percentChanceOriginal14)

    cdict1 = {}
    cdict2 = {}
    cdict3 = {}
    cdict4 = {}
    cdict5 = {}
    cdict6 = {}
    cdict7 = {}
    cdict8 = {}
    cdict9 = {}

    cldCvrInput = easygui.enterbox("Enter cloud cover in oktas")
    cldCvrInput = float(cldCvrInput)

    cldCoverDates = cList.keys()
    for date in cldCoverDates:
        cldCvr = cList[date]
        cldCvr = float(cldCvr)
        if cldCvr == 0:
            cldCvr = str(cldCvr)
            cdict1[date] = cldCvr
        if cldCvr == 1:
            cldCvr = str(cldCvr)
            cdict2[date] = cldCvr
        if cldCvr == 2:
            cldCvr = str(cldCvr)
            cdict3[date] = cldCvr
        if cldCvr == 3:
            cldCvr = str(cldCvr)
            cdict4[date] = cldCvr
        if cldCvr == 4:
            cldCvr = str(cldCvr)
            cdict5[date] = cldCvr
        if cldCvr == 5:
            cldCvr = str(cldCvr)
            cdict6[date] = cldCvr
        if cldCvr == 6:
            cldCvr = str(cldCvr)
            cdict7[date] = cldCvr
        if cldCvr == 7:
            cldCvr = str(cldCvr)
            cdict8[date] = cldCvr
        if cldCvr == 8:
            cldCvr = str(cldCvr)
            cdict9[date] = cldCvr
            
    cdict1Values =cdict1.values() 
    cdict2Values = cdict2.values() 
    cdict3Values = cdict3.values()
    cdict4Values = cdict4.values()
    cdict5Values = cdict5.values()
    cdict6Values = cdict6.values()
    cdict7Values = cdict7.values()
    cdict8Values = cdict8.values()
    cdict9Values = cdict9.values()

    cdict1Keys = cdict1.keys()
    cdict2Keys = cdict2.keys() 
    cdict3Keys = cdict3.keys()
    cdict4Keys = cdict4.keys()
    cdict5Keys = cdict5.keys()
    cdict6Keys = cdict6.keys()
    cdict7Keys = cdict7.keys()
    cdict8Keys = cdict8.keys()
    cdict9Keys = cdict9.keys()

    cdict1RainValues = []
    cdict2RainValues = []
    cdict3RainValues = []
    cdict4RainValues = []
    cdict5RainValues = []
    cdict6RainValues = []
    cdict7RainValues = []
    cdict8RainValues = []
    cdict9RainValues = []

    for key in cdict1Keys:
        rainValue = yList[key]
        cdict1RainValues.append(rainValue)
    for key in cdict2Keys:
        rainValue = yList[key]
        cdict2RainValues.append(rainValue)
    for key in cdict3Keys:
        rainValue = yList[key]
        cdict3RainValues.append(rainValue)
    for key in cdict4Keys:
        rainValue = yList[key]
        cdict4RainValues.append(rainValue)
    for key in cdict5Keys:
        rainValue = yList[key]
        cdict5RainValues.append(rainValue)
    for key in cdict6Keys:
        rainValue = yList[key]
        cdict6RainValues.append(rainValue)
    for key in cdict7Keys:
        rainValue = yList[key]
        cdict7RainValues.append(rainValue)
    for key in cdict8Keys:
        rainValue = yList[key]
        cdict8RainValues.append(rainValue)
    for key in cdict9Keys:
        rainValue = yList[key]
        cdict9RainValues.append(rainValue)

    if cldCvrInput == 0:
        for x in range(0, len(cdict1RainValues)):
            cdict1RainValues[x] = float(cdict1RainValues[x])
        sum1 = math.fsum(cdict1RainValues)
        len1 = len(cdict1RainValues)
        generatedChance1 = float(sum1/len1)
        percentChanceOriginal1 = generatedChance1*100
        percentChancec = round(percentChanceOriginal1)
    if cldCvrInput == 1:
        for x in range(0, len(cdict2RainValues)):
            cdict2RainValues[x] = float(cdict2RainValues[x])
        sum2 = math.fsum(cdict2RainValues)
        len2 = len(cdict2RainValues)
        generatedChance2 = float(sum2/len2)
        percentChanceOriginal2 = generatedChance2*100
        percentChancec = round(percentChanceOriginal2)
    if cldCvrInput == 2:
        for x in range(0, len(cdict3RainValues)):
            cdict3RainValues[x] = float(cdict3RainValues[x])
        sum3 = math.fsum(cdict3RainValues)
        len3 = len(cdict3RainValues)
        generatedChance3 = float(sum3/len3)
        percentChanceOriginal3 = generatedChance3*100
        percentChancec = round(percentChanceOriginal3)
    if cldCvrInput == 3:
        for x in range(0, len(cdict4RainValues)):
            cdict4RainValues[x] = float(cdict4RainValues[x])
        sum4 = math.fsum(cdict4RainValues)
        len4 = len(cdict4RainValues)
        generatedChance4 = float(sum4/len4)
        percentChanceOriginal4 = generatedChance4*100
        percentChancec = round(percentChanceOriginal4)
    if cldCvrInput == 4:
        for x in range(0, len(cdict5RainValues)):
            cdict5RainValues[x] = float(cdict5RainValues[x])
        sum5 = math.fsum(cdict5RainValues)
        len5 = len(cdict5RainValues)
        generatedChance5 = float(sum5/len5)
        percentChanceOriginal5 = generatedChance5*100
        percentChancec = round(percentChanceOriginal5)
    if cldCvrInput == 5:
        for x in range(0, len(cdict6RainValues)):
            cdict6RainValues[x] = float(cdict6RainValues[x])
        sum6 = math.fsum(cdict6RainValues)
        len6 = len(cdict6RainValues)
        generatedChance6 = float(sum6/len6)
        percentChanceOriginal6 = generatedChance6*100
        percentChancec = round(percentChanceOriginal6)
    if cldCvrInput == 6:
        for x in range(0, len(cdict7RainValues)):
            cdict7RainValues[x] = float(cdict7RainValues[x])
        sum7 = math.fsum(cdict7RainValues)
        len7 = len(cdict7RainValues)
        generatedChance7 = float(sum7/len7)
        percentChanceOriginal7 = generatedChance7*100
        percentChancec = round(percentChanceOriginal7)
    if cldCvrInput == 7:
        for x in range(0, len(cdict8RainValues)):
            cdict8RainValues[x] = float(cdict8RainValues[x])
        sum8 = math.fsum(cdict8RainValues)
        len8 = len(cdict8RainValues)
        generatedChance8 = float(sum8/len8)
        percentChanceOriginal8 = generatedChance8*100
        percentChancec = round(percentChanceOriginal8)
    if cldCvrInput == 8:
        for x in range(0, len(cdict9RainValues)):
            cdict9RainValues[x] = float(cdict9RainValues[x])
        sum9 = math.fsum(cdict9RainValues)
        len9 = len(cdict9RainValues)
        generatedChance9 = float(sum9/len9)
        percentChanceOriginal9 = generatedChance9*100
        percentChancec = round(percentChanceOriginal9)

    hdict1 = {}
    hdict2 = {}
    hdict3 = {}
    hdict4 = {}
    hdict5 = {}
    hdict6 = {}
    hdict7 = {}
    hdict8 = {}
    hdict9 = {}
    hdict10 = {}

    humidityInput = easygui.enterbox("Enter humidity")
    humidityInput = float(humidityInput)

    humidityDates = hList.keys()

    for date in humidityDates:
        humidity = hList[date]
        humidity = float(humidity)
        if humidity >= 0 and humidity <= 10:
            humidity = str(humidity)
            hdict1[date] = humidity
        if humidity >= 10 and humidity <=20:
            humidity = str(humidity)
            hdict2[date] = humidity
        if humidity >= 20 and humidity <= 30:
            humidity = str(humidity)
            hdict3[date] = humidity
        if humidity >= 30 and humidity <= 40:
            humidity = str(humidity)
            hdict4[date] = humidity
        if humidity >= 40 and humidity <= 50:
            humidity = str(humidity)
            hdict5[date] = humidity
        if humidity >= 50 and humidity <= 60:
            humidity = str(humidity)
            hdict6[date] = humidity
        if humidity >= 60 and humidity <= 70:
            humidity = str(humidity)
            hdict7[date] = humidity
        if humidity >= 70 and humidity <= 80:
            humidity = str(humidity)
            hdict8[date] = humidity
        if humidity >= 80 and humidity <= 90:
            humidity = str(humidity)
            hdict9[date] = humidity
        if humidity >= 90 and humidity <= 100:
            humidity = str(humidity)
            hdict10[date] = humidity
            
    hdict1Values = hdict1.values() 
    hdict2Values = hdict2.values() 
    hdict3Values = hdict3.values()
    hdict4Values = hdict4.values()
    hdict5Values = hdict5.values()
    hdict6Values = hdict6.values()
    hdict7Values = hdict7.values()
    hdict8Values = hdict8.values()
    hdict9Values = hdict9.values()
    hdict10Values = hdict10.values()

    hdict1Keys = hdict1.keys()
    hdict2Keys = hdict2.keys() 
    hdict3Keys = hdict3.keys()
    hdict4Keys = hdict4.keys()
    hdict5Keys = hdict5.keys()
    hdict6Keys = hdict6.keys()
    hdict7Keys = hdict7.keys()
    hdict8Keys = hdict8.keys()
    hdict9Keys = hdict9.keys()
    hdict10Keys = hdict10.keys()

    hdict1RainValues = []
    hdict2RainValues = []
    hdict3RainValues = []
    hdict4RainValues = []
    hdict5RainValues = []
    hdict6RainValues = []
    hdict7RainValues = []
    hdict8RainValues = []
    hdict9RainValues = []
    hdict10RainValues = []

    for key in hdict1Keys:
        rainValue = yList[key]
        hdict1RainValues.append(rainValue)
    for key in hdict2Keys:
        rainValue = yList[key]
        hdict2RainValues.append(rainValue)
    for key in hdict3Keys:
        rainValue = yList[key]
        hdict3RainValues.append(rainValue)
    for key in hdict4Keys:
        rainValue = yList[key]
        hdict4RainValues.append(rainValue)
    for key in hdict5Keys:
        rainValue = yList[key]
        hdict5RainValues.append(rainValue)
    for key in hdict6Keys:
        rainValue = yList[key]
        hdict6RainValues.append(rainValue)
    for key in hdict7Keys:
        rainValue = yList[key]
        hdict7RainValues.append(rainValue)
    for key in hdict8Keys:
        rainValue = yList[key]
        hdict8RainValues.append(rainValue)
    for key in hdict9Keys:
        rainValue = yList[key]
        hdict9RainValues.append(rainValue)
    for key in hdict10Keys:
        rainValue = yList[key]
        hdict10RainValues.append(rainValue)

    if humidityInput >= 0 and humidityInput <= 10:
        for x in range(0, len(hdict1RainValues)):
            hdict1RainValues[x] = float(hdict1RainValues[x])
        sum1 = math.fsum(hdict1RainValues)
        len1 = len(hdict1RainValues)
        generatedChance1 = float(sum1/len1)
        percentChanceOriginal1 = generatedChance1*100
        percentChanceh = round(percentChanceOriginal1)
    if humidityInput >= 10 and humidityInput <= 20:
        for x in range(0, len(hdict2RainValues)):
            hdict2RainValues[x] = float(hdict2RainValues[x])
        sum2 = math.fsum(hdict2RainValues)
        len2 = len(hdict2RainValues)
        generatedChance2 = float(sum2/len2)
        percentChanceOriginal2 = generatedChance2*100
        percentChanceh = round(percentChanceOriginal2)
    if humidityInput >= 20 and humidityInput <= 30:
        for x in range(0, len(hdict3RainValues)):
            hdict3RainValues[x] = float(hdict3RainValues[x])
        sum3 = math.fsum(hdict3RainValues)
        len3 = len(hdict3RainValues)
        generatedChance = float(sum3/len3)
        percentChanceOriginal3 = generatedChance3*100
        percentChanceh = round(percentChanceOriginal3)
    if humidityInput >= 30 and humidityInput <= 40:
        for x in range(0, len(hdict4RainValues)):
            hdict4RainValues[x] = float(hdict4RainValues[x])
        sum4 = math.fsum(hdict4RainValues)
        len4 = len(hdict4RainValues)
        generatedChance = float(sum4/len4)
        percentChanceOriginal4 = generatedChance4*100
        percentChanceh = round(percentChanceOriginal4)
    if humidityInput >= 40 and humidityInput <= 50:
        for x in range(0, len(hdict5RainValues)):
            hdict5RainValues[x] = float(hdict5RainValues[x])
        sum5 = math.fsum(hdict5RainValues)
        len5 = len(hdict5RainValues)
        generatedChance = float(sum5/len5)
        percentChanceOriginal5 = generatedChance*100
        percentChanceh = round(percentChanceOriginal5)
    if humidityInput >= 50 and humidityInput <= 60:
        for x in range(0, len(hdict6RainValues)):
            hdict6RainValues[x] = float(hdict6RainValues[x])
        sum6 = math.fsum(hdict6RainValues)
        len6 = len(hdict6RainValues)
        generatedChance6 = float(sum6/len6)
        percentChanceOriginal6 = generatedChance6*100
        percentChanceh = round(percentChanceOriginal6)
    if humidityInput >= 60 and humidityInput <= 70:
        for x in range(0, len(hdict7RainValues)):
            hdict7RainValues[x] = float(hdict7RainValues[x])
        sum7 = math.fsum(hdict7RainValues)
        len7 = len(hdict7RainValues)
        generatedChance7 = float(sum7/len7)
        percentChanceOriginal7 = generatedChance7*100
        percentChanceh = round(percentChanceOriginal7)
    if humidityInput >= 70 and humidityInput <= 80:
        for x in range(0, len(hdict8RainValues)):
            hdict8RainValues[x] = float(hdict8RainValues[x])
        sum8 = math.fsum(hdict8RainValues)
        len8 = len(hdict8RainValues)
        generatedChance8 = float(sum8/len8)
        percentChanceOriginal8 = generatedChance8*100
        percentChanceh = round(percentChanceOriginal8)
    if humidityInput >= 80 and humidityInput <= 90:
        for x in range(0, len(hdict9RainValues)):
            hdict9RainValues[x] = float(hdict9RainValues[x])
        sum9 = math.fsum(hdict9RainValues)
        len9 = len(hdict9RainValues)
        generatedChance9 = float(sum9/len9)
        percentChanceOriginal9 = generatedChance9*100
        percentChanceh = round(percentChanceOriginal9)
    if humidityInput >= 90 and humidityInput <= 100:
        for x in range(0, len(hdict10RainValues)):
            hdict10RainValues[x] = float(hdict10RainValues[x])
        sum10 = math.fsum(hdict10RainValues)
        len10 = len(hdict10RainValues)
        generatedChance10 = float(sum10/len10)
        percentChanceOriginal10 = generatedChance10*100
        percentChanceh = round(percentChanceOriginal10)

    finalizedChance = percentChancep*0.2 + percentChancec*0.2 + percentChanceh*0.6

    lowerBound = 0.8*finalizedChance
    upperBound = 1.2*finalizedChance

    if upperBound >= 100:
        upperBound = 100

    if finalizedChance <= 50:
        easygui.msgbox("The chance is predicted to be between " + str(lowerBound) + " and " + str(upperBound) + " percent." + "\n" + "It will probably not rain.")
    else:
        easygui.msgbox("The chance is predicted to be between " + str(lowerBound) + " and " + str(upperBound) + " percent." + "\n" + "It will probably rain.")

    x = easygui.buttonbox("Choose.",
                      choices = ['Continue', 'Cancel'])

    if x == 'Cancel':
        break
    elif x == 'Continue':
        continue

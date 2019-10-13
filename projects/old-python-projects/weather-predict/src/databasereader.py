import datetime
import json
import easygui

f = open("weatherData", "r")
winterDatabase = json.load(f)

userInputLevel1 = easygui.enterbox("What date do you want information for (ex. 01-Jan)?")
userInputLevel2 = easygui.enterbox("Which year do you want information for?")

dateInformation = winterDatabase[userInputLevel1]
yearInformation = dateInformation[userInputLevel2]
requestedData1 = yearInformation['Pressure']
requestedData2 = yearInformation['CloudCover1-10']
requestedData3 = yearInformation['Humidity']
requestedData4 = yearInformation['Precipitation']
requestedData5 = yearInformation['MeanTemp']

requestedData4 = str(requestedData4)

if requestedData4 == '0':
    easygui.msgbox("For the date of " + str(userInputLevel1) + " " + str(userInputLevel2) + ":" + "\n" + "The pressure is " + str(requestedData1) + " in Hg" + "\n" + "The cloud cover is " + str(requestedData2) + " oktas" + "\n" + "The humidity is " + str(requestedData3) + " percent" + "\n" + "It did not rain." + "\n" + " The mean temperature was " + str(requestedData5)) 
if requestedData4 == '1':
    easygui.msgbox("For the date of " + str(userInputLevel1) + " " + str(userInputLevel2) + ":" + "\n" + "The pressure is " + str(requestedData1) + " in Hg" + "\n" + "The cloud cover is " + str(requestedData2) + " oktas" + "\n" + "The humidity is " + str(requestedData3) + " percent" + "\n" + "It rained." + "\n" + " The mean temperature was " + str(requestedData5))

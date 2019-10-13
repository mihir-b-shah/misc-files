#Imports necessary modules

import datetime
import json
import easygui
import math

#Initializes and Defines Needed Dictionaries

monthInformation = {}
dayInformation = {}

#Opens data file so information can be utilized

f = open("weatherDataNew", "r")
winterDatabase = json.load(f)

#Collects user input and changes it to str format

userInputLevel1 = easygui.enterbox("What month do you want information for (starting) (ex. Jan)?")

#Performs all operations using months, derives day database

months = winterDatabase.keys()
monthInformation = winterDatabase[userInputLevel1]
days = monthInformation.keys()

days.sort()

#Gets day and year information

years = ['2005', '2006', '2007', '2008', '2009' ,'2010', '2011', '2012', '2013', '2014', '2015']

#Sets up the CSV file

filename = userInputLevel1 + "-climate.csv"
g = open(filename, "w")
g.write("day,high,low\n")

#Iterates over days list

for day in days:
    day = str(day)
    
    dayInformation = monthInformation[day]

    #Gets the sum of the temperatures
	
    highTempSum = 0
    lowTempSum = 0
    
    numYears = len(years)
    for year in years:
        
        yearInformation = dayInformation[year]

        #Extracts the temperature and precipitation
        
        HighTemp = int(yearInformation['HighTemp'])
        LowTemp = int(yearInformation['LowTemp'])
                
        #Appends info to list for summing

        highTempSum = highTempSum + HighTemp
        lowTempSum = lowTempSum + LowTemp

	Writes to the CSV file	
		
    avgHighTemp = highTempSum / numYears
    avgLowTemp = lowTempSum / numYears
    g.write(day + "," + str(avgHighTemp) + "," + str(avgLowTemp) + "\n")

#Closes the files

f.close()	
g.close()


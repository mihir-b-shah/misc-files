#Import necessary modules

import csv
import datetime
import json

#Initializes major database

winterDatabase = {}

#Opens the CSV file and assigns it to an object

csvfile = open('winter10YearsData.csv', 'r+')
weatherReader = csv.reader(csvfile, delimiter=',')

#Iterates over the CSV file object

for line in weatherReader:

    #Locates the date

    today = line[0]

    #Creates a date object by stripping the date
    
    dateObj = datetime.datetime.strptime(today, "%m/%d/%Y")

    day = dateObj.day
    month = dateObj.month
    year = dateObj.year

    #Formats the dates as wanted

    formattedDate = dateObj.strftime("%b")
    newformatDate = dateObj.strftime("%d")

    #Develops the 1st Hierarchy of Database: Months
    
    if formattedDate in winterDatabase:
        secondaryDatabase = winterDatabase[formattedDate]
    else:
        secondaryDatabase  = {}
        winterDatabase[formattedDate] = secondaryDatabase

    #Develops the 2nd Hierarchy of Database: Days

    if newformatDate in secondaryDatabase:
        tertiaryData = secondaryDatabase[newformatDate]
    else:
        tertiaryData = {}
        secondaryDatabase[newformatDate] = tertiaryData

    #Develops the 3rd Hierarchy of Database: Years

    if year in tertiaryData:
        actualData = tertiaryData[year]

    else:
        actualData = {}
        tertiaryData[year] = actualData

    #Develops the 4th and Final Level of Hierarchy of Database: Data Keys
        
    actualData['HighTemp'] = line[1]
    actualData['LowTemp'] = line[3]
    actualData['MeanTemp'] = line[2]
    actualData['Humidity'] = line[8]
    actualData['Pressure'] = line[11]
    actualData['GustSpeed'] = line[18]

    #Changes the Precipitation Data to a 0 or 1 statement

    if line[19] == '0' or line[19] == '0.0':
         actualData['Precipitation'] = '0'
    else:
        actualData['Precipitation'] = '1'

    #More developing the 4th Level of Hierarchy of Database: Data Keys
    
    actualData['CloudCover1-10'] = line[20]
    actualData['Events'] = line[21]
    actualData['WindDirection'] = line[22]

#Opens a file and assigns an object to it

f = open("weatherDataNew", "w")

#Dumps the database to the file and closes the file

json.dump(winterDatabase, f)
f.close()
    
    
        

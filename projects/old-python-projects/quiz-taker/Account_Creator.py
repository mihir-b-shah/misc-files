import time
import easygui
import json
import math
import random
import os
import csv
import sys

randInt = str(random.randint(1000, 9999))

Sort1 = easygui.buttonbox("Do you need to create an account or access your materials?",
                  choices = ['Create Account', 'Access my account'])

SoT = 0
j = open("teacherCodes.csv", "a")
j.write(str(randInt))
j.write("\n")
j.close()

if Sort1 == 'Create Account':
    
    FName = easygui.enterbox("Please enter your first name.")
    LName = easygui.enterbox("Please enter your last name.")

    NewName = str(FName + " " + LName)

    Password = str(easygui.enterbox("Please enter your 6 digit ID"))
    ConfirmPassword = str(easygui.enterbox("Please confirm your ID"))

    while Password != ConfirmPassword:

        easygui.msgbox("The password doesn't match. Try again.")

        Password = str(easygui.enterbox("Please enter your ID"))
        ConfirmPassword = str(easygui.enterbox("Please confirm your ID"))

    idDatabase = {}

    csvfile = open('passwordData.csv', 'r')
    passwordReader = csv.reader(csvfile, delimiter=',')

    for line in passwordReader:

        RName = str(line[0])
        RLName = str(line[1])
        ID = str(line[2])

        Name = str(RName + " " + RLName)
        
        idDatabase[Name] = ID

    persons = idDatabase.keys()
    ids = idDatabase.values()

    for item in ids:
        item = str(item)
        while item == Password:
            
            easygui.msgbox("Try creating an account with a different ID.")
            newPasscode = easygui.enterbox("Enter a different 6 digit ID.")

            if newPasscode != item:
                break
    
    for person in persons:
        person = str(person)
        while person == NewName:
            easygui.msgbox("You have already registered.")
            sys.exit()

    AdminCode = str(easygui.enterbox("If you are an administrator, enter your school code."))
    
    if AdminCode == randInt:
        SoT = '1'

    elif AdminCode != randInt:
        SoT = '0'

    f = open("passwordData.csv", "a")
    f.write(str(FName) + "," + str(LName) + "," + str(Password) + "," + str(SoT))
    f.write("\n")

    f.close()

elif Sort1 == 'Access my account':

    personDatabase = {}

    csvfile = open('passwordData.csv', 'r')
    passwordReader = csv.reader(csvfile, delimiter=',')

    for line in passwordReader:

        FName = str(line[0])
        LName = str(line[1])
        Password = str(line[2])

        Name = str(str(FName) + " " + str(LName))
        ID = Password
        
        personDatabase[Name] = ID

    idList = personDatabase.values()
    people = personDatabase.keys()
    lenList = len(idList)
    iterationCt = 0
    userInput = easygui.enterbox("Please enter your ID.")

    for item in idList:
        if item == userInput:

            for person in people:
                
                idItem = personDatabase[person]

                if idItem == item:
                    monster = person
                    break
                elif idItem != item:
                    continue
        
            AccountChoice = easygui.buttonbox(("Welcome, " + str(monster)),
                                              choices = ['Create sets', 'View sets'])

            if AccountChoice == 'Create sets':
                
                easygui.msgbox("This is a system allowing you to generate quizzes.")
                quizName = easygui.enterbox("Enter the name of the quiz.")

                terms = int(easygui.enterbox("How many terms do you wish to enter?"))
                termNow = 0

                quizAnswers = {}

                while termNow != terms:

                    key = easygui.enterbox("Please enter the question.")
                    value = easygui.enterbox("Please enter the answer.")

                    quizAnswers[key] = value

                    z = easygui.choicebox("You entered: " + "\n" + "\n" + str(key) + " as the question. " + "\n" + "and " + str(value) + " as the answer. ",
                                          choices = ['Confirm', 'Cancel'])

                    if z == 'Confirm':
                        termNow = termNow + 1

                    elif z == 'Cancel':
                        termNow = termNow + 0

            
                exists = os.path.isdir('C:/Python27/Quiz Sets/' + monster + "/")                
                if exists == True:
                    pass
                elif exists == False:
                    os.mkdir(('C:/Python27/Quiz Sets/' + str(monster) + "/"), 0755)
                    
                save_path = ('C:/Python27/Quiz Sets/'+ str(monster) + "/")
                completeName = os.path.join(save_path, quizName)
                f = open((str(completeName)), "w")
                json.dump(quizAnswers, f)

                f.close()

                g = open((str(quizName) + "- Responses"), "w")
                g.close()

            elif AccountChoice == 'View sets':

                exists = os.path.isdir('C:/Python27/Quiz Sets/' + monster + "/")

                print exists
                
                if exists == True:
                    pass
                elif exists == False:
                    os.mkdir(('C:/Python27/Quiz Sets/' + str(monster) + "/"), 0755)
                
                files = os.listdir('C:/Python27/Quiz Sets/' + monster + "/")                
                easygui.msgbox("Your sets: " + str(files))

            break

        elif userInput != item:
            
            iterationCt = iterationCt + 1
            continue

        elif iterationCt == lenList:
            break

            easygui.msgbox("Your ID was not found. Please try later.")
        
        




    


    

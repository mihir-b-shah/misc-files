import easygui
import json
import os.path

easygui.msgbox("This is a system allowing you to generate multiple choice quizzes.")
name = easygui.enterbox("Enter the name of the quiz.")

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
    
save_path = 'C:/Python27/Quiz Sets/'
completeName = os.path.join(save_path, name)
f = open((str(completeName)), "w")
json.dump(quizAnswers, f)

f.close()

g = open((str(name) + "- Responses"), "w")
g.close()
         



    


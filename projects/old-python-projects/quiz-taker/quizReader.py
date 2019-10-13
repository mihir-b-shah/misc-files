import easygui
import json
import random
import math

name = easygui.enterbox("Enter the exact name of the quiz.")
person = easygui.enterbox("Enter your name like this: First Last, ex. Bob Jones")

f = open(name, "r")
quizDatabase = json.load(f)

f.close()

quizPoint = quizDatabase.keys()
random.shuffle(quizPoint)
correctAnswers = []
dumperData = {}
mainDatabase = {}
problemInfo = {}

for x in range(0, len(quizPoint)):

    question = quizPoint[x]   

    answers = quizDatabase.values()
    newAnswers = str(answers)

    correctAnswer = True

    nums = []
    wrongAnswers = []
    wrongAnswer = True

    questionList = []

    correctAnswer = quizDatabase[question]

    newAnswers = answers
    newAnswers.remove(correctAnswer)

    wrongAnswers = random.sample(newAnswers, 3)

    questionList.append(correctAnswer)
    questionList.extend(wrongAnswers)

    random.shuffle(questionList)

    A1 = questionList[0]
    A2 = questionList[1]
    A3 = questionList[2]
    A4 = questionList[3]

    y = easygui.buttonbox((question + "\n" + "Pick one of these choices." + "\n" + "\n" + "A) " + A1 + "\n" + "B) " + A2 + "\n" + "C) " + A3 + "\n" + "D) " + A4),
                          choices = ["A", "B", "C", "D"])
    
    if y == "A":
        dumperData[question] = problemInfo
        if correctAnswer == A1:
            problemInfo['CoI'] = 'Correct'
            correctAnswers.append(1)
        else:
            problemInfo['CoI'] = 'Incorrect'
            correctAnswers.append(0)
        problemInfo['UserAns'] = A1
        problemInfo['CorrAns'] = correctAnswer
        
    if y == "B":
        dumperData[question] = problemInfo
        if correctAnswer == A2:
            problemInfo['CoI'] = 'Correct'
            correctAnswers.append(1)
        else:
            problemInfo['CoI'] = 'Incorrect'
            correctAnswers.append(0)
        problemInfo['UserAns'] = A2
        problemInfo['CorrAns'] = correctAnswer
    if y == "C":
        dumperData[question] = problemInfo
        if correctAnswer == A3:
            problemInfo['CoI'] = 'Correct'
            correctAnswers.append(1)
        else:
            problemInfo['CoI'] = 'Incorrect'
            correctAnswers.append(0)
        problemInfo['UserAns'] = A3
        problemInfo['CorrAns'] = correctAnswer
    if y == "D":
        dumperData[question] = problemInfo
        if correctAnswer == A4:
            problemInfo['CoI'] = 'Correct'
            correctAnswers.append(1)
        else:
            problemInfo['CoI'] = 'Incorrect'
            correctAnswers.append(0)
        problemInfo['UserAns'] = A4
        problemInfo['CorrAns'] = correctAnswer

answerSum = math.fsum(correctAnswers)
answerLen = float(len(correctAnswers))

average = answerSum/answerLen
average = round(average*100, 0)

dumperData['Score'] = average

print mainDatabase
mainDatabase[person] = dumperData

print mainDatabase

json.dump(mainDatabase, g)
g.close()
















    
    
    

    
    

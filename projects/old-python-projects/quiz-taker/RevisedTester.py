import easygui
import json
import random
import math
import time

name = easygui.fileopenbox("Find the file.")
person = easygui.enterbox("Enter your name like this: First Last, ex. Bob Jones")

f = open(name, "r")
quizDatabase = json.load(f)

f.close()

quizType = easygui.buttonbox("Choose quiz type.",
                             choices = ['Multiple-Choice', 'Written'])

if quizType == 'Multiple-Choice':
    
    quizPoint = quizDatabase.keys()
    random.shuffle(quizPoint)
    correctAnswers = []
    dumperData = {}
    mainDatabase = {}
    problemInfo = {}

    g = open(name + "- Responses", "a")
    g.write(name + ":")
    g.write("\n")
    g.write("\n")
    g.write(person)
    g.write("\n")
    g.write("\n")

    for x in range(0, len(quizPoint)):
        
        question = quizPoint[x]

        g.write("Question " + str(x+1) + ":" + question)
        g.write("\n")
        g.write("\n")

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
                g.write("Correct.")
                g.write("\n")
                g.write("User Ans: " + str(A1))
                g.write("\n")
                g.write("\n")
                g.write("\n")
            else:
                problemInfo['CoI'] = 'Incorrect'
                correctAnswers.append(0)
                g.write("Incorrect.")
                g.write("\n")
                g.write("User Ans: " + str(A1))
                g.write("\n")
                g.write("Correct Ans: " + correctAnswer)
                g.write("\n")
                g.write("\n")
                g.write("\n")
            problemInfo['UserAns'] = A1
            problemInfo['CorrAns'] = correctAnswer
            
        if y == "B":
            dumperData[question] = problemInfo
            if correctAnswer == A2:
                problemInfo['CoI'] = 'Correct'
                correctAnswers.append(1)
                g.write("Correct.")
                g.write("\n")
                g.write("User Ans: " + str(A2))
                g.write("\n")
                g.write("\n")
                g.write("\n")
            else:
                problemInfo['CoI'] = 'Incorrect'
                correctAnswers.append(0)
                g.write("Incorrect.")
                g.write("\n")
                g.write("User Ans: " + str(A2))
                g.write("\n")
                g.write("Correct Ans: " + correctAnswer)
                g.write("\n")
                g.write("\n")
                g.write("\n")
            problemInfo['UserAns'] = A2
            problemInfo['CorrAns'] = correctAnswer
            
        if y == "C":
            dumperData[question] = problemInfo
            if correctAnswer == A3:
                problemInfo['CoI'] = 'Correct'
                correctAnswers.append(1)
                g.write("Correct.")
                g.write("\n")
                g.write("User Ans: " + str(A3))
                g.write("\n")
                g.write("\n")
                g.write("\n")
            else:
                problemInfo['CoI'] = 'Incorrect'
                correctAnswers.append(0)
                g.write("Incorrect.")
                g.write("\n")
                g.write("User Ans: " + str(A3))
                g.write("\n")
                g.write("Correct Ans: " + correctAnswer)
                g.write("\n")
                g.write("\n")
                g.write("\n")
            problemInfo['UserAns'] = A3
            problemInfo['CorrAns'] = correctAnswer
        if y == "D":
            dumperData[question] = problemInfo
            if correctAnswer == A4:
                problemInfo['CoI'] = 'Correct'
                correctAnswers.append(1)
                g.write("Correct.")
                g.write("\n")
                g.write("User Ans: " + str(A4))
                g.write("\n")
                g.write("\n")
                g.write("\n")
            else:
                problemInfo['CoI'] = 'Incorrect'
                correctAnswers.append(0)
                g.write("Incorrect.")
                g.write("\n")
                g.write("User Ans: " + str(A4))
                g.write("\n")
                g.write("Correct Ans: " + correctAnswer)
                g.write("\n")
                g.write("\n")
                g.write("\n")
            problemInfo['UserAns'] = A4
            problemInfo['CorrAns'] = correctAnswer

    answerSum = math.fsum(correctAnswers)
    answerLen = float(len(correctAnswers))

    average = answerSum/answerLen
    average = round(average*100, 0)

    g.write("\n")
    g.write("Score: " + str(average))
    g.write("\n")
    g.write("\n")
    g.write("\n")

    easygui.msgbox("Thank you for completing this quiz." + "\n" + "Your score is " + str(average))

if quizType == 'Written':

    mainDatabase = {}
    
    quizKeys = quizDatabase.keys()
    quizValues = quizDatabase.values()

    random.shuffle(quizKeys)

    g = open(name + "- Responses", "a")
    g.write(name + ":")
    g.write("\n")
    g.write("\n")
    g.write(person)
    g.write("\n")
    g.write("\n")

    correctAnswers = 0
        
    for key in quizKeys:

        answer = easygui.enterbox(key)
        correctAnswer = quizDatabase[key]

        if answer == correctAnswer:
            correctAnswers = correctAnswers + 1

            g.write("Correct.")
            g.write("\n")
            g.write("User Ans: " + str(correctAnswer))
            g.write("\n")
            g.write("\n")
            g.write("\n")


        elif answer != correctAnswer:
            correctAnswers = correctAnswers + 0

            g.write("Incorrect.")
            g.write("\n")
            g.write("User Ans: " + str(answer))
            g.write("\n")
            g.write("Correct Answer: " + str(correctAnswer))
            g.write("\n")
            g.write("\n")
            g.write("\n")

    totalQuestions = len(quizKeys)
    numCorrect = int(correctAnswers)

    average = float(numCorrect/totalQuestions)
    average = round(average*100, 0)

    g.write("\n")
    g.write("Score: " + str(average))
    g.write("\n")
    g.write("\n")
    g.write("\n")

    easygui.msgbox("Thank you for completing this quiz." + "\n" + "Your score is " + str(average))

g.close()
time = time.asctime()

h = open("Student Responses.csv", "a")
h.write(str(time) + "," + str(name) + "," + str(person) + "," + str(average) + "%")
h.write("\n")
h.close()












    
    
    

    
    

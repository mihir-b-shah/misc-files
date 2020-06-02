// CALCULATOR.ASM
//==========================================================
	.orig	x3000


// MAIN SUBROUTINE
//----------------------------------------------------------
MAIN	LD R0, TESTADDR
	JSRR R0

	//Insert additional tests here...
//	AND R2, R2, #0	//
//	ADD R1, R2, #15	// R1 = 15 (example data)
//	ADD R2, R2, #15	// R2 = 15 (example data)
//	JSR PLUS	// R0 = R1 + R2
//	JSR PRINT	// Should print "30"
//
//	JSR PAUSE	// Pause until a key is pressed
	//--------------------------------------------------

	LD R0, STOPADDR
	JSRR R0		// Halt the processor

TESTADDR .fill TEST
STOPADDR .fill STOP


// SAMPLE ARITHMETIC SUBROUTINES (IMPRACTICAL TO USE)
//==========================================================

// NEGATION...
//  Precondition: R1 = x
// Postcondition: R0 = -x
//                Registers R1 through R7 remain unchanged
NEG	NOT R0, R1
	ADD R0, R0, #1
	RET


// ADDITION...
//  Precondition: R1 = x
//                R2 = y
// Postcondition: R0 = x + y
//                Registers R1 through R7 remain unchanged
PLUS	ADD R0, R1, R2
	RET


// BASIC ARITHMETIC SUBROUTINES
//==========================================================

// SUBTRACTION...
//  Precondition: R1 = x
//                R2 = y
// Postcondition: R0 = x - y
//                Registers R1 through R7 remain unchanged
SUB	NOT R0 R2
	ADD R0 R0 #1
	ADD R0 R0 R1
	RET


// MULTIPLICATION...
//  Precondition: R1 = x
//                R2 = y
// Postcondition: R0 = x * y
//                Registers R1 through R7 remain unchanged

MULT	AND R0 R0 #0
	ST R2 R2DAT
	ST R6 R6DAT
	AND R6 R6 #0
	ADD R6 R7 R6
	ADD R2 R2 #0
	JSR #0
	BRn NEGATE
	BRnzp LOOP
	RET

LOOP	ADD R2 R2 #0
	BRz FINISH
	ADD R0 R0 R1
	ADD R2 R2 #-1
	BRnp LOOP

FINISH	LD R2 R2DAT
	AND R7 R7 #0
	ADD R7 R6 R7
	AND R6 R6 #0
	LD R6 R6DAT
	RET

NEGATE  NOT R1 R1
	ADD R1 R1 #1
	NOT R2 R2
	ADD R2 R2 #1
	RET

// Data

R2DAT	.fill #0
R6DAT   .fill #0

// DIVISION...
//  Precondition: R1 = x
//                R2 = y
// Postcondition: If y != 0, then R0 = x / y
//                If y == 0, then prints an error message and sets R0 = 0
//                Registers R1 through R7 remain unchanged

DIV	ADD R2 R2 #0	
	ST R7 R7DATA
	BRz ERR	
	ST R1 R1DATA
	ST R2 R2DATA
	ST R3 R3DATA
	AND R0 R0 #0
	AND R3 R3 #0	
	ADD R3 R3 #1
	ADD R1 R1 #0
	BRn INV
	BRnzp NXT
	
NXT	ADD R2 R2 #0
	BRn NG

ACT	NOT R2 R2
	ADD R2 R2 #1
	BRnzp LP

LP	ADD R0 R0 #1
	ADD R1 R1 R2
	ADD R1 R1 #0
	BRnz FN
	BRnzp LP

FN	ADD R1 R1 #0
	BRn DCR

FN2	ADD R3 R3 #0
	BRn ACFLP
	BRnzp RLD

ACFLP	NOT R0 R0
	ADD R0 R0 #1

RLD	LD R2 R2DATA
	AND R1 R1 #0
	ST R0 PROD
	ADD R1 R0 R1
	JSR MULT
	LD R1 R1DATA
	AND R2 R2 #0
	ADD R2 R0 R2
	JSR SUB
	ST R0 MD
	LD R0 PROD

DNE	LD R3 R3DATA
	LD R1 R1DATA
	LD R2 R2DATA
	LD R7 R7DATA
	RET

INV	NOT R1 R1
	ADD R1 R1 #1
	NOT R2 R2
	ADD R2 R2 #1
	BRnzp NXT

NG	NOT R2 R2
	ADD R2 R2 #1
	NOT R3 R3
	ADD R3 R3 #1
	BRnzp ACT

DCR	ADD R0 R0 #-1
	BRnzp FN2

ERR	LEA R0 ERROR
	PUTS
	AND R0 R0 #0
	ST R0 MD
	LD R7 R7DATA
	RET

// DATA FOR THE DIVISION SUBROUTINE...

R1DATA  .fill #0
R2DATA  .fill #0
R3DATA  .fill #0
R7DATA  .fill #0
ERROR	.stringz "ERROR\n"
PROD	.fill #0
MD	.fill #0

// MODULUS...
//  Precondition: R1 = x
//                R2 = y
// Postcondition: If y != 0, then R0 = x % y
//                If y == 0, then prints an error message and sets R0 = 0
//                Registers R1 through R7 remain unchanged
MOD	ST R7 R7DATM
	ADD R2 R2 #0
	BRz HANDLE
	LD R0 MD
	RET

HANDLE	LEA R0 ERROR
	PUTS
	AND R0 R0 #0
	LD R7 R7DATM
	RET

// DATA FOR THE MODULUS SUBROUTINE...

R7DATM .fill #0

// SUBROUTINES FOR PRINTING INTEGERS
//==========================================================

// PRINT A NUMBER...
//  Precondition: R0 = a 16-bit integer value
// Postcondition: R0 is printed to the console in decimal
//                Registers R0 through R7 remain unchanged

PRINT	ST R0 PRINTr0	// PRINTr0 = R0 (saves R0 into memory)
	ST R7 PRINTr7	// PRINTr7 = R7 (saves return address into memory)
	ST R4 PRINTr4
	ADD R0 R0 #0	// Handle 0
	BRz ZRO	
	LD R4 MD	// Handle MD
	ST R4 MD2
	ST R1 R1DAT
	ST R2 R2DATP
	ST R3 R3DAT
	AND R1 R1 #0
	AND R2 R2 #0
	ADD R2 R2 #10
	AND R3 R3 #0
	ADD R1 R1 R0
	LEA R3 FAR
	ADD R3 R3 #-1
	ST R3 POINTER
	ADD R0 R0 #0
	BRn FX
	
LPT	ADD R1 R1 #0
	BRz EXT
	JSR DIV
	AND R1 R1 #0
	ADD R1 R0 R1
	LD R0 MD
	JSR TODIGIT	
	STI R0 POINTER
	LD R3 POINTER
	ADD R3 R3 #-1
	ST R3 POINTER
	BRnzp LPT

EXT	LD R0 POINTER
	ADD R0 R0 #1
	PUTS
	LD R0, NEWLINE	
	OUT		// print('\n')
	LD R0, PRINTr0	// R0 = PRINTr7 (reloads R0 from memory)
	LD R7, PRINTr7	// R7 = PRINTr7 (reloads return address from memory)
	LD R1 R1DAT
	LD R2 R2DATP
	LD R4 MD2
	ST R4 MD
	LD R4 PRINTr4
	RET		

FX	LD R0 HYPHEN
	OUT
	NOT R1 R1
	ADD R1 R1 #1
	BRnzp LPT

ZRO	JSR TODIGIT
	OUT
	LD R0, NEWLINE	
	OUT 
	LD R0 PRINTr0
	LD R7 PRINTr7
	RET

// DATA FOR THE PRINT SUBROUTINE...
HYPHEN	.fill #45	// ASCII Character '-'
NEWLINE	.fill #10	// ASCII Character '\n'
PRINTr0	.fill #0	// Allocates space for saving R0 in PRINT
PRINTr7	.fill #0	// Allocates space for saving R7 in PRINT
PRINTr4	.fill #0
R1DAT	.fill #0
R2DATP	.fill #0
R3DAT	.fill #0
POINTER .fill #0	
	.blkw #5
FAR	.fill #0
MD2	.fill #0


// CONVERT A NUMERICAL VALUE TO A DIGIT CHARACTER...
//  Precondition: R0 = a positive, single-digit integer value
// Postcondition: R0 = the ASCII character for the digit originally in R0
//                All other registers remain unchanged
TODIGIT	ST R1, DIGITr1	// DIGITr1 = R1 (saves R1 into memory)
	LD R1, ASCII0	// R1 = '0'
	ADD R0, R0, R1	// R0 = R0 + '0'
	LD R1, DIGITr1	// R1 = DIGITr1 (reloads R1 from memory)
	RET		//

// DATA FOR THE TODIGIT SUBROUTINE...
ASCII0	.fill #48	// ASCII Character '0'
DIGITr1	.fill #0	// Allocates space for saving R1 in TODIGIT

// ADVANCED ARITHETIC SUBROUTINES
//==========================================================

// EXPONENTIATION...
//  Precondition: R1 = x
//                R2 = y, where y >= 0
// Postcondition: R0 = Math.pow(x,y)
//                Registers R1 through R7 remain unchanged

POW	ST R2 Pr2
	ST R3 Pr3
	ST R1 Pr1
	ST R7 Pr7
	AND R0 R0 #0
	AND R3 R3 #0
	AND R2 R2 #0
	ADD R2 R2 #1
	LD R3 Pr2
		
LPG	ADD R3 R3 #0
	BRz EXR
	JSR MULT
	AND R2 R2 #0
	ADD R2 R0 R2
	ADD R3 R3 #-1
	BRnzp LPG

EXR	LD R2 Pr2
	LD R1 Pr1
	LD R3 Pr3
	LD R7 Pr7
	RET

// DATA FOR THE EXPONENTIATION SUBROUTINE...

Pr1	.fill #0
Pr2	.fill #0
Pr3	.fill #0
Pr7	.fill #0

// FACTORIAL...
//  Precondition: R1 = x, where x >= 0
// Postcondition: R0 = x!
//                Registers R1 through R7 remain unchanged

FACT	ST R1 PRt1
	ST R2 PRt2
	ST R7 PRt7
	AND R2 R2 #0
	ADD R2 R2 #1
	AND R0 R0 #0
	ADD R0 R0 #1
	
OPR	ADD R1 R1 #0
	BRz DN
	JSR MULT
	AND R2 R2 #0
	ADD R2 R0 R2
	ADD R1 R1 #-1
	BRnzp OPR
	
DN	LD R1 PRt1
	LD R2 PRt2
	LD R7 PRt7
	RET

// DATA FOR THE FACTORIAL SUBROUTINE...

PRt1	.fill #0
PRt2	.fill #0
PRt7	.fill #0

// CUSTOM ROUTINE...Define an operation of your choice.
//  Precondition: ... insert your precondition here ...
// Postcondition: ... insert your postcondition here ...
//
//      todo
//

// DATA FOR YOUR CUSTOM SUBROUTINE...



//==========================================================
// ******** DO NOT ALTER ANYTHING BELOW THIS POINT ********
//==========================================================


// SUBROUTINE FOR TESTING EACH ARITHMETIC OPERATION
//----------------------------------------------------------
TEST	ST R7, TESTr7	// TESTr7 = R7 (saves return address into memory)

	LEA R0, START	//
	PUTS		// Output "START OF TESTS" message
	//--------------------------------------------------

	LEA R0, ADDTEST
	PUTS

	LD R2, ZERO	//
	ADD R1, R2, #4	// R1 = 4 (example data)
	ADD R2, R2, #5	// R2 = 5 (example data)
	JSR PLUS	// R0 = R1 + R2
	JSR PRINT	// Should print "9"

	JSR PAUSE	// Pause until a key is pressed
	//--------------------------------------------------

	LEA R0, SUBTEST
	PUTS

	LD R2 ZERO	//
	ADD R1, R2, #12	// R1 = 12 (example data)
	ADD R2, R2, #4	// R2 = 4 (example data)
	JSR SUB		// R0 = R1 - R2
	JSR PRINT	// Should print "8"

	LD R2, ZERO	//
	ADD R1, R2, #-8	// R1 = -8 (example data)
	ADD R2, R2, #5	// R2 = 5 (example data)
	JSR SUB		// R0 = R1 - R2
	JSR PRINT	// Should print "-13" (or "#" until PRINT is complete)

	LD R2 ZERO	//
	ADD R1, R2, #10	// R1 = 10 (example data)
	ADD R2, R2, #-6	// R2 = -6 (example data)
	JSR SUB		// R0 = R1 - R2
	JSR PRINT	// Should print "16" (or "@" until PRINT is complete)

	LD R2 ZERO	//
	ADD R1, R2, #-9	// R1 = -9 (example data)
	ADD R2, R2, #-3	// R2 = -3 (example data)
	JSR SUB		// R0 = R1 - R2
	JSR PRINT	// Should print "-6" (or "*" until PRINT is complete)

	JSR PAUSE	// Pause until a key is pressed
	//--------------------------------------------------

	LEA R0, MULTEST
	PUTS

	LD R2, ZERO	//
	ADD R1, R2, #8	// R1 = 8 (example data)
	ADD R2, R2, #3	// R2 = 3 (example data)
	JSR MULT	// R0 = R1 * R2
	JSR PRINT	// Should print "24" (or "H" until PRINT is complete)

	LD R2, ZERO	//
	ADD R1, R2, #4	// R1 = 4 (example data)
	ADD R2, R2, #-3	// R2 = -3 (example data)
	JSR MULT	// R0 = R1 * R2
	JSR PRINT	// Should print "-12" (or "$" until PRINT is complete)

	LD R2, ZERO	//
	ADD R1, R2, #-2	// R1 = -2 (example data)
	ADD R2, R2, #5	// R2 = 5 (example data)
	JSR MULT	// R0 = R1 * R2
	JSR PRINT	// Should print "-10" (or "&" until PRINT is complete)

	LD R2, ZERO	//
	ADD R1, R2, #-5	// R1 = -5 (example data)
	ADD R2, R2, #-6	// R2 = -6 (example data)
	JSR MULT	// R0 = R1 * R2
	JSR PRINT	// Should print "30" (or "N" until PRINT is complete)

	LD R2, ZERO	//
	ADD R1, R2, #0	// R1 = 0 (example data)
	ADD R2, R2, #3	// R2 = 3 (example data)
	JSR MULT	// R0 = R1 * R2
	JSR PRINT	// Should print "0"

	LD R2, ZERO	//
	ADD R1, R2, #7	// R1 = 7 (example data)
	ADD R2, R2, #0	// R2 = 0 (example data)
	JSR MULT	// R0 = R1 * R2
	JSR PRINT	// Should print "0"

	LD R2, ZERO	//
	ADD R1, R2, #0	// R1 = 0 (example data)
	ADD R2, R2, #0	// R2 = 0 (example data)
	JSR MULT	// R0 = R1 * R2
	JSR PRINT	// Should print "0"

	JSR PAUSE	// Pause until a key is pressed
	//--------------------------------------------------

	LEA R0, DIVTEST
	PUTS

	LD R2, ZERO	//
	ADD R1, R2, #11	// R1 = 11 (example data)
	ADD R2, R2, #4	// R2 = 4 (example data)
	JSR DIV		// R0 = R1 / R2
	JSR PRINT	// Should print "2"
	JSR MOD		// R0 = R1 % R2
	JSR PRINT	// Should print "3"

	LEA R0, BLANK
	PUTS

	LD R2, ZERO	//
	ADD R1, R2, #-12	// R1 = -12 (example data)
	ADD R2, R2, #4	// R2 = 4 (example data)
	JSR DIV		// R0 = R1 / R2
	JSR PRINT	// Should print "-3" (or "-" until PRINT is written)
	JSR MOD		// R0 = R1 % R2
	JSR PRINT	// Should print "0"

	LEA R0, BLANK
	PUTS

	LD R2, ZERO	//
	ADD R1, R2, #13	// R1 = 13 (example data)
	ADD R2, R2, #-2	// R2 = -2 (example data)
	JSR DIV		// R0 = R1 / R2
	JSR PRINT	// Should print "-6" (or "*" until PRINT is written)
	JSR MOD		// R0 = R1 % R2
	JSR PRINT	// Should print "1"

	LEA R0, BLANK
	PUTS

	LD R2, ZERO	//
	ADD R1, R2, #-9	// R1 = -9 (example data)
	ADD R2, R2, #-4	// R2 = -4 (example data)
	JSR DIV		// R0 = R1 / R2
	JSR PRINT	// Should print "2"
	JSR MOD		// R0 = R1 % R2
	JSR PRINT	// Should print "-1" (or "/" until PRINT is written)

	LEA R0, BLANK
	PUTS

	LD R2, ZERO	//
	ADD R1, R2, #0	// R1 = 0 (example data)
	ADD R2, R2, #4	// R2 = 4 (example data)
	JSR DIV		// R0 = R1 / R2
	JSR PRINT	// Should print "0"
	JSR MOD		// R0 = R1 % R2
	JSR PRINT	// Should print "0"

	LEA R0, BLANK
	PUTS

	LD R2, ZERO	//
	ADD R1, R2, #11	// R1 = 11 (example data)
	ADD R2, R2, #0	// R2 = 0 (example data)
	JSR DIV		// R0 = R1 / R2
	JSR PRINT	// Should display error message
	JSR MOD		// R0 = R1 % R2
	JSR PRINT	// Should display error message

	JSR PAUSE	// Pause until a key is pressed
	//--------------------------------------------------

	LEA R0, POWTEST
	PUTS

	LD R2, ZERO	//
	ADD R1, R2, #3	// R1 = 3 (example data)
	ADD R2, R2, #5	// R2 = 5 (example data)
	JSR POW		// R0 = R1 ^ R2
	JSR PRINT	// Should print "243" (or "#  " until PRINT is written)

	JSR PAUSE	// Pause until a key is pressed
	//--------------------------------------------------

	LEA R0, FACTEST
	PUTS

	LD R1, ZERO	//
	ADD R1, R1, #7	// R1 = 7 (example data)
	JSR FACT	// R0 = R1!
	JSR PRINT	// Should print "5040" (or "à!!" until PRINT is written)

	JSR PAUSE	// Pause until a key is pressed
	//--------------------------------------------------

	LD R7, TESTr7	// R7 = TESTr7 (reloads R7 from memory)
	RET

// DATA FOR THE MAIN SUBROUTINE
TESTr7	.fill #0
START	.stringz "===== START OF TESTS =====\n"
ADDTEST	.stringz "PLUS:\n"
SUBTEST	.stringz "SUB:\n"
MULTEST	.stringz "MULT:\n"
DIVTEST	.stringz "DIV & MOD:\n"
POWTEST	.stringz "POW:\n"
FACTEST	.stringz "FACT:\n"
BLANK	.stringz "\n"
ZERO	.fill #0


// SUBROUTINE FOR PAUSING (DO NOT ALTER)
//----------------------------------------------------------
PAUSE	ST R0, PAUSEr0	// PAUSEr0 = R0 (saves R0 into memory)
	ST R7, PAUSEr7	// PAUSEr7 = R7 (saves R7 into memory)
	LEA R0, CONTMSG	//
	PUTS		// Output the prompt to continue
	GETC		// Wait for user response to prompt
	LD R7, PAUSEr7	// R7 = PAUSEr7 (reloads R7 from memory)
	LD R0, PAUSEr0	// R0 = PAUSEr0 (reloads R0 from memory)
	RET

PAUSEr0	.fill #0	// Allocates space for saving R0 in PAUSE
PAUSEr7	.fill #0	// Allocates space for saving R7 in PAUSE
CONTMSG	.stringz "----- Press [SPACE] for the next test -----\n"


// SUBROUTINE FOR HALTING THE PROCESSOR (DO NOT ALTER)
//----------------------------------------------------------
STOP	LEA R0, END	//
	PUTS		// Output "END OF TESTS" message
	AND R0, R0, #0	//
	LD R1, STATUS	// Load address of system status register
	STR R0, R1, #0	// Halt the processor

STATUS	.fill xFFFE
END	.stringz "====== END OF TESTS ======\n"

	.end

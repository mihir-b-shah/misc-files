
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

/*
Deliberately operates only on 0/1 signals.
No auxilliary storage allowed. Only exceptions are caching intermediate
results, since that could be done using wiring.
*/

#define DIGIT_LOWER 47
#define DIGIT_UPPER 58

#define EXIT_SUCCESS 0
#define EXIT_FAILURE 1

enum Type {Int, Float, InputError};

typedef enum Type Type;

typedef unsigned char bit;

static inline Type checkType(char* ptr) {
	while(*ptr > DIGIT_LOWER && *ptr < DIGIT_UPPER) {
		++ptr;
	}
	switch(*ptr) {
		case '\0':
			return Int;
		case '.':
			++ptr;
			break;
		default:
			return InputError;
	}
	while(*ptr > DIGIT_LOWER && *ptr < DIGIT_UPPER) {
		++ptr;
	}
	return *ptr == '\0' ? Float : InputError;
} 

/* treat like a gate, black-box implementation, 
   this is meant to teach me, how many gates are used. */
static inline bit nand(bit a, bit b) {
	return a&b^1;
}

static inline bit not(bit a) {
	return nand(a, a);
}

static inline bit and(bit a, bit b) {
	return not(nand(a, b));
}

static inline bit or(bit a, bit b) {
	return nand(not(a), not(b));
}

static inline bit xor(bit a, bit b) {
	bit share = nand(a,b);
	return nand(nand(a, share), nand(b, share));
}

static inline long intAdd(long a, long b) {
	return a+b;
}

long intSub(long a, long b) {
	return a-b;
}

long intMult(long a, long b) {
	return a*b;
}

long intDiv(long a, long b) {
	return a/b;
}

long intMod(long a, long b) {
	return a%b;	
}

long intPow(long a, long b) {
	return 0L;
}

double floatAdd(double a, double b) {
	return a+b;
}

double floatSub(double a, double b) {
	return a-b;
}

double floatMult(double a, double b) {
	return a*b;
}

double floatDiv(double a, double b) {
	return a/b;
}

double floatMod(double a, double b) {
	return 0;
}

double floatPow(double a, double b){
	return 0;
}

union Data {
	long lng;
	double dbl;
};

static int opToInt(const char op) {
	switch(op) {
		case '+':
			return 0;
		case '-':
			return 1;
		case '*':
			return 2;
		case '/':
			return 3;
		case '%':
			return 4;
		case '^':
			return 5;
		default:
			return -1;
	}
}

typedef long (*intFunc)();
static const intFunc intFx[6] = 
		{&intAdd, &intSub, &intMult, &intDiv, &intMod, &intPow};

typedef double (*floatFunc)();
static const floatFunc floatFx[6] = 
		{&floatAdd, &floatSub, &floatMult, &floatDiv, &floatMod, &floatPow};

typedef union Data Data;

int main(int argc, char** argv) {
	if(argc != 4) {
		perror("Too many or few arguments passed.\n");
		return EXIT_FAILURE;
	}
	
	const Type res1 = checkType(argv[1]);
	const Type res2 = checkType(argv[3]);
	
	Data arg1;
	Data arg2;
	Data res;
	
	switch(res1) {
		case Int:
			arg1.lng = atol(argv[1]);
			break;
		case Float:
			arg1.dbl = atof(argv[1]);
			break;
		case InputError:
			perror("Malformed argument passed in arg1.\n");
			break;
	}
	
	switch(res2) {
		case Int:
			arg2.lng = atol(argv[3]);
			break;
		case Float:
			arg2.dbl = atof(argv[3]);
			break;
		case InputError:
			perror("Malformed argument passed in arg2.\n");
			break;
	}
	
	printf("%d\n", arg1.lng);
	printf("%f\n", arg2.dbl);
	
	bool isInt = res1 == Int && res2 == Int;
	int fxIndex = opToInt(argv[2][0]);
	
	if(fxIndex == -1) {
		perror("Too many or few arguments passed.");
		return EXIT_FAILURE;
	}
	
	if(isInt) {
		res.lng = intFx[fxIndex](arg1.lng, arg2.lng);
	} else if(res1 == Int) {
		res.dbl = floatFx[fxIndex]((double) arg1.lng, arg2.dbl);
	} else if(res2 == Int) {
		res.dbl = floatFx[fxIndex](arg1.dbl, (double) arg2.lng);
	} else {
		res.dbl = floatFx[fxIndex](arg1.dbl, arg2.dbl);
	}
	
	if(isInt) {
		printf("%ld\n", res.lng);
	} else {
		printf("%f\n", res.dbl);
	}
	return EXIT_SUCCESS;
}

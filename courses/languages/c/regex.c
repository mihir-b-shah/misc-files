
#include "vector.h"

// uses a bitmask to store the ptr pairs

#define NULL_PTR (void*) 0
#define TRANS_MASK 0xffff
#define EMPTY_ENTRY 4
#define ID_SHIFT 16

static int precedence[8] = 
            {0, 0, 1, 1, 2, EMPTY_ENTRY, EMPTY_ENTRY, 1};

/* precedence of regex metacharacters

'*' -> 42, '+' -> 43, '?' -> 63
'(' -> 40, ')' -> 41, '|' -> 124 

ordering: '(', ')', '*', '+', '|', '\0', '\0', '?'

Bitmasking this with 0b111 gives us unique sequences.
Thus using a hashtable with 8 entries is sufficient.
There is an implicit concatenation also.
*/

typedef struct NONDET_FINITE_AUTOMATON {
    int id;
    vector ptrs;
} NFA;

static NFA end = {0, NULL_PTR};

void compile_regex(int len, char regex[]) {
    for(int i = 0; i<len; ++i) {
        switch(regex[i]) {
            
        }
    }
}
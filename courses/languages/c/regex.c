
#include "lex_utils.h"
#include <stdlib.h>

// uses a bitmask to store the ptr pairs

#define NULL_PTR (void*) 0
#define TRANS_MASK 0xffff
#define ID_SHIFT 16

static NFA end = {0, NULL_PTR};

void compile_regex(int len, char regex[]) {
    /* allocate a stack, first 64 bits is whether somethings
       a metacharacter or a reference to an NFA */
    __uint128_t stack[len];
    __uint128_t stack2[len];

    int stack_ptr = 0;
    int stack2_ptr = 0;

    int regex_ptr = 0;
    NFA* starter = allocate_singleton();

    do {
        switch(regex[regex_ptr]) {
            case '(':
                stack[stack_ptr++] = '(';
                break;
            case ')':
                while(stack[--stack_ptr]&CHECK_CHAR == CHECK_CHAR)
                    merge_front(INT_TO_NFA(stack[stack_ptr-1]), INT_TO_NFA(stack[stack_ptr]));
                starter = stack[stack_ptr+1];
                break;
            case '+':
                merge_1plus(starter, INT_TO_NFA(stack[stack_ptr--]));
                break;
            case '*':
                merge_0plus(starter, INT_TO_NFA(stack[stack_ptr--]));
                break;
            case '?':
                merge_cond(starter, INT_TO_NFA(stack[stack_ptr--]));
                break;
            case '|':
                int stack_auxptr = stack_ptr-1;
                while(stack_auxptr > 0)
                    merge_front(INT_TO_NFA(stack[stack_auxptr-1]), 
                                INT_TO_NFA(stack[stack_auxptr--]));
                merge_front(starter, stack[0]);   
                // not done     
            default:
                NFA* sing = allocate_singleton();
                add_transition1(sing, regex[regex_ptr]);
                stack[stack_ptr++] = CHECK_CHAR+NFA_TO_INT(sing);
                break;
        }
        ++regex_ptr;
    } while(regex_ptr < len && stack_ptr > 0);
}
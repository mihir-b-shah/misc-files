
#include <stdint.h>

#define INT_TO_NFA(x) (NFA*)(uintptr_t)(x)
#define NFA_TO_INT(x) (__uint128_t)(uintptr_t)(x)
__uint128_t CHECK_CHAR = (__uint128_t) 0x1ffffffffffffffff;

// Supports pointers and values up to 32 bits.
// VECTOR section

typedef struct VECTOR {
    __uint128_t* ptr;
    __uint128_t* begin;
    __uint128_t* end;
} vector;

vector* vect_new();
void vect_free(vector* v);

void vect_push(vector* v, __uint128_t val); 
__uint128_t vect_pop(vector* v);

int vect_size(vector* v);
__uint128_t vect_get(vector* v, int index);

__uint128_t* vect_begin(vector* v);
__uint128_t* vect_end(vector* v);
void print_vector(vector* v);

// NFA structures

typedef struct NONDET_FINITE_AUTOMATON {
    vector* ptrs;
} NFA;

NFA* allocate_singleton();
void free_NFA(NFA* root);

void add_transition1(NFA* root, NFA* next, char ch);
void merge_front(NFA* root, NFA* add);
void merge_1plus(NFA* root, NFA* add);
void merge_0plus(NFA* root, NFA* add);
void merge_cond(NFA* root, NFA* add);
void merge_or(NFA* root, NFA* add);
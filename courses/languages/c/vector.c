
#include "lex_utils.h"
#include <string.h>
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>

vector* vect_new() {
    __uint128_t* ptr = malloc(sizeof(__uint128_t) << 1); // nooooo a system call
    vector* vect = malloc(sizeof(vector));
    vect->begin = ptr; vect->end = ptr+2; vect->ptr = ptr;
    return vect;
}

__uint128_t vect_get(vector* v, int index) {
    return *(v->begin+index);
}

__uint128_t* vect_begin(vector* v) {
    return v->begin;
}

__uint128_t* vect_end(vector* v) {
    return v->ptr;
}

int vect_size(vector* v) {
    return (int) (v->end - v->begin);
}

void vect_push(vector* v, __uint128_t val) {
    if(v->ptr==v->end) {
        const int s = vect_size(v);
        __uint128_t* aux = malloc((s<<1)*sizeof(__uint128_t));
        memcpy(aux, v->begin, sizeof(__uint128_t)*s);
        free(v->ptr);
        v->begin = aux; v->end = aux+(s<<1);
        v->ptr = aux+s; 
    }
    *(v->ptr++) = val;
}

__uint128_t vect_pop(vector* v) {
    return *(--v->ptr);
}

void vect_free(vector* vect) {
    free(vect->ptr);
    free(vect);
}

void print_vector(vector* vect) {
    for(__uint128_t* it = vect->begin; it!=vect->end; ++it) {
        printf(*it); printf(", ");
    }
    printf("\n");
}
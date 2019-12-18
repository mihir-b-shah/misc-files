
#include "vector.h"
#include <string.h>
#include <stdlib.h>

vector* vect_new() {
    unsigned long* ptr = malloc(sizeof(unsigned long) << 1); // nooooo a system call
    vector* vect = malloc(sizeof(vector));
    vect->begin = ptr; vect->end = ptr+2; vect->ptr = ptr;
    return vect;
}

unsigned long vect_get(vector* v, int index) {
    return *(v->begin+index);
}

unsigned long* vect_begin(vector* v) {
    return v->begin;
}

unsigned long* vect_end(vector* v) {
    return v->ptr;
}

int vect_size(vector* v) {
    return (int) (v->end - v->begin);
}

void vect_push(vector* v, unsigned long val) {
    if(v->ptr==v->end) {
        const int s = vect_size(v);
        unsigned long* aux = malloc((s<<1)*sizeof(unsigned long));
        memcpy(aux, v->begin, sizeof(unsigned long)*s);
        free(v->ptr);
        v->begin = aux; v->end = aux+(s<<1);
        v->ptr = aux+s; 
    }
    *(v->ptr++) = val;
}

unsigned long vect_pop(vector* v) {
    return *(--v->ptr);
}
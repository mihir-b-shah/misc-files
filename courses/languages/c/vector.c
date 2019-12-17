
#include "vector.h"
#include <string.h>
#include <stdlib.h>

/* uses the small vector optimization
 allocates on the stack, avoids a malloc */

vector* vect_new() {
    long* ptr = malloc(sizeof(long) << 1); // nooooo a system call
    vector* vect = malloc(sizeof(vector));
    vect->begin = ptr; vect->end = ptr+2; vect->ptr = ptr;
    return vect;
}

long vect_get(vector* v, int index) {
    return *(v->begin+index);
}

long* vect_begin(vector* v) {
    return v->begin;
}

long* vect_end(vector* v) {
    return v->ptr;
}

int vect_size(vector* v) {
    return (int) (v->end - v->begin);
}

void vect_push(vector* v, long val) {
    if(v->ptr==v->end) {
        const int s = vect_size(v);
        long* aux = malloc((s<<1)*sizeof(long));
        memcpy(aux, v->begin, sizeof(long)*s);
        free(v->ptr);
        v->begin = aux; v->end = aux+(s<<1);
        v->ptr = aux+s; 
    }
    *(v->ptr++) = val;
}

long vect_pop(vector* v) {
    return *(--v->ptr);
}
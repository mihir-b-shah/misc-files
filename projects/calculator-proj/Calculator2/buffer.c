
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include "buffer.h"

#define MAX_LEN 10

void init_buf(struct buf* bst) {
    bst->pos = 0;
    bst->lock = false;
}

void push_buf_str(char* e, int len, struct buf* bst) {
    int it;
    it = 0;
    for(;it<len;it++)
        push_buf(*(e+it), bst);
}

void push_buf(char e, struct buf* bst) {
    int8_t i;
    if(!bst->lock && bst->pos < MAX_LEN) bst->buffer[bst->pos++] = e;
    else {
        printf("input string too long\t");
        i=0;
        for(;i<MAX_LEN;i++)
            printf("%c", bst->buffer[i]);
        printf("%c\n", e);
        printf("queue locked\n");
        bst->lock = true;
    }
}

void pop_buf(struct buf* bst) {
    if(!bst->lock) {
        if(bst->pos >= 0) bst->buffer[--bst->pos] = NULL;
        else printf("buffer empty, stack locked\n");
    } else bst->lock = true;
}

char peek_buf(struct buf* bst) {
    if(!bst->lock) {
        if(bst->pos >= 0) return bst->buffer[--bst->pos];
        else printf("buffer empty, queue locked\n");
    } else bst->lock = true;
    return NULL;
}

void print_buf(struct buf* bst) {
    int8_t it=0;
    int8_t STOP = bst->pos;
    for(;it<STOP;it++)
        printf("%c", bst->buffer[it]);
}
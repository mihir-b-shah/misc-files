   
/*
 * Supports add, and get_val() operations
 * The keys are guaranteed to be strings, the values are 8-bit integers. 
 * The keys will be hashed using two hash functions.
 * Hash function is powered sum of values
 * There are maximum of 47 keys, hence back with array of length 97
 * Uses a chaining approach to handle duplicates
 * Stores the map as an array of pointers
 */

#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include "hashmap.h"

int8_t hash_str(int8_t len, char* arr) {
    long val;
    val = 0;
    int8_t it;
    it = 0;
    for (; it < len; it++)
        val += (1 << (JUMP * it))*(*(arr+it));
    return val % SIZE;
}

bool equals_buf(int8_t len_1, char* arr1, int8_t len_2, char* arr2) {    
    if(len_1 != len_2) return false;
    int it;
    it = 0;
    for (; it < len_1; it++)
        if (*(arr1+it) != *(arr2+it)) return false;
    return true;
}

// Passing a reference to 1st element in array, which points to objects

uint8_t buf_getint(int8_t len, char* arr, struct node** ptr) {
    int8_t hash_val;
    hash_val = hash_str(len, arr);
    int it;
    it = 0;
    
    uint8_t val;
    struct node* res = *(ptr + hash_val);
    
    while (res != NULL) {
        val = res->val;
        if (equals_buf(len, arr, res->len, res->arr)) return val;
        else res = res->ptr;
    }
    return ERROR;
}

void add_map(int8_t len, char* arr, struct node** ptr, uint8_t val) {
    int8_t hash_val;
    hash_val = hash_str(len, arr);
    struct node* pt;
    pt = *(ptr + hash_val);
    while (pt != NULL) pt = pt->ptr;
    int num_bytes = sizeof(struct node);
    struct node* ptr_new = (struct node*) malloc(num_bytes);
    struct node new_item = {.val = val, .arr = arr, .len = len, .ptr = NULL};
    *ptr_new = new_item;
    *(ptr + hash_val) = ptr_new;
}
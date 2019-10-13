
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include "hashmap.h"

void parse() {    
    struct node* hash_table[SIZE] = {NULL};
    struct node** st = &hash_table;
    
    constr_map(st);
    
    int iter;
    iter = 0;
    for(;iter<SIZE;iter++)
        if(hash_table[iter] != NULL)
            free(hash_table[iter]);
    
}

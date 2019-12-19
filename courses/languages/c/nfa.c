
#include "lex_utils.h"
#include <stdlib.h>

NFA* allocate_singleton() {
    NFA* singleton = malloc(sizeof(NFA));
    vector* vect = vect_new();
    singleton->ptrs = vect;
    return singleton;
}

void free_NFA(NFA* root) {
    vector* vect = root->ptrs;
    const int size = vect_size(vect);  
    for(int i = 0; i<size; ++i) {
        free_NFA(INT_TO_NFA(vect_get(vect, i)));
    }
    vect_free(vect);
    free(root);
}

void add_transition1(NFA* root, char ch, NFA* next) {
    vect_push(root->ptrs, ch*CHECK_CHAR+NFA_TO_INT(next));
}

void merge_1plus(NFA* root, NFA* add) {

}

void merge_0plus(NFA* root, NFA* add) {

}

void merge_cond(NFA* root, NFA* add) {

}

void merge_or(NFA* root, NFA* add) {
    
}

void merge_front(NFA* root, NFA* add) {
    
}

#include <stdio.h>
#include "lex_utils.h"

int main() {
    NFA* first = allocate_singleton();
    NFA* second = allocate_singleton();
    add_transition1(first, second, 'a');
    print_vector(first->ptrs);
}
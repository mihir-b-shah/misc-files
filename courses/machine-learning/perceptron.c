
#include <stdio.h>
#include "linear_algs.h"
#include "fixed_vector.h"

void perceptron(const int T, const int DIM, 
        struct POINT* points, struct HYPERPLANE* plane) {
    
    struct POINT* ptr;
    float* vect = plane->vector;
    float offs = plane->offset;
    
    for(int i = 0; i<T; ++i) {
        ptr = points;
        for(int j = 0; j<DIM; ++j) {
            if(ptr->label*dot(DIM, ptr->coord, vect) <= 0) {
                add(DIM, vect, ptr->coord);
                offs += ptr->label;
            }
            ++ptr;
        }
    }
    
    plane->offset = offs;
}
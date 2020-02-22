#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "fixed_vector.h"
#include "linear_algs.h"

int main(int argc, char* argv[])
{
    FILE* fio = fopen("classifiers.in", "r");
    const int N,DIM;
    fscanf(fio, "%d %d", &N, &DIM);

    int buffer;
    struct POINT points[N];
    float data[N*DIM];

    for(int i = 0; i<N; ++i) {
        struct POINT* pt = points + i;
        for(int j = 0; j<DIM; ++j) {
            fscanf(fio, "%f", data+N*i+j);
            printf("%d %d\n", N, DIM);
        }
        pt->coord = data+N*i;
        fscanf(fio, "%d", &buffer);
        pt->label = buffer;
    }
    fclose(fio);

    for(int i = 0; i<N; ++i) {
        struct POINT* ptr = points+i;
        float* dat = ptr->coord;
        for(int i = 0; i<DIM; ++i) {
            printf("%f ", dat[i]);
        }
        printf("\n");
    }

    float vect[DIM];
    memset(vect, 0, DIM*sizeof(float));
    
    struct HYPERPLANE plane = {vect, 0};
    perceptron(5, DIM, points, &plane);  

    for(int i = 0; i<DIM; ++i) {
        printf("%f ", vect[i]);
    }
    printf("%f", plane.offset);
    return 0;
}
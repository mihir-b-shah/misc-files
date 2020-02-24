
#include "linear_algs.h"
#include "fixed_vector.h"
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <stdio.h> 
#include <math.h>

#define LAMBDA 1
#define BETA 5
#define NORM_BETA 0.2
#define EPS 1e-3

bool init_rand = false;

int rand_index(int N) {
    return rand()*N/RAND_MAX;
}

float learning_rate(int iter) {
    return 1.0/(1+iter);
}

void sgd(const int DIM, const int N, struct POINT* data, struct HYPERPLANE* fill) {
    if(!init_rand) {
        srand(time(NULL));
    }
    float theta[DIM+1];
    float gradient[DIM+1];
    memset(theta, 0, (DIM+1)*sizeof(float));
    int iter = 0;

    do {
        int index;
        struct POINT* point;
        memset(gradient, 0, (DIM+1)*sizeof(float));

        for(int i = 0; i<BETA; ++i) {
            index = rand_index(N);
            point = data + index;
            for(int j = 0; j<DIM; ++j) {
                printf("label: %d, coord: <%f, %f>\n", point->label, point->coord[0], point->coord[1]);
                gradient[j] += NORM_BETA*(LAMBDA*theta[j]+fmaxf(0, -(point->label)*(point->coord[j])));
                theta[j] -= learning_rate(iter)*gradient[j];
            }
            gradient[DIM] -= point->label;
            theta[DIM] += learning_rate(iter)*gradient[DIM];
            printf("Gradient: <%f, %f, %f>, Theta: <%f %f>, Offset: %f\n", gradient[0], gradient[1], gradient[2], 
                                                                       theta[0], theta[1], theta[2]);
        }
        ++iter;
    } while(!dot(DIM, gradient, gradient) < EPS);
    fill->vector = theta;
    fill->offset = theta[DIM];
}
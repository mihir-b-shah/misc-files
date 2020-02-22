
#ifndef LINEAR_ALGS
#define LINEAR_ALGS

struct POINT {
    float* coord;
    int label;
};

struct HYPERPLANE {
    float* vector;
    float offset;
};

#endif
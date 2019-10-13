
#include "fft.h"
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <string.h>
#include <complex.h>

/* computed using double angle to split the cosine term
 then used fractional de moivre to simplify the sums
   verified the number using a python script */ 

#define PI 3.141592653589
#define HANN_NORM sqrt(511.0/3)/8

// the hanning filter
double filter(complex cpx, int n) {
    return HANN_NORM/2*(1-cos(2*PI*n/511));
}

void hanning_wdw(complex aux[]) {
    int it;
    for(it=0; it<512; ++it)
        aux[it] *= filter(aux[it],it);
}

void fft_helper(complex arr[], complex out[], int step) {
    if(step < 512) {
        int incr = step << 1;
        fft_helper(out, arr, incr);
        fft_helper(out+step, arr+step, incr);
        int it;
        for(it=0;it<512;it+=incr) {
            complex nw = cexp(-I*PI*it/512)*out[it+step];
            arr[it >> 1] = out[it] + nw;
            arr[(it+512) >> 1] = out[it] - nw;
        }
    }
}

void str_spectr(spectrum* ptr) {
    int it;
    printf("%d", ptr->index);
    for(it=0; it<257; ++it) 
        printf("%d: %f\n", it, ptr->spect[it]);
}

/* computes a 512-pt fft on the signal.
   size parameter ensures no overflow 
   split into even and odd number components */
spectrum* fft(int ind, complex aux[]) {
    complex out[512];
    hanning_wdw(aux);
    int it;
    for(it=0;it<512;++it)
        out[it]=aux[it];
    for(int it=0;it<512;++it)
        printf("%d: %f + %fi\n", it, creal(aux[it]), cimag(aux[it]));
    fft_helper(aux, out, 1);
    double mgn[257];
    for(it=0; it<=256; ++it) 
        mgn[it] = cabs(aux[it])/512;
    spectrum* ret = calloc(1, sizeof(spectrum));
    ret->spect=mgn;
    ret->index=ind;
    return ret;
}
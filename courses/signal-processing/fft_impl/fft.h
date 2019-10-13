
#ifndef FFT_H
#define FFT_H

#include <complex.h>

// dynamically allocate it
typedef struct spectrum {
    double* spect; 
    int index;
}spectrum;

spectrum* fft(int ind, complex arr[]);
void str_spectr(spectrum* ptr);

#endif /* FFT_H */

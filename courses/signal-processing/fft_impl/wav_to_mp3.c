
#include "wav_to_mp3.h"
#include "fft.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

// idea adapted from https://gist.github.com/tkaczenko/21ced83c69e30cfbc82b
struct wav_header {
    char chunk_id[4];
    unsigned long chunk_size;
    char format[4];
    char id_1[4];
    unsigned long size_1;
    unsigned short aud_format;
    unsigned short num_channel;
    unsigned long sampl_rate;
    unsigned long byte_rate;
    unsigned short block_align;
    unsigned short bits_per;
    char sound_id[4];
    unsigned long sound_len;
};

void extr_mp3(char path[]) {
    if(strlen(path) > 25) {
        printf("File name too long. Limit to 25 characters");
        exit(EXIT_FAILURE);
    }
    struct wav_header head;
    const int size = strlen(path);
    char f_path[50];
    memset(f_path, 0, sizeof(f_path));
    strcpy(f_path, "C:\\Users\\mihir\\Documents\\");
    strcat(f_path, path);
    FILE *in;
    in = fopen(f_path,"rb");
    fread(&head, sizeof(head), 1, in);
    printf("%lu\n", head.sound_len);
    complex aux[512];
    float buf[512]; // making it a long makes types simple
    int LIM = head.sound_len >> 9;
    if((LIM << 9) == head.sound_len) --LIM;
    int it;
    spectrum spta[LIM+1];
    for(it=0;it<=LIM;++it) {
        fread(buf, head.bits_per >> 3, 
                it==LIM ? head.sound_len&0x1FF : 512, in);
        int j;
        for(j=0;j<512;++j)
            aux[j] = buf[j] + 0*I;
        spta[it] = *fft(it, aux);
    }
    fclose(in);
}

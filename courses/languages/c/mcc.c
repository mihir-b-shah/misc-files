
#include <stdio.h>
#include <assert.h>
#include "lexer.h"

#define EXIT_SUCCESS 0
#define EXIT_FAILURE 1

int main(int argc, char* argv[]) {

    assert(sizeof(char) == 1);

    if(argc != 2) {
        fprintf(stderr, "%d params passed. 1 extra (filename) expected.\n", argc);
        return EXIT_FAILURE;
    }

    FILE* infile = fopen(argv[1], "r");
    if(infile == NULL) {
        fprintf(stderr, "Error encountered in opening file.\n");
    }
    fseek(infile, 0, SEEK_END);
    long fsize = ftell(infile);
    rewind(infile);

    const long PROG_LEN = fsize;
    char program_contents[1+PROG_LEN];

    fread(program_contents, 1, PROG_LEN, infile);
    program_contents[PROG_LEN] = '\0';

    //lex(program_contents);
    return 0;
}

#include <stdio.h>

int main() {
    unsigned int N = 0u,L = 0u,U = 0u;
    while(!eof()) {
        scanf("%u %u %u\n",&N, &L, &U);
        unsigned int M = 0u;
        int i;
        for(i = 31; i>-1; --i) {
            if(M+(1u<<i)>U) {
                M += 0;
            } else if((L>M?L-M:0)>(1u<<i)-1) {
                M += 1u<<i;
            } else if((N& (1<<i)) == 0) {
                M += 1u<<i;
            }
        }
        printf("%u\n", M);
    }
}
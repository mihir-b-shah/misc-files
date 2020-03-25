
#include <math.h>
#include <string.h>
#include <stdio.h>

int main() {
    FILE* fptr = fopen("cowcode.in","r");
    char str[32];  
    memset(str, '\0', sizeof(char)*32);
    long long addr = 0;

    fscanf(fptr, "%s %lld\n", str, &addr);
    addr -= 1;
  
    long long len = strlen(str);
    long long mid = len*(1LL << ((int) log2(addr/len)));

    while(mid >= len) {
        if(addr > mid) {
            addr -= 1+mid;
        } else if(addr == mid) {
            addr = mid-1;
        }
        mid >>= 1;
    }

    FILE* fout = fopen("cowcode.out","w");
    fprintf(fout,"%c\n",str[(int) addr]);
    return 0;
}
#include <iostream>
#include <fstream>
#include <cstring>
#include <algorithm>

using namespace std;

int main() {
    ifstream fin;
    fin.open("diamond.in");

    int n,K;
    fin >> n >> K;
    const int N = n;
    int diamonds[N];

    for(int i = 0; i<N; ++i) {
        fin >> diamonds[i];
    }
    
    fin.close();
    sort(diamonds, diamonds+N);

    int inner = 0;
    int outer = 0;

    int first_start = 0; // inclusive
    int first_end = 0; // exclusive

    while(outer < N) {
        while(inner < N && diamonds[inner]-diamonds[outer]<=K) {
            ++inner;
        }
        if(inner-outer>first_end-first_start) {
            first_start = outer;
            first_end = inner;
        }
        ++outer;
        inner = outer;
    }

    const int N2 = N-first_end+first_start;
    int one = first_end-first_start;

    int diamonds2[N2];

    memcpy(diamonds2,diamonds,first_start*sizeof(int));
    memcpy(diamonds2+first_start,diamonds+first_end,(N-first_end)*sizeof(int));

    inner = 0;
    outer = 0;

    first_start = 0; // inclusive
    first_end = 0; // exclusive

    while(outer < N2) {
        while(inner < N2 && diamonds2[inner]-diamonds2[outer]<=K) {
            ++inner;
        }
        if(inner-outer>first_end-first_start) {
            first_start = outer;
            first_end = inner;
        }
        ++outer;
        inner = outer;
    }

    int two = first_end-first_start;

    ofstream fout;
    fout.open("diamond.out");
    fout << one+two << endl;
    fout.close();
}

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
        inner = outer;
        while(inner < N && diamonds[inner]-diamonds[outer]<=3) {
            ++inner;
        }
        if(inner-outer>first_end-first_start) {
            first_start = outer;
            first_end = inner;
        }
        ++outer;
    }

    cout << first_start << first_end << endl;
    int diamonds2[N-first_end+first_start];

    memcpy(diamonds,diamonds2,first_start);
    memcpy(diamonds+first_end,diamonds2+first_start,N-first_end);

    ofstream fout;
    fout.open("diamond.out");
    fout.close();
}
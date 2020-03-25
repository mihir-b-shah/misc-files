
#include <iostream>
#include <fstream>

using namespace std;

int main() {
    ifstream fin;
    fin.open("shuffle.in");

    int buf;
    fin >> buf;
    const int N = buf;
    int cows[N];
    bool state[N];

    for(int i = 0; i<N; ++i) {
        fin >> cows[i];
        state[i] = 1;
    }

    for(int i = 0; i<N; ++i) {
        state[cows[i]-1] |= state[i];
        if(i != cows[i]-1) state[i] = 0;
    }

    int ctr = 0;
    for(int i = 0; i<N; ++i) {
        if(state[i]) {
            ++ctr;
        }
    }

    fin.close();

    ofstream fout;
    fout.open("shuffle.out");
    fout << ctr << endl;
    fout.close();
}
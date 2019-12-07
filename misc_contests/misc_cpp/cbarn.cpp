
#include <iostream>
#include <fstream>

using namespace std;

int main() {
    ifstream fin("cbarn.in");
    int n;
    fin >> n;
    const int N = n;
    int cows[N];

    for(int i = 0; i<N; ++i) {
        fin >> cows[i];
    }
    fin.close();

    ofstream fout("cbarn.out");
    fout.close();
    return 0;
}
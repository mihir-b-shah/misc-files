#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <cmath>

using namespace std;

int main() {
    ifstream fin("milkvisits.in");
    int N,Q;
    fin >> N >> Q;

    string types;
    getline(fin, types);
    getline(fin, types);

    int a,b; char c;
    vector<int> ufds(N,0);

    int ctr = 1;
    ofstream fout("milkvisits.out");

    for(int i = 0; i<N-1; ++i) {
        fin >> a >> b;
        a -= 1; b -= 1;

        if(ufds[a] == 0 && ufds[b] == 0) {
            if(types[a] == types[b]) {
                ufds[a] = (types[a] == 'G' ? 1 : -1)*ctr; 
                ufds[b] = ufds[a];
                ++ctr;
            } else {
                ufds[a] = types[a] == 'G' ? ctr : -ctr;
                ++ctr;
                ufds[b] = types[b] == 'G' ? ctr : -ctr;
                ++ctr;
            }
        } else if(ufds[a] == 0) {
            if(types[a] == types[b]) {
                ufds[a] = ufds[b];
            } else {
                ufds[a] = types[a] == 'G' ? ctr : -ctr;
                ++ctr;
            }
        } else if(ufds[b] == 0) {
            if(types[a] == types[b]) {
                ufds[b] = ufds[a];
            } else {
                ufds[b] = types[b] == 'G' ? ctr : -ctr;
                ++ctr;
            }
        }
    }
    
    for(int i = 0; i<Q; ++i) {
        fin >> a >> b >> c;
        a -= 1; b -= 1;
        if(signbit(ufds[a]) != signbit(ufds[b])) fout << 1;
        else if(ufds[a] == ufds[b]) {
            char type = signbit(ufds[a]) ? 'H' : 'G';
            fout << (type == c);
        } else if(signbit(ufds[a]) == signbit(ufds[b])) {
            fout << 1;
        } else fout << 0;
    }

    fin.close();
    fout << endl;
    fout.close();
    return 0;
}
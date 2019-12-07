
#include <iostream>
#include <fstream>
#include <cstring>
#include <algorithm>
#include <unordered_set>

using namespace std;

int main() {
    ifstream fin("gates.in");
    int n;
    fin >> n;
    const int N = n;
    char buf[N];
    fin >> buf;
    fin.close();

    int ctrs[4];
    memset(ctrs, 0, sizeof(int)*4);
    int ct = 0;
    unordered_set<int> set;

    for(int i = 0; i<N; ++i) {
        switch(buf[i]) {
            case 'N': ++ctrs[0];
            case 'E': ++ctrs[1];
            case 'S': ++ctrs[2];
            case 'W': ++ctrs[3];
        }

        int minval = 1000000000;
        for(int i = 0; i<4; ++i) {
            minval = min(minval, ctrs[i]);
        }
        if(minval > 0) {
            for(int i = 0; i<4; ++i) {
                ctrs[i] -= minval;
            }

            if(set.find((ctrs[0]+ctrs[2]<<10)+ctrs[1]+ctrs[3]) == set.end()) {
                set.insert((ctrs[0]+ctrs[2]<<10)+ctrs[1]+ctrs[3]);
                continue;
            }
            ct += minval;
        }
    }

    ofstream fout("gates.out");
    fout << ct-1 << endl;
    fout.close();
    return 0;
}
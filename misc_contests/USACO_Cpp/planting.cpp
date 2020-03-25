
#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>

using namespace std;

int main() {
    ifstream fin("planting.in");
    int N;
    fin >> N;
    int a,b;
    vector<vector<int>> adjlist; 
    adjlist.reserve(N);

    for(int i = 0; i<N; ++i) {
        vector<int> vi;
        adjlist.push_back(vi);
    }

    for(int i = 0; i<N-1; ++i) {
        fin >> a >> b;
        adjlist[a-1].push_back(b-1);
        adjlist[b-1].push_back(a-1);
    }
    fin.close();

    // find max{degree+1}

    int maxdeg = 0;
    for(int i = 0; i<N; ++i) {
        int deg = adjlist[i].size();
        cout << deg << endl;
        maxdeg = max(maxdeg, deg);
    }

    ofstream fout("planting.out");
    fout << maxdeg+1 << endl;
    fout.close();
    return 0;
}
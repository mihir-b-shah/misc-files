
#include <iostream>
#include <fstream>
#include <algorithm>
#include <unordered_map>
#include <vector>

using namespace std;

int main() {
    ifstream fin;
    fin.open("sort.in");

    int buf;
    fin >> buf;
    const int N = buf;
    int array[N];
    unordered_map<int, vector<int>> mapping;

    for(int i = 0; i<N; ++i) {
        fin >> array[i];
        mapping[array[i]].push_back(i);
    }
    fin.close();

    sort(array, array+N);    

    int iter = 0;
    int curr;
    int ctr;
    int maxdist = 0;

    while(iter < N) {
        curr = array[iter];
        ctr = 0;
        vector<int>& idxs = mapping[curr];
        // so now map from curr's list to [iter-ctr,iter)
        while(iter < N && array[iter] == curr) {
            maxdist = max(maxdist, idxs[ctr++]-iter);
            ++iter;
        }
    }

    ofstream fout;
    fout.open("sort.out");
    fout << maxdist+1 << endl;
    fout.close();
}
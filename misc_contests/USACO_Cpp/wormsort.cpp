
#include <iostream>
#include <fstream>
#include <vector>
#include <utility>
#include <algorithm>
#include <set>

using namespace std;

int N,M;
vector<int> positions;
vector<vector<pair<int,int>>> adjlist;
set<int> visited;

int maxpath(int src, int dest, int width) {
    vector<pair<int,int>>& vect = adjlist[src];
    if(src == dest) {
        return width;
    }
    visited.insert(src);

    int maxval = 0;
    for(auto it = vect.begin(); it!=vect.end(); ++it) {
        pair<int,int>& pr = *it;
        if(visited.find(pr.first) == visited.end()) {
            maxval = max(maxval, maxpath(pr.first, dest, min(width, pr.second)));
        }
    }
    return maxval;
}

int main() {
    ifstream fin("wormsort.in");
    fin >> N >> M;

    int buf;
    positions.reserve(N);
    adjlist.reserve(N);

    for(int i = 0; i<N; ++i) {
        fin >> buf;
        positions.push_back(buf-1);
        vector<pair<int,int>> vi;
        adjlist.push_back(vi);
    }

    int buf2, buf3;
    for(int i = 0; i<M; ++i) {
        fin >> buf >> buf2 >> buf3;
        adjlist[buf-1].push_back({buf2-1,buf3});
        adjlist[buf2-1].push_back({buf-1,buf3});
    }
    fin.close();

    int minwidth = INT32_MAX;
    for(int i = 0; i<N; ++i) {
        visited.clear();
        if(positions[i] != i) {
            minwidth = min(maxpath(i, positions[i], INT32_MAX), minwidth);
        }
    }

    ofstream fout("wormsort.out");
    fout << (minwidth == INT32_MAX ? -1 : minwidth) << endl;
    fout.close();
    return 0;
}

#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <algorithm>
#include <cstring>

using namespace std;

typedef vector<pair<int,int>> vii;

int main() {
    ifstream fin;
    fin.open("mootube.in");

    int n,q;
    fin >> n >> q;
    const int N = n;
    const int Q = q;

    vector<vii> adjlist;
    adjlist.reserve(N);
    for(int i = 0; i<N; ++i) {
        vii inner;
        adjlist.push_back(inner);
    }

    int a,b,c;
    for(int i = 0; i<N-1; ++i) {
        fin >> a >> b >> c;
        adjlist[a-1].push_back({b-1,c});
        adjlist[b-1].push_back({a-1,c});
    }

    pair<int,int> queries[Q];
    for(int i = 0; i<Q; ++i) {
        fin >> a >> b;
        queries[i] = {a,b-1};
    }

    fin.close();
    queue<pair<int,int>> qe;
    const int INF = 1000000000;
    int visited[N];
    memset(visited, 0, sizeof(int)*N);
    int ctrs[Q];
    memset(ctrs, 0, sizeof(int)*Q);

    for(int i = 0; i<Q; ++i) {
        int src = queries[i].second;
        int ctr = 0;
        int min_wt = queries[i].first;

        qe.push({src,INF});

        while(!qe.empty()) {
            pair<int,int>& pr = qe.front();
            if(pr.second >= min_wt && visited[pr.first] == i) {
                ++ctr;
            }
            ++visited[pr.first];
            qe.pop();
            
            vii& next = adjlist.at(pr.first);
            const int size = next.size();

            for(int j = 0; j<size; ++j) {
                pair<int,int>& iter = next.at(j);
                if(visited[iter.first] == i) {
                    qe.push({iter.first, min(iter.second, pr.second)});
                }
            }            
        }

        ctrs[i] = ctr-1;
    }

    ofstream fout;
    fout.open("mootube.out");

    for(int i = 0; i<Q; ++i) {
        fout << ctrs[i] << endl;
    }

    fout.close();
}

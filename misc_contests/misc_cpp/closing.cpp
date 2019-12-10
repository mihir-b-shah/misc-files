
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <unordered_set>

using namespace std;

typedef vector<int> vi;

int main() {
    ifstream fin("closing.in");
    int N,M;
    fin >> N >> M;

    vector<vi> adjlist;
    adjlist.reserve(N);
    vi loclist;
    loclist.reserve(N);
    unordered_set<int> locs;
    unordered_set<int> visited;

    for(int i = 0; i<N; ++i) {
        adjlist.push_back(vi());
    }

    int a,b;
    for(int i = 0; i<M; ++i) {
        fin >> a >> b;
        adjlist[a-1].push_back(b-1);
        adjlist[b-1].push_back(a-1);
    }

    for(int i = 0; i<N; ++i) {
        fin >> a;
        loclist.push_back(a-1);
        locs.insert(a-1);
    }

    fin.close();
    queue<int> queue;
    vector<bool> output;
    output.reserve(N);

    // locs is full of all items

    for(int i = 0; i<N; ++i) {
        int st = *(locs.begin());
        queue.push(st);

        for(int j = 0; j<N; ++j) {
            if(locs.find(j) != locs.end()) {
                visited.insert(j);
            }
        }

        while(!queue.empty()) {
            int curr = queue.front();
            visited.erase(curr);
            queue.pop();

            vi next = adjlist[curr];
            for(int j = 0; j<next.size(); ++j) {
                if(visited.find(next[j]) != visited.end() && locs.find(next[j]) != locs.end()) {
                    queue.push(next[j]);
                    visited.erase(next[j]);
                }

            }
        }

        output.push_back(visited.empty());
        visited.clear();
        locs.erase(loclist[i]);
    }

    ofstream fout("closing.out");

    for(int i = 0; i<N; ++i) {
        if(output[i]) 
            fout << "YES" << endl;
        else
            fout << "NO" << endl;
    }   

    fout.close();
    return 0;
}
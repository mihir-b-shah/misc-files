#include <fstream>
#include <iostream>
#include <vector>
#include <unordered_set>
#include <queue>
#include <utility>
#include <algorithm>

using namespace std;

typedef vector<int> vi;

int main() {
    ifstream fin;
    fin.open("fenceplan.in");
    
    int N,M;
    fin >> N >> M;
    int x,y;

    vector<vi> adjlist;
    vector<pair<int,int>> coords;
    unordered_set<int> visited;

    adjlist.reserve(N);
    coords.reserve(N);
    for(int i = 0; i<N; ++i) {
        vi inner;
        adjlist.push_back(inner);
        fin >> x >> y;
        coords.push_back(make_pair(x,y));
        visited.insert(i);
    }

    for(int i = 0; i<M; ++i) {
        fin >> x >> y;
        adjlist.at(x-1).push_back(y-1);
        adjlist.at(y-1).push_back(x-1);
    }

    int min_perim = 1000000000;
    queue<int> qe;
    fin.close();

    while(!visited.empty()) {
        qe.push(*visited.begin());
        int xmax = 0, ymax = 0, xmin = 100000000, ymin = 100000000;

        while(!qe.empty()) {
            int loc = qe.front();
            pair<int,int>& coord = coords.at(loc);

            xmax = max(coord.first, xmax);
            ymax = max(coord.second, ymax);
            xmin = min(coord.first, xmin);
            ymin = min(coord.second, ymin);

            qe.pop();
            visited.erase(loc);
            vi& next = adjlist.at(loc);

            const int lim = next.size();
            int el;
            for(int i = 0; i<lim; ++i) {
                el = next.at(i);
                if(visited.find(el) != visited.end()) {
                    qe.push(el);
                }
            } 
        }
        min_perim = min(min_perim, xmax-xmin+ymax-ymin);
    }

    ofstream fout;
    fout.open("fenceplan.out");
    fout << (min_perim << 1);
    fout.close();
    return 0;
}
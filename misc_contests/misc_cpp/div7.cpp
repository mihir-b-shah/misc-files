
#include <iostream>
#include <fstream>
#include <vector>
#include <utility>
#include <algorithm>
#include <unordered_map>

using namespace std;

int main() {
    ifstream fin("div7.in");
    int n;
    fin >> n;
    const int N = n+1;
    int cows[N];
    unordered_map<int,pair<int,int>> map;

    for(int i = 1; i<N; ++i) {
        fin >> cows[i];
        cows[i] += cows[i-1];
        cows[i] %= 7;

        if(map.count(cows[i])) {
            pair<int,int>& mm = map[cows[i]];
            mm.first = min(mm.first, i);
            mm.second = max(mm.second, i);
        } else {
            map.insert(make_pair(cows[i], make_pair(i,i)));
        }
    }

    int diff = 0;

    for(pair<int,pair<int,int>> entry: map) {
        diff = max(diff, entry.second.second-entry.second.first);
    }

    ofstream fout("div7.out");
    fout << diff << endl;
    fout.close();
    return 0;
}
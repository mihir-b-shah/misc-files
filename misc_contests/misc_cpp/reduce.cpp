
#include <iostream>
#include <fstream>
#include <vector>
#include <utility>
#include <algorithm>
#include <unordered_set>

using namespace std;

vector<pair<int,int>> xs;
vector<pair<int,int>> ys;
unordered_set<long long> set;

inline long long hashpair(const pair<int,int>& pr) {
    return ((static_cast<long long>(pr.first))<<32)+pr.second;
}

int recur(int xleft, int yleft, int xright, int yright, int lvl) {
    if(xleft >= xright || yleft >= yright) {
        return 1000000000;
    }
    int ret = 1000000000;
    if(lvl < 3) {
        if(set.find(hashpair(xs[xleft])) == set.end()) {
            ret = min(ret, ((set.insert(hashpair(xs[left])), recur(xleft+1,yleft,xright,yright,lvl+1)));
            set[xleft] = 0;
        }
        if(!set[xright]) {
            ret = min(ret, (set[xright] = 1, recur(xleft,yleft,xright-1,yright,lvl+1)));
            set[xright] = 0;
        }
        if(!set[yleft]) {
            ret = min(ret, (set[yleft] = 1, recur(xleft,yleft+1,xright,yright,lvl+1)));
            set[yleft] = 0;
        }
        if(!set[yright]) {
            ret = min(ret, (set[yright] = 1, recur(xleft,yleft,xright,yright-1,lvl+1)));
            set[yright] = 0;
        }
        return ret;
    } return (ys[yright].second-ys[yleft].second)*(xs[xright].first-xs[xleft].first);
}

bool compare(const pair<int,int>& p1, const pair<int,int>& p2) {
    return p1.second<p2.second;
}

int main() {
    ifstream fin("reduce.in");
    int N;
    fin >> N;

    xs.reserve(N);
    ys.reserve(N);

    int x,y;
    for(int i = 0; i<N; ++i) {
        fin >> x >> y;
        xs.push_back({x,y});
        ys.push_back({x,y});
    }
    fin.close();

    sort(xs.begin(),xs.end());
    sort(ys.begin(),ys.end(),compare);

    int result = recur(0, 0, N-1, N-1, 0);

    ofstream fout("reduce.out");
    fout << result << endl;
    fout.close();

    return 0;
}
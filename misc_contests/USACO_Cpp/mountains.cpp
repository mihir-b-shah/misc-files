
#include <iostream>
#include <fstream>
#include <vector>
#include <utility>
#include <algorithm>
#include <cmath>

using namespace std;

inline bool compare(const pair<int,int>& p1, const pair<int,int>& p2) {
    return p2.first+p2.second<p1.first+p1.second;
}

inline char within(const pair<int,int>& p1, const pair<int,int>& p2) {
    float comp = ((float) p1.second-p2.second)/(p1.first-p2.first);
    if(p1.second>p2.second) {
        return abs(comp) > 1.0f ? 1 : 0;
    } else {
        return abs(comp) > 1.0f ? 2 : 0;
    }
}

int main() {
    ifstream fin("mountains.in");
    int N;
    fin >> N;
    int buf1,buf2;
    vector<pair<int,int>> mountains;
    vector<bool> eliminated;
    mountains.reserve(N);
    for(int i = 0; i<N; ++i) {
        fin >> buf1 >> buf2;
        mountains.push_back({buf1,buf2});
        eliminated.push_back(0);
    }
    fin.close();
    sort(mountains.begin(), mountains.end(), compare);

    for(int i = 0; i<mountains.size()-1; ++i) {
        if(eliminated[i]) continue;
        for(int j = i+1; j<mountains.size(); ++j) {
            switch(buf1 = within(mountains[i],mountains[j])) {
                case 1: eliminated[j] = 1; break;
                case 2: eliminated[i] = 1; break;
            }
        }
    }

    int ctr = 0;
    for(int i = 0; i<eliminated.size(); ++i) {
        if(!eliminated[i]) ++ctr;
    }

    ofstream fout("mountains.out");
    fout << ctr << endl;
    fout.close();
    return 0;
}
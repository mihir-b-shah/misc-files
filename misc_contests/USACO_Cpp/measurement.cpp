
#include <iostream>
#include <fstream>
#include <vector>
#include <map>
#include <unordered_map>
#include <algorithm>
#include <functional>

using namespace std;

typedef struct update {
    int day;
    int id;
    int change;
};

bool compare(const update& u1, const update& u2) {
    return u2.day>u1.day;
}

int main() {
    ifstream fin("measurement.in");
    int N,G;
    fin >> N >> G;

    vector<update> updates;
    updates.reserve(N);
    unordered_map<int,int> ids;
    map<int,int,greater<int>> mmap;

    int a,b,c;
    for(int i = 0; i<N; ++i) {
        fin >> a >> b >> c;
        updates.push_back({a,b-1,c});
        ids.insert({i,G});
    }
    mmap.insert({G,__INT32_MAX__});
    fin.close();

    sort(updates.begin(), updates.end(), compare);
    int ctr = 0;
    pair<int,int> prior = *(mmap.begin());
    cout << prior.first << " " << prior.second << endl;

    for(int i = 0; i<N; ++i) {
        update& ud = updates[i];
        cout << ud.day << " " << ud.id << " " << ud.change << endl;
        ids[ud.id] += ud.change;
        int curr_val = ids[ud.id];

        --mmap[curr_val-ud.change];
        ++mmap[curr_val];

        pair<int,int> now = *(mmap.begin());
        cout << now.first << " " << now.second << endl;
        if(now.first != prior.first || now.second != prior.second) {
            ++ctr;
        }
        prior = now;
    }

    ofstream fout("measurement.out");
    fout << ctr << endl;
    fout.close();
    return 0;
}
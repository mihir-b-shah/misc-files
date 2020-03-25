
#include <fstream>
#include <iostream>
#include <unordered_set>
#include <utility>
#include <vector>
#include <algorithm>

using namespace std;

typedef struct endpoint {
    int pt;
    bool end;
    int id;
};

bool compare_endpoint(endpoint e1, endpoint e2) {
    return e2.pt>e1.pt;
}

void print_array(int* array, int N) {
    for(int i = 0; i<N; ++i) {
        cout << array[i] << ", ";
    }
    cout << endl;
}

int main() {
    ifstream fin;
    fin.open("lifeguards.in");

    int n;
    fin >> n;
    const int N = n;
    endpoint pts[N<<1];
    int num_guards[N<<1];
    for(int i = 0; i<(N<<1); ++i) {
        num_guards[i] = 0;
    }

    int a,b;

    for(int i = 0; i<N; ++i) {
        fin >> a >> b;
        pts[i<<1] = {a, 0, i};
        pts[1+(i<<1)] = {b, 1, i};
    }

    sort(pts, pts+(N<<1), compare_endpoint);
    fin.close();

    int num = 0;
    int dist = pts[(N<<1)-1].pt-pts[0].pt;
    unordered_set<int> items;
    vector<int> ids;

    for(int i = 0; i<(N<<1); ++i) {
        num_guards[i] = num;
        if(num == 0 && i != 0) {
            dist -= pts[i].pt-pts[i-1].pt;
        }
        if(items.size() == 1) {
            ids.push_back(*items.begin());
        }
        if(pts[i].end) {
            items.erase(pts[i].id);
            --num;
        } else {
            items.insert(pts[i].id);
            ++num;
        }
    }

    int ptr = 1;
    const int LIM = N<<1;
    int ctr = 0;
    int idmins[N];
    for(int i = 0; i<N; ++i) {
        idmins[i] = 0;
    }

    while(ptr < LIM) {
        if(num_guards[ptr] == 1) {
            idmins[ids.at(ctr++)] += ((pts[ptr].pt)-(pts[ptr-1].pt));
        }
        ++ptr;
    } 

    int minval = 1000000000;
    for(int i = 0; i<N; ++i) {
        minval = min(minval, idmins[i]);
    }

    ofstream fout;
    fout.open("lifeguards.out");
    fout << dist-minval << endl;
    fout.close();
    return 0;
}

#include <fstream>
#include <iostream>
#include <utility>
#include <cstring>
#include <algorithm>

using namespace std;

typedef struct cow {
    int id;
    bool start;
    int coord;
};

bool cow_compare(cow c1, cow c2) {
    return c1.coord == c2.coord ? c1.start-c2.start : c1.coord<c2.coord;
}

int main() {
    ifstream fin;
    fin.open("helpcross.in");
    int c,n;
    fin >> c >> n;
    const int N = n, C = c;

    int chickens[C];
    for(int i = 0; i<C; ++i) {
        fin >> chickens[i];
    }
    sort(chickens,chickens+C);
    cow endpoints[N << 1];

    for(int i = 0; i<N; ++i) {
        fin >> c >> n;
        endpoints[i<<1] = {i,1,c};
        endpoints[1+(i<<1)] = {i,0,n+1};
    }
    fin.close();
    
    sort(endpoints, endpoints+(N<<1), cow_compare);

    int sums[1+(N<<1)];
    memset(sums, 0, (N<<1)*__SIZEOF_INT__);
    int curr = 0;

    for(int i = 0; i<(N<<1); ++i) {
        curr += (endpoints[i].start << 1)-1;
        sums[i] = curr;
    }

    for(int i = 0; i<(N<<1); ++i) {
        cout << sums[i] << ", ";
    }

    int chicken_ptr = 0;
    int accumulator = 0;

    for(int i = 0; i<(N<<1)-1; ++i) {
        if(endpoints[i].coord <= chickens[chicken_ptr] && chickens[chicken_ptr] < endpoints[i+1].coord) {
            if(sums[i]-accumulator > 0) {
                --accumulator;
            }
            ++chicken_ptr;
        }
    }

    ofstream fout;
    fout.open("helpcross.out");
    fout.close();

    return 0;
}

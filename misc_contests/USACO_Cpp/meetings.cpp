
#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

typedef struct {
    int wt;
    int pos;
    int right;

    inline int operator-(const cow& oth) {
        return pos-oth.pos;
    }

    inline void operator~() {
        right *= -1;
    }

    inline void advance() {
        pos += right;
    }

    inline bool operator==(int pos) {
        return this->pos = pos;
    }
} cow;

bool compare(const cow& c1, const cow& c2) {
    return c1.pos == c2.pos ? 
            c1.right > c2.right : c1.pos < c2.pos;
}

int main() {
    ifstream fin("meetings.in");
    int N,L;
    fin >> N >> L;

    vector<cow> cows;
    cows.reserve(N);

    int a,b,c;
    int half_weight = 0;

    for(int i = 0; i<N; ++i) {
        fin >> a >> b >> c;
        half_weight += a;
        cows.push_back({a,b,c});
    }

    fin.close();
    sort(cows.begin(), cows.end(), compare);
    half_weight = half_weight & 1 ? 
                   (half_weight >> 1)+2 : (half_weight >> 1)+1;

    int acc_weight = 0;
    int t = 0;
    int left_bound = 0; int right_bound = N-1;

    while(acc_weight < half_weight) {
        for(int i = left_bound; i<right_bound; ++i) {
            if(cows[i]==0) ++left_bound;
            if(cows[i]==L) --right_bound;
            
            if(cows[i+1]-cows[i]<2 && cows[i+1].right == -1 && cows[i+1].right == 1) {
                ~cows[i+1]; ~cows[i];
            }
            cows[i].advance();
        }
        cows[N-1].advance();
        ++t;
    }

    ofstream fout("meetings.out");
    fout.close();
    return 0;
}
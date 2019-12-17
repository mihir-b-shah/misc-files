
#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

typedef struct cow {
    int wt;
    int pos;
    bool right;
};

bool compare(const cow& c1, const cow& c2) {
    return c1.pos<c2.pos;
}

int main() {
    ifstream fin("meetings.in");
    int N,L;
    fin >> N >> L;

    vector<cow> cows;
    cows.reserve(N);

    int buf1,buf2,buf3;
    int total_weight = 0;

    for(int i = 0; i<N; ++i) {
        fin >> buf1 >> buf2 >> buf3;
        total_weight += buf1;
        cows.push_back({buf1,buf2,buf3==1?1:0});
    }

    fin.close();
    sort(cows.begin(), cows.end(), compare);

    int left = 0;
    int right = N-1;
    int accm = 0;
    int limit = (total_weight & 1) == 0 ? total_weight >> 1 : 1+(total_weight >> 1);
    int collision_count = 0;

    while(accm < limit && left <= right) {
        if(!cows[left].right && cows[right].right) {
            if(cows[left].pos < L-cows[right].pos) {
                accm += cows[left].wt;
                ++left;
            } else {
                accm += cows[right].wt;
                --right;
            }
        } else if(!cows[left].right) {
            accm += cows[left].wt;
            ++left;
        } else if(cows[right].right) {
            accm += cows[right].wt;
            --right;
        } else {
            int aux_left = left;
            while(cows[aux_left].right) {
                ++aux_left;
            }

            int aux_right = right;
            while(!cows[aux_right].right) {
                --aux_right;
            }

            if(cows[aux_left].pos-cows[left].pos<cows[right].pos-cows[aux_right].pos) {
                collision_count += aux_left-left;
                cows[aux_left].right = !cows[aux_left].right;
                accm += cows[left].wt;
                ++left;
            } else {
                collision_count += right-aux_right;
                cows[aux_right].right = !cows[aux_right].right;
                accm += cows[right].wt;
                --right;
            }
        }
    }

    ofstream fout("meetings.out");
    fout << collision_count << endl;
    fout.close();
    return 0;
}
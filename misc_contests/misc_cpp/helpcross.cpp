
#include <fstream>
#include <iostream>
#include <vector>
#include <utility>
#include <algorithm>

using namespace std;

vector<pair<int,int>> cows;
vector<int> chickens;
vector<bool> used_cows;

int main() {
    ifstream fin("helpcross.in");
    int C,N;
    fin >> C >> N;

    cows.reserve(N);
    chickens.reserve(C);
    used_cows.reserve(N);

    int buf;
    for(int i = 0; i<C; ++i) {
        fin >> buf;
        chickens.push_back(buf);
    }

    int buf2;
    for(int i = 0; i<N; ++i) {
        fin >> buf >> buf2;
        cows.push_back({buf,buf2});
        used_cows.push_back(0);
    }
    fin.close();

    sort(cows.begin(), cows.end());
    sort(chickens.begin(), chickens.end());

    int cow_ptr = 0;
    int chicken_ptr = 0;
    int ctr = 0;

    while(cow_ptr < N && chicken_ptr < C) {
        int chicken = chickens[chicken_ptr];
        pair<int,int>& cow = cows[cow_ptr];

        if(!used_cows[cow_ptr] && cow.first <= chicken && chicken <= cow.second) {
            ++ctr;
            int aux_ptr = cow_ptr+1;
            int min_ptr = cow_ptr;
            int min_end = cows[cow_ptr].second;
            while(aux_ptr < N && cows[aux_ptr].second <= cow.second) {
                if(cows[aux_ptr].second < min_end) {
                    min_end = cows[aux_ptr].second;
                    min_ptr = aux_ptr;
                }
                ++aux_ptr;
            }
            used_cows[min_ptr] = 1;
            if(min_ptr == cow_ptr) ++cow_ptr;
            ++chicken_ptr;
        } else if(chicken > cow.second) {
            ++cow_ptr;
        } else if(cow.first > chicken) {
            ++chicken_ptr;
        }
    }

    ofstream fout("helpcross.out");
    fout << ctr << endl;
    fout.close();

    return 0;
}


#include <iostream>
#include <fstream>
#include <utility>
#include <algorithm>
#include <vector>
#include <functional>

using namespace std;

vector<int> cows;
vector<int> trades;
vector<pair<int,int>> stores;
int N;
int store_ptr;
int trade_ptr;

inline void manage(int cow) {
    int amt = cows[cow];
    int alt = cows[cow+1];

    int store_ptr_1 = 0;
    int store_ptr_2 = 0;
    int mb1 = 0;
    int mb2 = 0;
    int rem1 = 0;
    int rem2 = 0;

    while(amt > 0 || alt > 0) {

        pair<int,int>& pr1 = stores[store_ptr_1];
        pair<int,int>& pr2 = stores[store_ptr_2];

        if(amt > 0) {
            if(pr1.second >= amt) {
                mb1 += amt*pr1.first;
                rem1 = pr1.second-amt;
                amt = 0;
            } else {
                mb1 += pr1.second*pr1.first;
                amt -= pr1.second;
                ++store_ptr_1;
            }
        } 
        if(alt > 0) {
            if(pr2.second >= alt) {
                mb2 += alt*pr2.first;
                rem2 = pr2.second-alt;
                alt = 0;
            } else {
                mb2 += pr2.second*pr2.first;
                alt -= pr2.second;
                ++store_ptr_2;
            }            
        }
    }    
    
    int benefit = (mb1-mb2)-(trades[trade_ptr]-trades[trade_ptr+1]);
    if(benefit > 0) {
        // actually do the incrementing
    }
}

int main() {
    ifstream fin;
    fin.open("rental.in");
    fin.close();

    int M,R;
    fin >> N >> M >> R;

    cows.reserve(N);
    stores.reserve(M);
    trades.reserve(R);
    int a,b;

    for(int i = 0; i<N; ++i) {
        fin >> a;
        cows.push_back(a);
    }
    for(int i = 0; i<M; ++i) {
        fin >> a >> b;
        stores.push_back({b,a});
    }
    for(int i = 0; i<R; ++i) {
        fin >> a;
        trades.push_back(a);
    }

    sort(cows.begin(), cows.end(), greater<int>());
    sort(stores.begin(), stores.end(), greater<pair<int,int>>());
    sort(trades.begin(), trades.end(), greater<int>());

    for(int i = 0; i<N; ++i) {

    }


    ofstream fout;
    fout.open("rental.out");
    fout.close();
}
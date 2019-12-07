
#include <iostream>
#include <fstream>
#include <algorithm>
#include <cmath>

using namespace std;

bool compare(const pair<int,int>& p1, const pair<int,int>& p2) {
    return p1.second < p2.second;
}

int main() {
    ifstream fin("balancing.in");
    int n;
    fin >> n;
    const int N = n;

    pair<int,int> xys[N];

    int x,y;
    for(int i = 0; i<N; ++i) {
        fin >> x >> y;
        xys[i] = pair<int,int>{x,y};
    }

    fin.close();

    sort(xys,xys+N);

    int opt_x = 0;
    int diff = __INT_MAX__; 

    for(int i = 0; i<N-1; ++i) {
        if(xys[i].first == xys[i+1].first) continue;
        else if(abs(N-(i<<1)-2)<diff) {
            opt_x = xys[i].first+1;
            diff = abs(N-(i<<1)-2);
        }
    }

    sort(xys,xys+N,compare);
    int opt_y = 0;
    diff = __INT_MAX__;

    for(int i = 0; i<N-1; ++i) {
        if(xys[i].second == xys[i+1].second) continue;
        else if(abs(N-(i<<1)-2)<diff) {
            opt_y = xys[i].second+1;
            diff = abs(N-(i<<1)-2);
        }
    }

    int ctrs[4] = {0};

    for(int i = 0; i<N; ++i) {
        if(xys[i].first < opt_x && xys[i].second < opt_y) {
            ++ctrs[0];
        } else if(xys[i].first > opt_x && xys[i].second < opt_y) {
            ++ctrs[1];
        } else if(xys[i].first < opt_x && xys[i].second > opt_y) {
            ++ctrs[2];
        } else if(xys[i].first > opt_x && xys[i].second > opt_y) {
            ++ctrs[3];
        }
    }

    cout << "opt x: " << opt_x << " opt y: " << opt_y << endl;
    int output = 0;
    for(int i = 0; i<4; ++i) {
        output = max(output, ctrs[i]);
    }

    ofstream fout("balancing.out");
    fout << output << endl;
    fout.close();
    return 0;
}
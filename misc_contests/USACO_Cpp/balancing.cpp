
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

    pair<int,int> xs[N];
    pair<int,int> ys[N];

    int x,y;
    for(int i = 0; i<N; ++i) {
        fin >> x >> y;
        xs[i] = pair<int,int>{x,y};
        ys[i] = pair<int,int>{x,y};
    }

    fin.close();

    sort(xs,xs+N);
    sort(ys,ys+N,compare);

    int opt_y;
    int output_opt = 1000000000;

    for(int i = 0; i<N-1; ++i) {
        if(ys[i].second == ys[i+1].second) continue;
        opt_y = ys[i].second+1;

        int opt_x = 0;
        int diff = __INT_MAX__; 
        int ctrs[4] = {0};

        for(int i = 0; i<N; ++i) {
            if(xs[i].first < opt_x && xs[i].second < opt_y) {
                ++ctrs[0]; // -x,-y
            } else if(xs[i].first > opt_x && xs[i].second < opt_y) {
                ++ctrs[1]; // +x,-y
            } else if(xs[i].first < opt_x && xs[i].second > opt_y) {
                ++ctrs[2]; // -x, +y
            } else if(xs[i].first > opt_x && xs[i].second > opt_y) {
                ++ctrs[3]; // +x, +y
            }
        }

        for(int i = 0; i<N-1; ++i) {
            if(xs[i].first == xs[i+1].first) continue;
            opt_x = xs[i].first+1;
            if(xs[i].second > opt_y) {
                ++ctrs[2];
                --ctrs[3];
            } else {
                ++ctrs[0];
                --ctrs[1];
            }

            int output = 0;
            for(int i = 0; i<4; ++i) {
                output = max(output, ctrs[i]);
            }

            output_opt = min(output_opt, output);
        }
    }

    ofstream fout("balancing.out");
    fout << output_opt << endl;
    fout.close();
    return 0;
}
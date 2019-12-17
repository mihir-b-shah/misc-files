
#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    ifstream fin("haybales.in");
    int N,Q;
    fin >> N >> Q;
    vector<int> vals;
    vals.reserve(N);
    int buf;
    for(int i = 0; i<N; ++i) {
        fin >> buf;
        vals.push_back(buf);
    }
    sort(vals.begin(), vals.end());
    
    int buf2;
    ofstream fout("haybales.out");

    for(int i = 0; i<Q; ++i) {
        fin >> buf >> buf2;
        auto one = lower_bound(vals.begin(), vals.end(),buf);
        auto two = upper_bound(vals.begin(), vals.end(),buf2);
        fout << two-one << endl;
    }

    fin.close();
    fout.close();
    return 0;
}
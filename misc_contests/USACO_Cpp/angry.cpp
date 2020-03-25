
#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

int K;
vector<int> bales;

bool possible(int R) {
    R *= 2;
    int cow_count = 0;
    const int size = bales.size();

    int ptr = 0;
    int lag = 0;

    while(lag < size) {
        while(ptr < size && bales[ptr]-bales[lag]<=R) {
            ++ptr;
        }
        lag = ptr;
        ++cow_count;
    }

    return cow_count<=K;
}

int main() {
    ifstream fin("angry.in");
    int N;
    fin >> N;
    fin >> K;

    int buf;
    bales.reserve(N);

    for(int i = 0; i<N; ++i) {
        fin >> buf;
        bales.push_back(buf);
    }
    fin.close();
    sort(bales.begin(), bales.end());

    int upper = 1+(bales[N-1]-bales[0])>>1;
    int lower = 0;
    int mid;

    while(lower < upper) {
        mid = (lower+upper)>>1;
        if(possible(mid)) {
            upper = mid;
        } else {
            lower = mid+1;
        }
    }

    ofstream fout("angry.out");
    fout << upper << endl;
    fout.close();

    return 0;
}
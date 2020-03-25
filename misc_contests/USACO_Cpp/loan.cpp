
#include <iostream>
#include <fstream>
#include <algorithm>

using namespace std;

long long N,K,M;

bool possible(long long X) {
    cout << endl << X << endl;
    long long G = 0;
    long amt;
    long double x = N;
    for(int i = 0; i<K; ++i) {
        amt = max((N-G)/X, M);
        x /= X;
        cout << x << endl;
        cout << amt << endl;
        G += amt;
    }
    cout << endl;
    return G >= N;
}

int main() {
    ifstream fin("loan.in");
    fin >> N >> K >> M;
    fin.close();

    long long upper = 1000000000000LL;
    long long lower = 0;
    long long mid;

    while(lower < upper) {
        mid = (lower+upper)/2;
        if(mid == lower) break;
        if(possible(mid)) {
            lower = mid;
        } else {
            upper = mid;
        }
    }

    ofstream fout("loan.out");
    fout << lower << endl;
    fout.close();
    return 0;
}
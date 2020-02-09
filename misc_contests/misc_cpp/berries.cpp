
#include <iostream>
#include <fstream>
#include <set>
#include <algorithm>

using namespace std;

typedef struct compare {
    bool operator()(const int v1, const int v2) const {
        return v2<v1;
    }
};

int main() {
    ifstream fin("berries.in");
    int n,k;
    fin >> n >> k;
    const int N = n;
    const int K = k;

    int array[N];
    for(int i = 0; i<N; ++i) {
        fin >> array[i];
    }
    fin.close();
    sort(array, array+N);

    multiset<int,compare> one;
    multiset<int,compare> two;

    for(int i = 0; i<K/2; ++i) {
        one.insert(array[N-1-i]);
    }

    for(int i = K/2; i<K; ++i) {
        two.insert(array[N-1-i]);
    }

    for(int i = 0; i<K/2; ++i) {
        int top = *(one.begin());
        cout << top << endl;
        int orig = top/2;
        int rest = top-top/2;

        if(rest >= *(two.begin())) {
            one.erase(one.begin());
            one.insert(rest);
            two.erase(*(--two.end()));
            two.insert(orig);
        } else if(rest <= *(--two.end())) {
            break;
        } else if(rest < *(two.begin()) && rest > *(--two.end()) 
            && *(two.begin())+*(--two.end()) < top) {
            one.erase(one.begin());
            one.insert(rest);
            two.erase(--two.end());
            two.insert(rest); two.insert(orig);
        }
    }

    int sum = 0;
    for(auto it = two.begin(); it!=two.end(); ++it) {
        sum += *it;
    }

    ofstream fout("berries.out");
    fout << sum << endl;
    fout.close();
    return 0;
}
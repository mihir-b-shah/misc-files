
#include <iostream>
#include <fstream>
#include <queue>
#include <vector>
#include <algorithm>

using namespace std;

vector<int> cows;
priority_queue<int,vector<int>,greater<int>> pq;
int Tmax;

bool possible(int K) {
    const int N = cows.size();

    int ptr = 0;
    int time = 0;
    int capacity = 0;

    do {
        while(ptr < N && capacity < K) {
            pq.push(time+cows[ptr]);
            ++ptr;
            ++capacity;
        }
        if(pq.size() > 0)
            time = pq.top();
        while(pq.size() > 0 && pq.top() == time) {
            pq.pop();
            --capacity;
        }
    } while (!pq.empty());
    return time <=Tmax;
}

int main() {
    ifstream fin("cowdance.in");
    int N;
    fin >> N;
    fin >> Tmax;

    cows.reserve(N);
    int buf;
    for(int i = 0; i<N; ++i) {
        fin >> buf;
        cows.push_back(buf);
    }
    fin.close();

    int lower = 0; // inclusive
    int upper = N; // inclusive
    int mid;

    while(lower < upper) {
        mid = (lower+upper) >> 1;
        if(possible(mid)) {
            upper = mid;
        } else {
            lower = mid+1;
        }
    }

    ofstream fout("cowdance.out");
    fout << upper << endl;
    fout.close();
    return 0;
}
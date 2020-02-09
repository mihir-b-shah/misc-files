
#include <cstdlib>
#include <fstream>
#include <vector>
#include <unordered_map>
#include <utility>
#include <stack>

using namespace std;

int main(int argc, char** argv) {
    ofstream fout("primes.txt");

    int N = atoi(argv[1]); 
    vector<int> primes;
    stack<pair<int,int>> stk;
    unordered_map<int,int> queued;

    for(int i = 2; i<N+1; ++i) {
        auto val = queued.find(i);
        if(val == queued.end()) {
            primes.push_back(i);
            queued.insert(pair<int,int>(i << 1,i));
        } else {
            auto ptr = val;
            do {
                stk.push(*ptr);
            } while((ptr = queued.find(ptr->first+ptr->second)) != queued.end());

            while(!stk.empty()) {
                auto ptr = stk.top();
                queued[ptr.first+ptr.second] = ptr.second;
                queued.erase(ptr.first);
                stk.pop();
            }
        }
    }

    for(auto it=primes.begin(); it!=primes.end(); ++it) {
        fout << *it << '\n';
    }
    fout.close();
}
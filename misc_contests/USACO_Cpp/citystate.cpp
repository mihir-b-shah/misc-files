
#include <iostream>
#include <fstream>
#include <cstring>
#include <vector>
#include <utility>
#include <unordered_map>

using namespace std;

long long hashbuf(char* buf) {
    int len = strlen(buf);
    return ((static_cast<long long>((buf[0]-'A'))<<24)+buf[1]-'A')*
           ((static_cast<long long>((buf[len-2]-'A'))<<24)+buf[len-1]-'A');
}

int main() {
    ifstream fin("citystate.in");
    unordered_map<long long,int> mapper;
    char buf[15];
    int N;
    fin >> N;
    for(int i = 0; i<N; ++i) {
        fin >> buf;
        ++mapper[hashbuf(buf)];
    }
    fin.close();

    int ctr = 0;
    for(auto it = mapper.begin(); it!=mapper.end(); ++it) {
        int v = it->second;
        ctr += v>>1;
    }

    ofstream fout("citystate.out");
    fout << ctr << endl;
    fout.close();
    return 0;
}
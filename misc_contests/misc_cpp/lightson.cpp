
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <cstring>

using namespace std;

#define GEN(x,y) (x-1)*N+(y-1)
#define ABS(x) (x>0)?x:-x

bool adj(int addr1, int addr2, int N) {
    return ABS(addr1-addr2) == 1 ^ ABS(addr1-addr2) == N;
}

int main() {
    ifstream fin("lightson.in");
    int n,M;
    fin >> n >> M;
    const int N = n;
    const int SIZE = N*N;

    int graph[SIZE];
    memset(graph, 0, sizeof(int)*SIZE);
    vector<vector<int>> list(SIZE);

    for(int i = 0; i<SIZE; ++i) {
        vector<int> vi;
        list.push_back(vi);
    }

    int a,b,c,d;
    for(int i = 0; i<M; ++i) {
        fin >> a >> b >> c >> d;
        list[GEN(a,b)].push_back(GEN(c,d));
    }
    fin.close();

    queue<int> qe;
    qe.push(GEN(1,1));

    while(!qe.empty()) {
        int addr = qe.front();
        qe.pop();
        graph[addr] = 1;
        vector<int>& vect = list[addr];

        for(int i = 0; i<vect.size(); ++i) {
            if(graph[vect[i]] == 0 && (adj(addr, vect[i], N) || graph[]) {
                qe.push(vect[i]);
            }
        }
    }



    ofstream fout("lightson.out");
    fout.close();
    return 0;
}
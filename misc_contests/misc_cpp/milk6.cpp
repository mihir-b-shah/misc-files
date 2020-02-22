
#include <fstream>
#include <vector>
#include <cstring>
#include <algorithm>

using namespace std;

typedef vector<int> vi;

#define INF 0x7f7f7f7fL

int main() {
    ifstream fin("milk6.in");
    int buffer, buffer2, buffer3;
    fin >> buffer;
    const int M = buffer;

    fin >> buffer;
    const int N = buffer;

    vector<vi> adjlist(M);
    int capacity[M][M];
    memset(capacity, 0, sizeof(int)*M*M);

    for(int i = 0; i<M; ++i) {
        vi inner;
        fin >> buffer >> buffer2 >> buffer3;
        adjlist[buffer-1].push_back(buffer2-1);
        adjlist[buffer2-1].push_back(buffer-1);
        capacity[buffer-1][buffer2-1] = buffer3;
    }
    fin.close();

    unsigned long visited;
    int flow[M];
    memset(flow, 0, M*sizeof(int));
    int prev[M];
    memset(prev, 0, M*sizeof(int));
    int maxflow = 0;
    int maxloc = 0;
    bool break_outer = false;

    int total_flow = 0;

    while(!break_outer) {
        visited = 0L;
        memset(flow, 0, M*sizeof(int));
        memset(prev, -1, M*sizeof(int));

        flow[0] = INF;

        while(true) {
            maxflow = 0;
            maxloc = -1;

            for(int i = 0; i<M; ++i) {
                if(flow[i] > maxflow && (visited & (1UL << i)) == 0) {
                    maxflow = flow[i];
                    maxloc = i;
                }
            }

            if(maxloc == -1 || maxloc == N-1) {
                break;
            }

            visited += 1UL << maxloc;
            vi& inner = adjlist[maxloc];
            for(int pr: inner) {
                if(flow[pr] < min(maxflow, capacity[maxloc][pr])) {
                    prev[pr] = maxloc;
                    flow[pr] = min(maxflow, capacity[maxloc][pr]);
                }
            }
        }

        if(maxloc == -1) {
            break_outer = true;
        }

        if(!break_outer) {
            int path_capacity = flow[N-1];
            total_flow += path_capacity;

            int curr_node = N-1;
            int next_node;

            while(curr_node != 0) {
                next_node = prev[curr_node];
                capacity[next_node][curr_node] -= path_capacity;
                capacity[curr_node][next_node] += path_capacity;
                curr_node = next_node; 
            }
        }
    }

    ofstream fout("milk6.out");
    fout << total_flow << endl;
    fout.close();
}
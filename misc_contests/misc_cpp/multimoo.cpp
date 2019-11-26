#include <unordered_set>
#include <fstream>

using namespace std;

int dx[4] = {1,0,-1,0};
int dy[4] = {0,1,0,-1};
bool visited[250][250];

void flood_fill(int x, int y) {
    visited[x][y] = 1;
}

int main() {
    ifstream fin;
    fin.open("multimoo.in");
    int N;
    fin >> N;

    int grid[250][250];
    for(int i = 0; i<N; ++i) {
        for(int j = 0; j<N; ++j) {
            fin >> grid[i][j];
        }
    }
}
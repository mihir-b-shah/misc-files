
#include <string>
#include <fstream>

using namespace std;

int main() {
    ifstream fin;
    fin.open("leftout.in");
    string line;
    bool matrix[1000][1000];
    int N;

    fin >> N;
    for(int i = 0; i<N; ++i) {
        fin >> line;
        for(int j = 0; j<N; ++j) {
            matrix[i][j] = line.at(i) == 'L' ? 0 : 1;
        }
    }
}
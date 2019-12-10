
#include <iostream>
#include <fstream>

using namespace std;

int main() {
    ifstream fin("herding.in");
    int n;
    fin >> n;
    const int N = n;
    int nums[N];

    for(int i = 0; i<N; ++i) {
        fin >> nums[i];
    }

    fin.close();

    ofstream fout("herding.out");
    fout.close();
    return 0;
}
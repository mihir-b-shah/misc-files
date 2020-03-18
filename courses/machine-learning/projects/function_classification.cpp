
using namespace std;

#include <iostream>
#include <fstream>
#include <array>
#include <vector>

class POINT {
    private:
        double x1,x2,x3,x4,x5;
    public:
        void compress(int N, double* buffer);
};

void POINT::compress(int N, double* buffer) {
    if(N > 5) {
        // perform a principal component analysis
    } else {
        switch(N) {
            case 5:
                x5 = buffer[4];
            case 4:
                x4 = buffer[3];
            case 3:
                x3 = buffer[2];
            case 2:
                x2 = buffer[1];
            case 1:
                x1 = buffer[0];
                break;
        }
    }
}

int main(int argc, char** argv) {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    cerr.tie(NULL);

    if(argc != 2) {
        cerr << "Please enter a data file.\n";
    }

    ifstream fin(argv[1]);
    fin.tie(NULL);
    int buf; double buf2,buf3;
    fin >> buf;

    const int N = buf;
    vector<POINT> points(N);

    for(int i = 0; i<N; ++i) {
        fin >> buf2 >> buf3;
        points[i] = {buf2,buf3};
    }


}

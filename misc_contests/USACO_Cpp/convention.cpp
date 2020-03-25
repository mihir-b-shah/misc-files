
#include <fstream>
#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

vector<int> times;
int M,C;

inline bool possible(int wtime) {
    int ptr = 0;
    int loc_ptr = 0;
    int num_buses = 0;
    const int size = times.size();
    //cout << "wtime: " << wtime << endl;
    while(ptr < size) {
        //cout << "loc_ptr: " << loc_ptr << " ptr: " << ptr << " num_buses: " << num_buses << endl;
        if(loc_ptr < size && loc_ptr-ptr < C && times[loc_ptr]-times[ptr] <= wtime) {
            ++loc_ptr;
        } else if(loc_ptr < size) {
            ptr = loc_ptr;
            ++num_buses;
        } else if(loc_ptr != ptr){
            ++num_buses;
            break;
        } else {
            break;
        }
    }
    //cout << "num buses: " << num_buses << endl << endl;
    return num_buses <= M;
}

int main() {
    ifstream fin;
    fin.open("convention.in");

    int n;
    fin >> n >> M >> C;
    const int N = n;

    int MIN = 0;
    int MAX = 1000000000;
    times.reserve(N);

    for(int i = 0; i<N; ++i) {
        fin >> n;
        times.push_back(n);
    }
    
    fin.close();
    sort(times.begin(), times.end());
    //cout << "M: " << M << " C: " << C << endl;

    int mid;
    int last_worked;

    while(MIN < MAX) {
        mid = (MIN+MAX) >> 1;
        //cout << "MIN: " << MIN << " MAX: " << MAX << " MID: " << mid << endl;
        if(possible(mid)) {
            MAX = mid;
            last_worked = mid;
        } else {
            MIN = mid+1;
        }
    }

    ofstream fout;
    fout.open("convention.out");
    fout << last_worked << endl;
    fout.close();
}
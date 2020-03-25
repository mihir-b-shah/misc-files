
#include <iostream>
#include <fstream>

using namespace std;

int main() {
    ifstream fin("moobuzz.in");
    int N;
    fin >> N;
    fin.close();

    long long upper = 1000000000000;
    long long lower = 1;
    long long mid;
    long long ans;

    while(lower <= upper) {
        mid = (lower+upper) >> 1;
        cout << lower << " " << upper << " " << mid << endl;
        ans = mid-mid/3-mid/5+mid/15;

        if(ans == N) {
            break;
        } else if(ans < N) {
            lower = mid+1;
        } else {
            upper = mid-1;
        }
    }

    ofstream fout("moobuzz.out");
    while(mid%3 == 0 || mid%5 == 0) --mid;
    fout << mid << endl;
    fout.close();
    return 0;
}
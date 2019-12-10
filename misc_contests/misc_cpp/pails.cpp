
#include <iostream>
#include <fstream>
#include <cmath>
#include <unordered_set>

using namespace std;

int X,Y,K,M;
int mindiff;
unordered_set<long long> visited;

void recur(long long x, long long y, int k) {
    if(abs(x+y-M)<mindiff) mindiff = abs(x+y-M);
    if(visited.find((x<<42)+(y<<23)+k) != visited.end()) return;
    else visited.insert((x<<42)+(y<<23)+k);
    if(k==K) return;

    recur(X,y,k+1);
    recur(x,Y,k+1);
    recur(0,y,k+1);
    recur(x,0,k+1);
    if(x>Y-y) {
        recur(x-Y+y,Y,k+1);
    } else {
        recur(0,x+y,k+1);
    }
    if(y>X-x) {
        recur(X,y-X+x,k+1);
    } else {
        recur(x+y,0,k+1);
    }
}

int main() {
    ifstream fin("pails.in");
    fin >> X >> Y >> K >> M;
    fin.close();   
    
    mindiff = __INT32_MAX__;
    recur(0,0,0);

    ofstream fout("pails.out");
    fout << mindiff << endl;
    fout.close();
    return 0;
}
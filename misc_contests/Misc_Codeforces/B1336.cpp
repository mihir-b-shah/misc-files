
#include <iostream>
#include <cstdlib>
#include <climits>
#include <algorithm>

using namespace std;

long long calc_function(long long r, long long g, long long b) {
	return (r-g)*(r-g) + (g-b)*(g-b) + (b-r)*(b-r);
}

int main() {
	int buf;
	cin >> buf;
	const int T = buf;
	for(int t = 0; t<T; ++t) {
		cin >> buf;
		const int nR = buf;
		cin >> buf;
		const int nG = buf;
		cin >> buf;
		const int nB = buf;
		
		int R[nR];
		int G[nG];
		int B[nB];
		
		for(int i = 0; i<nR; ++i) {
			cin >> R[i];
		}
		
		for(int i = 0; i<nG; ++i) {
			cin >> G[i];
		}
		
		for(int i = 0; i<nB; ++i) {
			cin >> B[i];
		}
		
		// o(n lg n)
		sort(R, R+nR);
		sort(G, G+nG);
		sort(B, B+nB);
		
		// o(n lg n)
		
		long long best = LLONG_MAX;
		for(int i = 0; i<nR; ++i) {
			int* gPtr = lower_bound(G, G+nG, R[i]);
			int* bPtr = lower_bound(B, B+nB, R[i]);
			
			if(gPtr == G+nG) {
				gPtr = G+nG-1;
			} else if(gPtr != G){
				if(abs(R[i]-*gPtr) > abs(R[i]-*(gPtr+1))){
					gPtr++;
				}
			}
			
			if(bPtr == B+nB) {
				bPtr = B+nB-1;
			} else if(bPtr != B){
				if(abs(R[i]-*bPtr) > abs(R[i]-*(bPtr+1))){
					bPtr++;
				}
			}
			
			best = min(best, calc_function(R[i], *gPtr, *bPtr));
		}
		
		cout << "OUT: " << best << '\n';
	}
	cout.flush();
	return 0;
}

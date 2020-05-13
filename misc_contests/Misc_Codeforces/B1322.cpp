
#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int nC2(int n) {
	return n%4 != 0 && (n-1)%4 != 0;
}

int nn1(int n, int n1) {
	return n1 % 2 != 0 && (n-n1) % 2 != 0;
}

int corrMod2(int x) {
	if(x%2 == -1) {
		return 1;
	} else {
		return x%2;
	}
}

int main() {
	int N;
	cin >> N;
	vector<int> arr(N);
	
	int maxval = 0;
	for(int i = 0; i<N; ++i) {
		cin >> arr[i];
		if(arr[i] > maxval) {
			maxval = arr[i];
		}
	}
	
	int LIMIT = 0;
	while(maxval > 0) {
		maxval >>= 1;
		++LIMIT;
	}

	int bit_counts[24] = {0};

	for(int i = 0; i<N; ++i) {
		for(int j = 0; j<24; ++j) {
			bit_counts[j] += arr[i] & 1;
			arr[i] >>= 1;
		}
	}
	
	int overflow = 0;
	int ret = 0;
	int reg;
	
	for(int i = 0; i<LIMIT; ++i) {
		reg = nn1(N, bit_counts[i]);
		ret |= corrMod2(reg - overflow) << i;
		printf("Reg: %d, Current: %d, ", reg, corrMod2(reg - overflow));
		overflow=(overflow+nC2(bit_counts[i]))%2;
		printf("Overflow: %d\n", overflow);
	}	
	
	ret |= corrMod2(-overflow) << LIMIT;
	cout << ret << endl;
	return 0;
}

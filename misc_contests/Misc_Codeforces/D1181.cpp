
#include <iostream>
#include <vector>
#include <algorithm>
#include <set>
#include <utility>

using namespace std;

bool compare(pair<int,int>& p1, pair<int,int>& p2) {
	return p1.second == p2.second ? p1.second > p2.second : p1.first - p2.first;
}

int main() {
	using namespace std;
	int N,M,Q;
	cin >> N >> M >> Q;
	
	vector<int> table(M+1);
	int buf;
	for(int i = 0; i<N; ++i) {
		cin >> buf;
		++table[buf];
	}
	
	vector<pair<int,int>> sorted(M);
	for(int i = 0; i<M; ++i) {
		sorted[i].first = i+1;
		sorted[i].second = table[i+1];
	}
	
	sort(sorted.begin(), sorted.end(), compare);
	
	set<int> active;
	vector<long long> queries(Q);
	
	for(int i = 0; i<Q; ++i) {
		cin >> queries[i];
		queries[i] -= N+1;
	}
	
	sort(queries.front(), queries.end());
	table[0] = 0;
	
	int queryPtr = 0;
	int accm = 0;
	
	auto iter = 
	// should use pointer but whatever...
	for(int i = 0; i<sorted.size()-1; ++i) {
		active.insert(sorted[i].first);
		int diff = sorted[i+1].second - sorted[i].second;

		while(queryPtr < queries.size() && queries[i] < accm+diff*active.size()) {
			// answer all these queries
			active.find()
		}
	}
}

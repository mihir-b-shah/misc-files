
#include <iostream>
#include <cmath>
#include <cstring>

class MergeSortTree {
	private:
		int* tree;
		int* end;
		void build(int,int);
	public:
		MergeSortTree(int N, int* data);
		~MergeSortTree();
		int freq(int,int);
};

MergeSortTree::MergeSortTree(int N, int* data) {
	int size = N*ceil(log2(N));
	tree = new int[size];
	end = tree+size;
}

MergeSort

MergeSortTree::~MergeSortTree() {
	delete(tree);
	end = nullptr;
}

int MergeSortTree::freq(int start, int end, int bound) {
	
}

int main() {
	using namespace std;
	
	
}


#include <iostream>
#include <cstring>
#include <string>

class matrix {
	char* array;
	int m;
	int n;
	
	public:
		matrix(int,int) {
			array = new char[m*n];
			this->n = n;
			this->m = m;	
		};
		
		~matrix() {
			free(array);
		};
		
		char* at(int i,int j) {
			return array+i*m+j;
		};
		
		int height() {
			return n;
		};
		
		int width() {
			return m;
		};
		
		void print() {
			for(int i = 0; i<n; ++i) {
				for(int j = 0; j<m; ++j) {
					std::cout << *(matrix::at(i,j)) << ' ';
				}
				std::cout << '\n';
			}
		};
};

int main() {
	using namespace std;
	int buf;
	cin >> buf;
	const int N = buf;
	cin >> buf;
	const int M = buf;
	cin >> buf;
	const int Q = buf;
	
	cout << N << ' ' << M << '\n';
	matrix picture(N,M);
	char ch;
	for(int i = 0; i<N; ++i) {
		for(int j = 0; j<M; ++j) {
			cin >> ch;
			*picture.at(i,j) = ch;
		}
	}
	
	
	
	cout.flush();
}

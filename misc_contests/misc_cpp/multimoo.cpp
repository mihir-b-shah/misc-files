
#include <fstream>
#include <iostream>
#include <algorithm>
#include <map>
#include <vector>
#include <utility>

using namespace std;

int dx[4] = {1,0,-1,0};
int dy[4] = {0,1,0,-1};
bool visited[250][250];
int grid[250][250];
int N;
int curr_f;
map<int, vector<pair<int,int>>> regions;

typedef struct comparator {
    bool operator()(const vector<pair<int,int>>& a, const vector<pair<int,int>>& b) {
        return b.size()-a.size();
    }
};

int flood_fill(int x, int y) {
    visited[x][y] = 1;
    regions[curr_f].push_back(make_pair(x,y));
    int sum = 1;
    for(int i = 0; i<4; ++i) {
        if(x + dx[i] >= 0 && x + dx[i] < N && y + dy[i] >= 0 
            && y + dy[i] < N && !visited[x+dx[i]][y+dy[i]] && grid[x+dx[i]][y+dy[i]] == curr_f) {
            sum += flood_fill(x+dx[i], y+dy[i]);
        }
    }
    return sum;
}

int main() {
    ifstream fin;
    fin.open("multimoo.in");
    fin >> N;

    for(int i = 0; i<N; ++i) {
        for(int j = 0; j<N; ++j) {
            fin >> grid[i][j];
        }
    }

    fin.close();

    int val = 0;
    for(int i = 0; i<N; ++i) {
        for(int j = 0; j<N; ++j) {
            if(!visited[i][j]) {
                curr_f = grid[i][j];
                val = max(val, flood_fill(i,j));
            }
        }
    }

    multimap<vector<pair<int,int>>, int, comparator> revmap;
    for(auto& pr: regions) {
        revmap.insert(pair<vector<pair<int,int>>,int>(pr.second,pr.first));
    }

    for(auto& pr: revmap) {
        cout << pr.second << ", {";
        const vector<pair<int,int>>& ref = pr.first;
        for(auto& pr2: ref) {
            cout << '(' << pr2.first << ',' << pr2.second << "), ";
        }
        cout << '}' << endl;
    }

    ofstream fout;
    fout.open("multimoo.out");
    fout << val << endl;
    fout.close();
    return 0;
}
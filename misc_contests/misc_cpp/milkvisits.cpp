
#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <cstring>
#include <set>

using namespace std;

int main() {
    ifstream fin("milkvisits.in");
    int N,M;
    fin >> N >> M;
    string HG;

    getline(fin, HG);
    getline(fin, HG);

    vector<int> parent;
    parent.reserve(N);
    for(int i = 0; i<N; ++i) {
        parent.push_back(-1);
    }

    int buf1,buf2;
    for(int i = 0; i<N-1; ++i) {
        fin >> buf1 >> buf2;
        buf1 -= 1; buf2 -= 1;

        if(parent[buf1] == -1 || parent[buf2] == -1) {
            parent[buf2] = buf1;
        } else if(parent[buf1] == -1) {
            parent[buf1] = buf2;
        } else if(parent[buf2] == -1) {
            parent[buf2] = buf1;
        }
    }

    for(int i = 0; i<parent.size(); ++i) {
        //cout << parent[i] << " ";
    } //cout << endl;

    char buf3;
    vector<bool> output;
    output.reserve(N);
    bool no;

    for(int i = 0; i<M; ++i) {
        set<int> iset;
        set<int> iset2;
        no = 1;
        
        fin >> buf1 >> buf2 >> buf3;
        buf1 -= 1; buf2 -= 1;
        int latest = -1;
        int ctr = 0;
        bool found_1y = 0;

        while(buf1 != -1) {
            iset.insert(buf1);
            if(!found_1y && HG[buf1] == buf3) {
                found_1y = 1;
                latest = buf1;
            }
            //cout << buf1 << " ";
            buf1 = parent[buf1];
        } //cout << endl;

        while(buf2 != -1) {
            if(HG[buf2] == buf3) {
                no = 0; 
            }
            iset2.insert(buf2);
            //cout << buf2 << " ";
            if(iset.find(buf2) != iset.end()) {
                break;
            }
            buf2 = parent[buf2];
        } //cout << endl;

        //cout << "no: " << no << "found: " << found_1y << "latest: " << latest << endl;
        if(!found_1y && no) output.push_back(0);
        else if(!no) output.push_back(1); 
        else if(iset2.find(latest) != iset2.end()) output.push_back(1);
        else output.push_back(0);
    }

    fin.close();
    ofstream fout("milkvisits.out");

    for(int i = 0; i<output.size(); ++i) {
        fout << output[i];
    }
    fout.close();
    return 0;
}
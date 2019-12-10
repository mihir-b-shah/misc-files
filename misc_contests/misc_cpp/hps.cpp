
#include <iostream>
#include <fstream>
#include <algorithm>

using namespace std;

inline bool win(char a, char b) {
    if(a == b) {
        return 0;
    } else {
        switch(a) {
            case 'H': return b == 'S'; break;
            case 'S': return b == 'P'; break;
            case 'P': return b == 'H'; break;
            default: return 0;
        }
    }
}

int main() {
    ifstream fin("hps.in");
    int n;
    fin >> n;
    const int N = n;
    char moves[N];

    for(int i = 0; i<N; ++i) {
        fin >> moves[i];
    }
    fin.close();

    char types[3] = {'H','S','P'};
    int max_wins = 0;

    for(int a = 0; a<3; ++a) {
        for(int b = 0; b<3; ++b) {
            int wins_1 = 0;
            int wins_2 = 0;
            // base case, switch before start of game.
            for(int i = 0; i<N; ++i) {
                wins_2 += win(types[b], moves[i]);
            }
            for(int i = 0; i<N-1; ++i) {
                // sum number of wins with types[a] through i and then from i+1 to end with types[b]
                bool a_win = win(types[a], moves[i]);
                bool b_win = win(types[b], moves[i]);
                if(a_win) {
                    if(b_win) {
                        --wins_2;
                        ++wins_1;
                    } else {
                        ++wins_1;
                    }
                } else if(b_win) {
                    --wins_2;
                }
                max_wins = max(max_wins, wins_1+wins_2);
            }
        }
    }

    ofstream fout("hps.out");
    fout << max_wins << endl;
    fout.close();
    return 0;
}

#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <cmath>
#include <algorithm>

using namespace std;

typedef struct limit {
    bool start;
    int id,xval;
};

inline bool compare(const limit& L1, const limit& L2) {
    return L1.xval<=L2.xval;
}

typedef struct segment {
    int x1,x2,y1,y2;
    float m,b;

    segment(int x1, int y1, int x2, int y2) {
        this->x1 = x1; this->x2 = x2;
        this->y1 = y1; this->y2 = y2;
        m = ((float) y2-y1)/(x2-x1);
        b = y2 - m*x2;
        printf("(%d,%d,%d,%d)->%f,%f\n",x1,y1,x2,y2,m,b);
    }
};

inline bool intersect(const segment& s1, const segment& s2) {
    if(!isnan(s1.m) && !isnan(s2.m)) {
        float calc_x = (s2.b-s1.b)/(s1.m-s2.m);
        float calc_y = s1.m*calc_x+s1.b;
        return s1.x1 <= calc_x && calc_x <= s1.x2 && s2.x1 <= calc_x && calc_x <= s2.x2;
    } else if(!isnan(s1.m)) {
        float calc = s1.m*s1.x1+s1.b;
        return s1.x1 <= s2.x1 && s2.x1 <= s1.x2 && s2.y1 <= calc && calc <= s2.y2;
    } else if(!isnan(s2.m)) {
        float calc = s2.m*s1.x1+s2.b;
        return s2.x1 <= s1.x1 && s1.x1 <= s2.x2 && s1.y1 <= calc && calc <= s2.y1;
    } else return 0;
}

int main() {
    ifstream fin("cowjump.in");
    int N;
    fin >> N;

    int a,b,c,d;
    vector<limit> pts;
    pts.reserve(N<<1);
    vector<segment> segments;
    segments.reserve(N);

    for(int i = 0; i<N; ++i) {
        fin >> a >> b >> c >> d;
        pts.push_back({1,i,a}); pts.push_back({0,i,c});

        if(a == c)
            if(b < d) segments.push_back(segment(a,b,c,d));
            else segments.push_back(segment(c,d,a,b));
        else
            if(a < c) segments.push_back(segment(a,b,c,d));
            else segments.push_back(segment(c,d,a,b));
    }
    fin.close();
    sort(pts.begin(), pts.end(), compare);

    set<int> active;

    for(int i = 0; i<pts.size(); ++i) {
        if(pts[i].start) {

        } else {

        }
    }

    ofstream fout("cowjump.out");
    fout.close();
    return 0;
}
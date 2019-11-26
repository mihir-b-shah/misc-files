#include <iostream>
#include <string>
#include <regex>

using namespace std;

int main() {
    cout << "Hi welcome to the MC1!" << endl;
    cout << "Enter file name: ";
    string filename;
    cin >> filename;
    int len = filename.length();
    if(regex_match(filename, regex(".*\\.mc1"))) {
        cout << "Yay!";   
    } else {
        cerr << endl << "Error: file is not of type .mc1" 
            << endl << endl;
    }
}
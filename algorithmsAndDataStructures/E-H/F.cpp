#include <string>
#include <vector>
#include <iostream>
#include <algorithm>

using namespace std;
bool cmp(const string& a, const string& b){
    return a+b > b+a;
}
int main()
{
    vector<string> A;
    string input;
    while(cin >> input){
        A.push_back(input);
    }

    sort(A.begin(), A.end(), cmp);
    for(const auto & j : A)
        cout << j;
}
#include <iostream>
#include "vector"

using namespace std;
int main() {
    int N, K;
    vector<int> coords;
    cin >> N >> K;
    int input;
    for (int i = 0; i < N; ++i){
        cin >> input;
        coords.push_back(input);
    }
    int left = 0;
    int right = coords.back();

    while (right - left > 1){
        int mid = (left + right) / 2;

        int cows = 1;
        int last_cow = coords[0];
        for (int i = 1; i < N; ++i){
            if (coords[i] - last_cow >= mid){
                cows++;
                last_cow = coords[i];
            }
        }
        if (cows >= K){
            left = mid ;
        }
        else{
            right = mid;
        }
    }
    cout << left;
}

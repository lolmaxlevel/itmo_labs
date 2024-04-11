#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    int n;
    cin >> n;
    vector<vector<int>> fuel(n, vector<int>(n));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            cin >> fuel[i][j];
        }
    }

    for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                fuel[i][j] = max(fuel[i][j], min(fuel[i][k], fuel[k][j]));
            }
        }
    }

    int maxFuel = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            maxFuel = max(maxFuel, fuel[i][j]);
        }
    }

    cout << maxFuel << endl;

    return 0;
}
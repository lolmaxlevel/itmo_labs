#include <iostream>
#include <algorithm>
#include <vector>
#include <cmath>

using namespace std;

int main() {
    int n, k;
    cin >> n >> k;

    vector<int> prices(n);
    for (int i = 0; i < n; i++) {
        cin >> prices[i];
    }

    sort(prices.begin(), prices.end());
    reverse(prices.begin(), prices.end());
    int ans = 0;
    for (int i = 0; i < n; i++) {
        if ((i+1) % k != 0) {
            ans += prices[i];
        }
    }

    cout << ans << endl;

    return 0;
}
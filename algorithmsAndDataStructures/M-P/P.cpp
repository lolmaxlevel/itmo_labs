#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

const int INF = 1e9;

bool bfs(int mid, vector<vector<int>>& fuel, int n) {
    vector<bool> visited(n, false);
    queue<int> q;
    q.push(0);
    visited[0] = true;

    while (!q.empty()) {
        int u = q.front();
        q.pop();

        for (int v = 0; v < n; v++) {
            if (!visited[v] && fuel[u][v] <= mid) {
                visited[v] = true;
                q.push(v);
            }
        }
    }

    return count(visited.begin(), visited.end(), true) == n;
}

int main() {
    int n;
    cin >> n;

    vector<vector<int>> fuel(n, vector<int>(n));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            cin >> fuel[i][j];
        }
    }

    int low = 0, high = INF;
    while (low < high) {
        int mid = low + (high - low) / 2;
        if (bfs(mid, fuel, n)) {
            high = mid;
        } else {
            low = mid + 1;
        }
    }

    cout << low << endl;

    return 0;
}
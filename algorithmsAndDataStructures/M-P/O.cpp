#include <iostream>
#include <vector>

using namespace std;

vector<vector<int>> adj;
vector<int> color;

bool dfs(int v, int c) {
    color[v] = c;
    for (int u : adj[v]) {
        if (color[u] == -1) {
            if (!dfs(u, 1 - c)) {
                return false;
            }
        } else if (color[u] == color[v]) {
            return false;
        }
    }
    return true;
}

int main() {
    int n, m;
    cin >> n >> m;
    adj.resize(n + 1);
    color.assign(n + 1, -1);
    for (int i = 0; i < m; i++) {
        int a, b;
        cin >> a >> b;
        adj[a].push_back(b);
        adj[b].push_back(a);
    }
    for (int i = 1; i <= n; i++) {
        if (color[i] == -1) {
            if (!dfs(i, 0)) {
                cout << "NO" << endl;
                return 0;
            }
        }
    }
    cout << "YES" << endl;
    return 0;
}
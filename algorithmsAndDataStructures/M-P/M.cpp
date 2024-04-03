#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

const int MAXN = 1000;
const int INF = 1e9;
const vector<pair<int, int>> directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
const vector<char> dirChar = {'N', 'S', 'W', 'E'};

int n, m, xStart, yStart, xEnd, yEnd;
char world[MAXN][MAXN];
int cost[MAXN][MAXN];
pair<int, int> parent[MAXN][MAXN];

void bfs() {
    priority_queue<pair<int, pair<int, int>>, vector<pair<int, pair<int, int>>>, greater<pair<int, pair<int, int>>>> q;
    q.push({0, {xStart, yStart}});
    cost[xStart][yStart] = 0;

    while (!q.empty()) {
        auto [c, cell] = q.top();
        auto [x, y] = cell;
        q.pop();

        if (c > cost[x][y]) continue;

        for (int i = 0; i < 4; i++) {
            int newX = x + directions[i].first;
            int newY = y + directions[i].second;

            if (newX >= 0 && newX < n && newY >= 0 && newY < m && world[newX][newY] != '#') {
                int newCost = cost[x][y] + (world[newX][newY] == '.' ? 1 : 2);
                if (newCost < cost[newX][newY]) {
                    cost[newX][newY] = newCost;
                    parent[newX][newY] = {x, y};
                    q.push({newCost, {newX, newY}});
                }
            }
        }
    }
}

int main() {
    cin >> n >> m >> xStart >> yStart >> xEnd >> yEnd;
    xStart--; yStart--; xEnd--; yEnd--;

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            cin >> world[i][j];
            cost[i][j] = INF;
        }
    }

    bfs();

    if (cost[xEnd][yEnd] == INF) {
        cout << -1 << endl;
        return 0;
    }

    vector<char> path;
    for (pair<int, int> p = {xEnd, yEnd}; p != make_pair(xStart, yStart); p = parent[p.first][p.second]) {
        pair<int, int> diff = {p.first - parent[p.first][p.second].first, p.second - parent[p.first][p.second].second};
        path.push_back(dirChar[find(directions.begin(), directions.end(), diff) - directions.begin()]);
    }

    reverse(path.begin(), path.end());

    cout << cost[xEnd][yEnd] << endl;
    for (char c : path) {
        cout << c;
    }
    cout << endl;

    return 0;
}
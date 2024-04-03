#include <iostream>
#include <vector>

using namespace std;

const int MAXN = 105;
vector<int> piggyBanks[MAXN];
int status[MAXN];
int breakCount = 0;

void DFS(int currentNode) {
  status[currentNode] = 1;
  for (int nextNode : piggyBanks[currentNode]) {
    if (status[nextNode] == 0) {
      DFS(nextNode);
    } else if (status[nextNode] == 1){
      breakCount++;
    }
  }
  status[currentNode] = 2;
}

int main() {
  int n, key;
  cin >> n;
  for (int i = 0; i < n; ++i) {
    cin >> key;
    piggyBanks[--key].push_back(i);
  }

  for (int i = 0; i < n; ++i) {
    if (status[i] == 0) {
      DFS(i);
    }
  }

  cout << breakCount << endl;

  return 0;
}
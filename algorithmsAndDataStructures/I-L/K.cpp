#include <iostream>
#include <queue>
#include <vector>

using namespace std;

struct Block {
    int size, start;
    bool operator<(const Block& other) const {
        if (size == other.size) return start > other.start;
        return size < other.size;
    }
};

int main() {
    int N, M;
    cin >> N >> M;

    priority_queue<Block> freeBlocks;
    freeBlocks.push({N, 1});

    vector<Block> allocatedBlocks(M + 1);

    for (int i = 1; i <= M; i++) {
        int K;
        cin >> K;

        if (K > 0) {
            while (!freeBlocks.empty() && freeBlocks.top().size < K) {
                freeBlocks.pop();
            }
            if (freeBlocks.empty()) {
                cout << -1 << endl;
                allocatedBlocks[i] = {0, 0};
            } else {
                Block block = freeBlocks.top();
                freeBlocks.pop();
                cout << block.start << endl;
                allocatedBlocks[i] = {K, block.start};
                if (block.size > K) {
                    freeBlocks.push({block.size - K, block.start + K});
                }
            }
        } else {
            int T = -K;
            Block block = allocatedBlocks[T];
            if (block.size > 0) {
                freeBlocks.push(block);
            }
        }
    }

    return 0;
}
#include <iostream>
#include <deque>
#include <vector>

using namespace std;

int main() {
    int n, k;
    cin >> n >> k;
    vector<int> a(n);
    for (int i = 0; i < n; i++) {
        cin >> a[i];
    }

    deque<int> deque;
    for (int i = 0; i < k; i++) {
        deque.push_back(a[i]);
        while (deque.size() > 1 && deque.back() < deque[deque.size() - 2]) {
            deque.erase(deque.end() - 2);
        }
    }
    
    cout << deque.front() << " ";
    for (int i = k; i < n; i++) {
        if (deque.front() == a[i - k] || deque.size() > k) {
            deque.pop_front();
        }
        deque.push_back(a[i]);
        while (deque.size() > 1 && deque.back() < deque[deque.size() - 2]) {
            deque.erase(deque.end() - 2);
        }
        cout << deque.front() << " ";
    }
    return 0;
}
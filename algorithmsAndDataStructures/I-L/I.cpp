#include <iostream>
#include <vector>
#include <map>
#include <queue>
#include <set>

using namespace std;

int main() {
    int n, k, p;
    int ans = 0;
    cin >> n >> k >> p;
    int cars[p];

    bool on_shelf[n];

    vector<queue<int>> used_cars(n);

    set<int> floor_queue;

    for (int i = 0; i < p; i++) {
        on_shelf[i] = true;
    }

    for (int i = 0; i < p; i++) {
        cin >> cars[i];

        used_cars[cars[i] - 1].emplace(i);
    }
    int cars_on_floor = -1;
    for (int i = 0; i < p; i++) {
       int car = cars[i] - 1;
        if (on_shelf[car]) {
            ans++;
            cars_on_floor++;
            if (cars_on_floor >= k) {
                int remove_car = *floor_queue.rbegin();
                // и тут костыль =(
                if (remove_car >= 999999){
                    on_shelf[remove_car - 999999] = true;
                }
                else{
                    on_shelf[cars[remove_car] - 1] = true;
                }
                floor_queue.erase(*floor_queue.rbegin());
            }
            on_shelf[car] = false;
        }
        int a = used_cars[car].front();
        used_cars[car].pop();
        if (used_cars[car].empty()) {
            // костыль что бы сохранять уникальность машинки =(
            floor_queue.insert(999999 + car);
        } else {
            floor_queue.insert(used_cars[car].front());
        }
    }

    cout << ans << "\n";
    return 0;
}

#include <iostream>
#include <vector>

using namespace std;


int main() {
    int n;
    vector<int> queue;
    cin >> n;

    char request;
    for (int i = 0; i < n; ++i){
        cin >> request;
        if (request == '-'){
            cout << queue[0] << "\n";
            queue.erase(queue.begin());
        }
        else {
            int num;
            cin >> num;
            if (request == '+'){
                queue.push_back(num);
        } else {
                int add = queue.size()%2;
                queue.insert(queue.begin() + ((queue.size())/2)+add, num);
            }
        }
    }
}

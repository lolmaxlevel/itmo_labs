#include <iostream>

using namespace std;

int main() {

    int a, b, c, d, k;
    cin >> a;
    cin >> b;
    cin >> c;
    cin >> d;
    cin >> k;

    int currentDay;
    
    if (d == 1 && b>c){
        cout << 1;
    }
    else {
        for (int i = 0; i < k; i++) {

            currentDay = a * b - c;

            if (currentDay >= d){
                currentDay = d;
            }

            if (a == currentDay){
                break;
            }

            if (currentDay <= 0) {
                currentDay = 0;
                break;
            }
            a = currentDay;
        }

        cout << currentDay;
    }
}
#include <iostream>
#include <stack>
#include <vector>

using namespace std;

int main() {

    stack<char> mainStack;

    stack<int> animalCounterStack;
    stack<int> trapCounterStack;

    int animalCounter = 0;
    int trapCounter = -1;

    string data;
    cin >> data;

    vector<int> v(data.size()/2);

    mainStack.push(0);

    for (char letter : data) {

        if (islower(letter)) {
            animalCounterStack.push(++animalCounter);
        } else
        {
            trapCounterStack.push(++trapCounter);
        }
        if (( (islower(mainStack.top()) && isupper(letter)) || (isupper(mainStack.top()) && islower(letter)))
            && tolower(mainStack.top()) == tolower(letter)) {

                v[trapCounterStack.top()] = animalCounterStack.top();
                animalCounterStack.pop();
                trapCounterStack.pop();
                mainStack.pop();

            } else
            {
                mainStack.push(letter);
            }
    }

    if (mainStack.size() == 1) {
        cout << "Possible" << endl;
        for (int i : v) {
            cout << i << " ";
        }
    } else
    {
        cout << "Impossible" << endl;
    }

}
#include <iostream>
#include <vector>
using namespace std;


int main() {
    int n;
    cin >> n;
    vector<int> v(n);
    for (auto& it : v)
        cin >> it;
//    for(auto& i:v)
//    {
//        cout<<i<<" ";
//    }
    int max1 = 0;
    int count = 0;
    int index = 0;

    for (int i = 1; i< n + 1; i++){
        if ((v[i - 1] == v[i]) && (v[i]== v[i + 1])){
            count++;
            if (count > max1){
                max1 = count + 1;
                index = i + 1;
            }
            count = 0;
        }
        else{
            count++;
            if (count > max1){
                max1 = count;
                index = i;
            }
        }
    }
    cout<<index-max1+1<<" "<<index;
}
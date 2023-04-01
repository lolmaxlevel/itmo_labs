#include <iostream>
#include <vector>
#include <unordered_map>
#include <cstring>
#include <stack>
using namespace std;


int main() {
    unordered_map<string, stack<int>> hackmap;
    string currentString;
    vector<unordered_map<string , int>> callStack;
    callStack.emplace_back();
    string str="-123456789";
    std::ios::sync_with_stdio(false);

    while(cin >> currentString){

        if (currentString == "{"){
            callStack.emplace_back();
            continue;
        }

        if (currentString == "}"){
            for (const auto & [ key, value ] : callStack.back()) {
                hackmap[key].pop();
                if (hackmap[key].empty()){
                    hackmap.erase(key);
                }
            }
            callStack.pop_back();
            continue;
        }

        size_t EndPart1 = currentString.find_first_of('=');
        string var1 = currentString.substr(0, EndPart1);
        string var2 = currentString.substr(EndPart1 + 1);



        if (str.find(var2[0]) == std::string::npos){ // if right side is a variable

            int new_val;
            try {
                if (hackmap.at(var2).empty()){
                    new_val = 0;
                }else{
                    new_val = callStack[hackmap.at(var2).top() - 1].at(var2) ;
                }
            } catch (...){
                new_val = 0;
            }
            callStack.back()[var1] = new_val;
            cout << new_val<<"\n";
//            for (int i = callStack.size() - 1; i >= 0; i--)
//            {
//                try {
//                    int new_val = callStack[i].at(var2);
//                    cout << new_val << endl;
//                    callStack.back()[var1] = new_val;
//                    flag = true;
//                    break;
//                } catch (...){ continue;}
//            }
//            if (!flag){
//                cout << 0 << endl;
//                callStack.back()[var1] = 0;
//            }
        }else{
            callStack.back()[var1] = atoi(var2.c_str());
        }
        if (hackmap.count(var1) == 0 ){
            hackmap[var1].push(callStack.size());
        }else{
            if (hackmap[var1].empty() || hackmap[var1].top() != callStack.size()){
                hackmap[var1].push(callStack.size());
            }
        }
    }
}
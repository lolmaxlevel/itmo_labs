#include <iostream>
#include <algorithm>

using namespace std;

int weights[26];

bool cmp(char a, char b) {
    if (weights[a - 'a'] == weights[b - 'a']) return a > b;
    return weights[a - 'a'] > weights[b - 'a'];
}

int main() {
    string s, ans, multiple_letters;

    cin >> s;

    string start, end;

    for (int i = 0; i < 26; ++i){
        cin >> weights[i];
    }
    sort(s.begin(), s.end(), cmp);

    for (int i = 0; i < s.size(); ++i){
        if (s[i] == s[i + 1]){
            if (end.empty() || end.back() != s[i]){
                end.push_back(s[i]);
                i++;
            }
            else{
                ans.push_back(s[i]);
            }
        }
        else{
            ans.push_back(s[i]);
        }
    }

    cout << end << ans;
    reverse(end.begin(), end.end());
    cout << end;
}
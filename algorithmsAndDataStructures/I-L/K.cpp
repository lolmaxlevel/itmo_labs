#include <iostream>
#include <vector>

using namespace std;

struct Segment {
    int start, length;
    bool isOccupied;
    Segment* next;
    Segment* prev;

    Segment(int start, int length) : start(start), length(length), isOccupied(false), next(nullptr), prev(nullptr) {}

    Segment() : start(0), length(0), isOccupied(false), next(nullptr), prev(nullptr) {}  // default constructor
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr); cout.tie(nullptr);
    int N, M;
    cin >> N >> M;

    auto* head = new Segment(0, N);

    vector<Segment*> allocations(M);
    vector<int> results;

    for (int i = 0; i < M; i++) {
        int request;
        cin >> request;

        if (request > 0) {
            Segment* current = head;
            while (current != nullptr && (current->isOccupied || current->length < request)) {
                current = current->next;
            }
            if (current != nullptr && current->length >= request) {
                current->isOccupied = true;

                allocations[i] = current;
                results.push_back(current->start + 1);

                if (current->length > request) {
                    auto* newSegment = new Segment(current->start + request, current->length - request);
                    newSegment->next = current->next;
                    newSegment->prev = current;
                    if (current->next != nullptr) {
                        current->next->prev = newSegment;
                    }
                    current->next = newSegment;
                    current->length = request;
                }
            } else {
                allocations[i] = nullptr;
                results.push_back(-1);
            }
        } else {
            if (results[-request - 1] == -1 || allocations[-request - 1] == nullptr) {
                allocations[i] = nullptr;
                continue;
            }
            Segment* segment = allocations[-request - 1];
            segment->isOccupied = false;
            allocations[i] = nullptr;

            if (segment->prev != nullptr && !segment->prev->isOccupied) {
                segment->prev->length += segment->length;
                segment->prev->next = segment->next;
                if (segment->next != nullptr) {
                    segment->next->prev = segment->prev;
                }
                segment = segment->prev;
            }

            if (segment->next != nullptr && !segment->next->isOccupied) {
                segment->length += segment->next->length;
                if (segment->next->next != nullptr) {
                    segment->next->next->prev = segment;
                }
                segment->next = segment->next->next;
            }
        }
    }

    for (int result: results) {
        cout << result << endl;
    }

    return 0;
}
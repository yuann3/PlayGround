#include <iostream>
#include <vector>
#include <cassert>

class LinkedList {
  private:
    struct ListNode {
      int val;
      ListNode* next;
      ListNode() : val(0), next(nullptr) {}
      ListNode(int x) : val(x), next(nullptr) {}
      ListNode(int x, ListNode* next) : val(x), next(next) {}
    };

    ListNode* head;

  public:
    LinkedList() : head(nullptr) {}

    ~LinkedList() {
      clear();
    }

    void push_back(int val) {
      if (!head) {
        head = new ListNode(val);
        return;
      }
      ListNode* current = head;
      while (current->next) {
        current = current->next;
      }
      current->next = new ListNode(val);
    }

    void clear() {
      while (head) {
        ListNode* temp = head;
        head = head->next;
        delete temp;
      }
    }

    std::vector<int> toVector() const {
      std::vector<int> result;
      ListNode* current = head;
      while (current) {
        result.push_back(current->val);
        current = current->next;
      }
      return result;
    }

    void print() const {
      ListNode* current = head;
      while (current) {
        std::cout << current->val;
        if (current->next) {
          std::cout << " -> ";
        }
        current = current->next;
      }
      std::cout << std::endl;
    }

    LinkedList merge(const LinkedList& list1, const LinkedList& list2) {
      LinkedList merged;
      const ListNode* ls1 = list1.head();
      const ListNode* ls2 = list2.head();

      while (ls1 && ls2) {
        if (ls1->val <= ls2->val) {
          merged.push_back(ls1->val);
          ls1 = ls1->next;
        } else {
          merged.push_back(ls2->val);
          ls2 = ls2->next;
        }
      }

      // Handle remaining elements in ls1, if any
      while (ls1) {
        merged.push_back(ls1->val);
        ls1 = ls1->next;
      }

      // Handle remaining elements in ls2, if any
      while (ls2) {
        merged.push_back(ls2->val);
        ls2 = ls2->next;
      }

      return merged;
    }
};

void testMergeTwoLists() {
  // Test case 1: Merge two non-empty lists
  LinkedList list1, list2;
  list1.push_back(1);
  list1.push_back(3);
  list1.push_back(5);
  list2.push_back(2);
  list2.push_back(4);
  list2.push_back(6);

  std::cout << "List 1: ";
  list1.print();
  std::cout << "List 2: ";
  list2.print();

  LinkedList mergedList = LinkedList::merge(list1, list2);
  std::cout << "Merged List: ";
  mergedList.print();

  std::vector<int> result = mergedList.toVector();
  std::vector<int> expected = {1, 2, 3, 4, 5, 6};
  assert(result == expected && "Test case 1 failed");

  // Test case 2: One empty list
  LinkedList list3, emptyList;
  list3.push_back(1);
  list3.push_back(3);
  list3.push_back(5);

  std::cout << "\nList 3: ";
  list3.print();
  std::cout << "Empty List: ";
  emptyList.print();

  LinkedList mergedList2 = LinkedList::merge(list3, emptyList);
  std::cout << "Merged List: ";
  mergedList2.print();

  result = mergedList2.toVector();
  expected = {1, 3, 5};
  assert(result == expected && "Test case 2 failed");

  // Test case 3: Both empty lists
  LinkedList emptyList1, emptyList2;
  LinkedList mergedList3 = LinkedList::merge(emptyList1, emptyList2);
  std::cout << "\nMerged empty lists: ";
  mergedList3.print();

  result = mergedList3.toVector();
  expected = {};
  assert(result == expected && "Test case 3 failed");

  std::cout << "All test cases passed!" << std::endl;
}

int main() {
  testMergeTwoLists();
  return 0;
}

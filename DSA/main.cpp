#include "helpers.hpp"

using namespace std;

class Solution {
  public:
    ListNode* mergeTwoLists(ListNode* list1, ListNode* list2) {
        LinkedList* merged;
        ListNode* ls1 = list1.head();
        ListNode* ls2 = list2.head();

        while (ls1 && ls2) {
            if (ls1->val <= ls2->val) {
                merged.push_back(ls1->val);
                merged.push_back(ls2->val);
            } else {
                merged.push_back(ls2->val);
                merged.push_back(ls1->val);
            }
            ls1 = ls1->next;
            ls2 = ls2->next;
        }
        return merged;
    
};

int main() {
  // // Test case 1: Basic binary tree (unbanlanced)
  // vector<int> treeValues1 = {1, 2, 2, 3, 3, INT_MIN, INT_MIN, 4, 4};
  // TreeNode* root1 = createBinaryTree(treeValues1);
  // cout << "\nTest case 1:" << endl;
  // cout << "Binary Tree:" << endl;
  // visualizeTreeEnhanced(root1);
  // cout << "Tree 1 is balanced: " << (solution.isBalanced(root1) ? "true" : "false") << endl;
  // delete root1;
  //
  // // Test case 2: balanced tree
  // vector<int> treeValues2 = {3, 9, 20, INT_MIN, INT_MIN, 15, 7};
  // TreeNode* root2 = createBinaryTree(treeValues2);
  // cout << "\nTest case 2:" << endl;
  // cout << "Binary Tree:" << endl;
  // visualizeTreeEnhanced(root2);
  // cout << "Tree 2 is balanced: " << (solution.isBalanced(root2) ? "true" : "false") << endl;
  // delete root2;

  Solution solution;

  return 0;
}

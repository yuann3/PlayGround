#include "helpers.hpp"
#include <iostream>
#include <queue>
#include <climits>
#include <iomanip>

// ANSI color codes
#define RESET   "\033[0m"
#define RED     "\033[31m"
#define GREEN   "\033[32m"
#define YELLOW  "\033[33m"
#define BLUE    "\033[34m"
#define MAGENTA "\033[35m"
#define CYAN    "\033[36m"

void printVector(const std::vector<int>& vec) {
  std::cout << "[";
  for (size_t i = 0; i < vec.size(); i++) {
    std::cout << vec[i];
    if (i != vec.size() - 1) std::cout << ", ";
  }
  std::cout << "]" << std::endl;
}

void printLinkedList(ListNode* head) {
  while (head != nullptr) {
    std::cout << head->val;
    if (head->next != nullptr) std::cout << " -> ";
    head = head->next;
  }
  std::cout << std::endl;
}

ListNode* createLinkedList(const std::vector<int>& vec) {
  ListNode dummy(0);
  ListNode* current = &dummy;
  for (int num : vec) {
    current->next = new ListNode(num);
    current = current->next;
  }
  return dummy.next;
}

TreeNode* createBinaryTree(const std::vector<int>& values) {
  if (values.empty()) return nullptr;

  TreeNode* root = new TreeNode(values[0]);
  std::queue<TreeNode*> q;
  q.push(root);

  for (size_t i = 1; i < values.size(); i += 2) {
    TreeNode* current = q.front();
    q.pop();

    if (i < values.size() && values[i] != INT_MIN) {
      current->left = new TreeNode(values[i]);
      q.push(current->left);
    }

    if (i + 1 < values.size() && values[i + 1] != INT_MIN) {
      current->right = new TreeNode(values[i + 1]);
      q.push(current->right);
    }
  }

  return root;
}

void printBinaryTree(TreeNode* root) {
  if (!root) {
    std::cout << "[]" << std::endl;
    return;
  }

  std::queue<TreeNode*> q;
  q.push(root);
  std::vector<int> result;

  while (!q.empty()) {
    int levelSize = q.size();
    for (int i = 0; i < levelSize; i++) {
      TreeNode* node = q.front();
      q.pop();

      if (node) {
        result.push_back(node->val);
        q.push(node->left);
        q.push(node->right);
      } else {
        result.push_back(INT_MIN);
      }
    }
  }

  // Remove trailing nulls
  while (!result.empty() && result.back() == INT_MIN) {
    result.pop_back();
  }

  // Print the result
  std::cout << "[";
  for (size_t i = 0; i < result.size(); i++) {
    if (result[i] == INT_MIN) {
      std::cout << "null";
    } else {
      std::cout << result[i];
    }
    if (i < result.size() - 1) std::cout << ", ";
  }
  std::cout << "]" << std::endl;
}


void visualizeTreeEnhanced(TreeNode* root, std::string prefix, bool isLeft, bool isRoot) {
    if (root == nullptr) {
        return;
    }

    // Process right child first (will be on top)
    visualizeTreeEnhanced(root->right, prefix + (isLeft ? "│   " : "    "), false, false);

    // Print current node
    std::cout << prefix;
    if (!isRoot) {
        std::cout << (isLeft ? "└── " : "┌── ");
    }
    std::cout << YELLOW << root->val << RESET;
    
    // Print left and right child counts
    int leftCount = (root->left) ? 1 : 0;
    int rightCount = (root->right) ? 1 : 0;
    std::cout << CYAN << " (L:" << leftCount << ", R:" << rightCount << ")" << RESET << std::endl;

    // Process left child (will be on bottom)
    visualizeTreeEnhanced(root->left, prefix + (isLeft ? "    " : "│   "), true, false);
}


// BST methods implementation
TreeNode* BST::insert(TreeNode* node, int value) {
  if (node == nullptr) {
    return new TreeNode(value);
  }
  if (value < node->val) {
    node->left = insert(node->left, value);
  } else if (value > node->val) {
    node->right = insert(node->right, value);
  }
  return node;
}

void BST::insert(int value) {
  root = insert(root, value);
}

TreeNode* BST::search(TreeNode* node, int value) {
  if (node == nullptr || node->val == value) {
    return node;
  }
  if (value < node->val) {
    return search(node->left, value);
  }
  return search(node->right, value);
}

bool BST::search(int value) {
  return search(root, value) != nullptr;
}

void BST::inorder(TreeNode* node) {
  if (node != nullptr) {
    inorder(node->left);
    std::cout << node->val << " ";
    inorder(node->right);
  }
}

void BST::inorderTraversal() {
  inorder(root);
  std::cout << std::endl;
}

void BST::levelOrder(TreeNode* node) {
  if (node == nullptr) return;
  std::queue<TreeNode*> q;
  q.push(node);
  while (!q.empty()) {
    int levelSize = q.size();
    for (int i = 0; i < levelSize; i++) {
      TreeNode* current = q.front();
      q.pop();
      std::cout << current->val << " ";
      if (current->left) q.push(current->left);
      if (current->right) q.push(current->right);
    }
    std::cout << std::endl;
  }
}

void BST::levelOrderTraversal() {
  levelOrder(root);
}

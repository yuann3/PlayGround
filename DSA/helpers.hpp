#ifndef HELPERS_HPP
#define HELPERS_HPP

#include <algorithm>
#include <any>
#include <array>
#include <atomic>
#include <bitset>
#include <cassert>
#include <ccomplex>
#include <cctype>
#include <cerrno>
#include <cfenv>
#include <cfloat>
#include <charconv>
#include <chrono>
#include <cinttypes>
#include <ciso646>
#include <climits>
#include <clocale>
#include <cmath>
#include <codecvt>
#include <complex>
#include <condition_variable>
#include <csetjmp>
#include <csignal>
#include <cstdarg>
#include <cstdbool>
#include <cstddef>
#include <cstdint>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <ctgmath>
#include <ctime>
#include <cuchar>
#include <cwchar>
#include <cwctype>
#include <deque>
#include <exception>
#include <filesystem>
#include <forward_list>
#include <fstream>
#include <functional>
#include <future>
#include <initializer_list>
#include <iomanip>
#include <ios>
#include <iosfwd>
#include <iostream>
#include <istream>
#include <iterator>
#include <limits>
#include <list>
#include <locale>
#include <map>
#include <memory>
#include <mutex>
#include <new>
#include <numeric>
#include <optional>
#include <ostream>
#include <queue>
#include <random>
#include <ratio>
#include <regex>
#include <scoped_allocator>
#include <set>
#include <shared_mutex>
#include <sstream>
#include <stack>
#include <stdexcept>
#include <streambuf>
#include <string>
#include <string_view>
#include <system_error>
#include <thread>
#include <tuple>
#include <type_traits>
#include <typeindex>
#include <typeinfo>
#include <unordered_map>
#include <unordered_set>
#include <utility>
#include <valarray>
#include <variant>
#include <vector>
#include <string>

// Definition for a binary tree node.
struct TreeNode {
  int val;
  TreeNode *left;
  TreeNode *right;
  TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
};

// Definition for singly-linked list.
struct ListNode {
  int val;
  ListNode *next;
  ListNode(int x) : val(x), next(nullptr) {}
};

// Function declarations
void printVector(const std::vector<int>& vec);
void printLinkedList(ListNode* head);
ListNode* createLinkedList(const std::vector<int>& vec);
TreeNode* createBinaryTree(const std::vector<int>& values);
void printBinaryTree(TreeNode* root);
void visualizeTreeEnhanced(TreeNode* root, std::string prefix = "", bool isLeft = true, bool isRoot = true);

// Binary Search Tree class
class BST {
  private:
    TreeNode* root;
    TreeNode* insert(TreeNode* node, int value);
    TreeNode* search(TreeNode* node, int value);
    void inorder(TreeNode* node);
    void levelOrder(TreeNode* node);

  public:
    BST() : root(nullptr) {}
    void insert(int value);
    bool search(int value);
    void inorderTraversal();
    void levelOrderTraversal();
    TreeNode* getRoot() { return root; }
};

#endif // HELPERS_HPP

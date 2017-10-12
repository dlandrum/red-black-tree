# red-black-tree
This project is my implementation of a Red-Black self-balancing binary search tree. The core benefit of this data structure is that it ensures that a tree containing n values, which can be inserted in any manner, has a height no greater than twice the log base 2 of n. Whereas a basic binary search tree can boast this same behavior in the best case, its degenerative worst case leads to a height of n. The Red-Black tree guarantees its ideal behavior by mandating several rules:
1. Every node is either red or black.
2. The root is black.
3. Every leaf is black.
4. Each path from the root to each leaf contains the exact same number of black nodes (this is called the black-height of the tree).
5. No red node has any red children.
Whenever the integrity of one of these rules is jeopardized during an insertion or deletion, the tree restructures itself to preserve the optimal behavior.
I wrote out this project because a professor of mine recently described it as a stimulating exercise in graph theory, a subject that I enjoy greatly but have not had the chance to explore recently because of a heavy class-load in different topics. I decided to learn more about the algorithm, and I definitely enjoyed writing the source code.
My tree itself is compatible with any Comparable type T, but my driver file populates its instance of the graph with integers. Additionally, my tree rejects duplicate values, so if you see one million random numbers generated and "added" to the tree but then only 90% of those are actually found in the tree, that is the reason why.

Check out the Makefile for more info about how to run the project and driver file.

//This file written by Don Landrum
import java.util.*;
public class Tree {
	public class Node {
		int value;
		boolean isRed;
		Node parent;
		Node left;
		Node right;
		public Node() {
			value = -1234567;
			isRed = false;
			parent = null;
			left = null;
			right = null;
		}
		public Node(int aValue) {
			value = aValue;
			isRed = false;
			parent = null;
			left = null;
			right = null;
		}
	}
	public class Check {
		int checks;
		boolean contained;
		public Check() {
			checks = 0;
			contained = false;
		}
		String ToString() {
			String s = contained + ", " + checks + " checks.";
			return s;
		}
	}
	Node root;
	public Tree() {
		root = null;
	}
	String Search(int aValue) {
		Check aCheck = new Check();
		String s = aValue + ": " + Search(root, aValue, aCheck).ToString();
		return s;
	}
	Check Search(Node aNode, int aValue, Check aCheck) {
		aCheck.checks++;
		if (aNode == null) {
			aCheck.contained = false;
			return aCheck;
		}
		if (aNode.value == aValue) {
			aCheck.contained = true;
			return aCheck;
		}
		if (aNode.value < aValue) {
			return Search(aNode.right, aValue, aCheck);
		}
		else {
			return Search(aNode.left, aValue, aCheck);
		}
	}
	void Delete(int aValue) {
		Delete(root, aValue);
	}
	void Delete(Node aNode, int aValue) {
		/*
		 * if it has only left children, I reroute its left children
		 * if it has only right children, i reroute those
		 * if it has no children, it is dead
		 * if it has both children, I find its replacement, replace it, then recursively delete its replacement
		 * 
		 */
		if (aNode == null) {
			System.out.println("Not contained");
			return;
		}
		if (aNode.value == aValue) {
			if (aNode == root) {
				//special case
			}
			if (aNode.right == null && aNode.left == null) {
				if (aNode.parent.left == aNode) {
					aNode.parent.left = null;
				}
				else {
					aNode.parent.right = null;
				}
			}
			else if (aNode.right != null && aNode.left == null) {
				if (aNode.parent.left == aNode) {
					aNode.parent.left = aNode.right;
				}
				else {
					aNode.parent.right = aNode.right;
				}
				aNode.right.parent = aNode.parent;
			}
			else if (aNode.left != null && aNode.right == null) {
				if (aNode.parent.left == aNode) {
					aNode.parent.left = aNode.left;
				}
				else {
					aNode.parent.right = aNode.left;
				}
				aNode.left.parent = aNode.parent;
			}
			else {
				Node target = aNode.right;
				while (target.left != null) {
					target = target.left;
				}
				aNode.value = target.value;
				Delete(aNode.right, target.value);
				
/*				
				target.parent.left = target.right;
				target.right.parent = target.parent;
				if (aNode.parent.left == aNode) {
					aNode.parent.left = target;
				}
				else {
					aNode.parent.right = target;
				}
				target.parent = aNode.parent;
				target.left = aNode.left;
				target.right = aNode.right;
				*/
			}
		}
		else if (aNode.value < aValue) {
			Delete(aNode.right, aValue);
		}
		else {
			Delete(aNode.left, aValue);
		}
	}
	void Add(int aValue) {
		Node aNode = new Node(aValue);
		if (root == null) {
			aNode.isRed = false;
			root = aNode;
		}
		else {
			aNode.isRed = true;
			Add(aNode, root);
		}
	}
	void Add(Node aNode, Node aParent) {
		if (aNode.value < aParent.value) {
			if (aParent.left != null) {
				Add(aNode, aParent.left);
			}
			else {
				aParent.left = aNode;
				aNode.parent = aParent;
				//check for validity
				CheckForValidity(aNode, aParent);
			}
		}
		else if (aNode.value > aParent.value) {
			if (aParent.right != null) {
				Add(aNode, aParent.right);
			}
			else {
				aParent.right = aNode;
				aNode.parent = aParent;
				//check for validity
				CheckForValidity(aNode, aParent);
			}
		}
		else {
			System.out.println("Cannot add, for this value already exists in this tree");
		}
	}
	void CheckForValidity(Node aNode, Node aParent) {
		//I assume that aNode and aParent are valid
		Node aGrand = new Node();
		if (aParent.parent != null)
			aGrand = aParent.parent;
		else {
			// there is no grandparent, so there is no uncle either, aParent is the root
			if (aNode.isRed == true && aParent.isRed == false) {
				//we're good, don't do anything
			}
			else if (aNode.isRed && aParent.isRed) {
				aParent.isRed = false;
			}
			else {
				System.out.println("This shouldn't have happened. K should be red. Top error.");
				System.out.println("K, P:" + aNode.value + ", " + aParent.value);
			}
			return;
		}
		if (aNode.isRed == true && aParent.isRed == false) {
			//we're good, don't do anything
		}
		else if (aNode.isRed == true && aParent.isRed == true) {
			//double red
			//if aNode's uncle is red too, we just recolor
			//if not, we have to shift stuff all around
			if (aGrand.left != null && aGrand.right != null && aGrand.left.isRed == true && aGrand.right.isRed == true) {
				if (aGrand.isRed)
					System.out.println("Major recolor error");
				//recolor
//				RecolorFromRoot(aGrand);
				aGrand.left.isRed = false;
				aGrand.right.isRed = false;
				aGrand.isRed = true;
				if (aGrand.parent == null) {
					aGrand.isRed = false; //TODO probably not all the way right, not sure whether to comment out this line
					//RecolorFromRoot();						
					//this means aGrand is the root and it is currently red
					//make a function to recolor everything going all the way down, starting at the root
				}
				else 
					CheckForValidity(aGrand, aGrand.parent);
			}
			else {
				if (aGrand.left != null && aGrand.left == aParent) { //P left of G
					if (aParent.left != null && aParent.left == aNode) { //K left of P
						aGrand.isRed = true;
						aParent.isRed = false;
						aGrand.left = aParent.right;
						if (aParent.right != null)
							aParent.right.parent = aGrand;
						aParent.right = aGrand;
						//G's parent needs to recognize P
						if (aGrand.parent != null) {
							if (aGrand.parent.right != null && aGrand.parent.right == aGrand)
								aGrand.parent.right = aParent;
							else
								aGrand.parent.left = aParent;
							aParent.parent = aGrand.parent;
						}
						else { //here we reset the root
							root = aParent;
							aParent.parent = null;
						}
						aGrand.parent = aParent;
/*
 * I've got red K, red P, black G, and black S
 * I want to shift P to the top, G to its right child, K to its left child (still)
 * P becomes black, G becomes red
 * P.right becomes G.left
 */
					}
					else { //K right of P
						aNode.isRed = false;
						aGrand.isRed = true;
						//G's parent needs to recognize K
						if (aGrand.parent != null) {
							aNode.parent = aGrand.parent;
							if (aGrand.parent.right != null && aGrand.parent.right == aGrand)
								aGrand.parent.right = aNode;
							else
								aGrand.parent.left = aNode;
						}
						else { //reset root
							root = aNode;
							aNode.parent = null;
						}
						aGrand.left = aNode.right;
						if (aNode.right != null)
							aNode.right.parent = aGrand;
						aNode.right = aGrand;
						aGrand.parent = aNode;
						aParent.right = aNode.left;
						if (aNode.left != null)
							aNode.left.parent = aParent;
						aNode.left = aParent;
						aParent.parent = aNode;						
/*
 * I've got red K, red P, black G, and black S
 * I want K on top, K is black, K.left = P, K.right = G, P is red (still), G is red
 */
					}
				}
				else { //P right of G
					if (aParent.left != null && aParent.left == aNode) { //K left of P
						aNode.isRed = false;
						aGrand.isRed = true;
						//G's parent needs to recognize K
						if (aGrand.parent != null) {
							aNode.parent = aGrand.parent;
							if (aGrand.parent.right != null && aGrand.parent.right == aGrand)
								aGrand.parent.right = aNode;
							else
								aGrand.parent.left = aNode;
						}
						else {  //reassign the root
							aNode.parent = null;
							root = aNode;
						}
						
						aGrand.right = aNode.left;
						if (aNode.left != null)
							aNode.left.parent = aGrand;
						aParent.left = aNode.right;
						if (aNode.right != null)
							aNode.right.parent = aParent;
						aNode.right = aParent;
						aParent.parent = aNode;
						aNode.left = aGrand;
						aGrand.parent = aNode;
/*
 * I've got black S, black G, red P, and red K, K left of P
 * K goes to top, K is black, K.left = G, G is red, K.right = P
 */
					}
					else { //K right of P
						aGrand.isRed = true; //TODO I tried changing to false, but that made it go haywire
						aParent.isRed = false;
						aGrand.right = aParent.left;
						if (aParent.left != null)
							aParent.left.parent = aGrand;
						aParent.left = aGrand;
						if (aGrand.parent != null) {
							aParent.parent = aGrand.parent;
							//G's parent needs to recognize P
							if (aGrand.parent.right != null && aGrand.parent.right == aGrand)
								aGrand.parent.right = aParent;
							else
								aGrand.parent.left = aParent;
						}
						else {
							root = aParent;
							aParent.parent = null;
						}
						aGrand.parent = aParent;
/*
 * I've got black S, black G, red P, and red K, k right of P
 * P goes to top, P.r = K (still), P.l = G, G is red, G.r = P.l
 */
					}
				}
			}
		}
		else {
			System.out.println("This shouldn't have happened. K should be red. Bottom error.");
			System.out.println("K, P:" + aNode.value + ", " + aParent.value);
		}
	}
	void RecolorFromRoot() {
		if (root.isRed) {
			RecolorFromRoot(root);
		}
		else {
			System.out.println("Recolor From Root was called in error");
		}
	}
	void RecolorFromRoot(Node aNode) {
		//this method assumes that the color on aNode is correct, and changes the colors of its children accordingly
		//this method isn't restructuring my tree correctly
		if (aNode != null) {
			if (aNode.isRed)
				aNode.isRed = false;
			else
				aNode.isRed = true;
			RecolorFromRoot(aNode.right);
			RecolorFromRoot(aNode.left);
		}
	}
	void InOrderTraversal() {
		System.out.println("In-order");
		InOrderTraversal(root);
	}
	void PreOrderTraversal() {
		System.out.println("Pre-order");
		PreOrderTraversal(root);
	}
	void PostOrderTraversal() {
		System.out.println("Post-order");
		PostOrderTraversal(root);
	}
	void InOrderTraversal(Node aNode) {
		if (aNode != null) {
			InOrderTraversal(aNode.left);
			System.out.println(aNode.value + " " + aNode.isRed);
			InOrderTraversal(aNode.right);
		}
	}
	void PreOrderTraversal(Node aNode) {
		if (aNode != null) {
			System.out.println(aNode.value + " " + aNode.isRed);
			PreOrderTraversal(aNode.left);
			PreOrderTraversal(aNode.right);
		}
	}
	void PostOrderTraversal(Node aNode) {
		if (aNode != null) {
			PostOrderTraversal(aNode.left);
			PostOrderTraversal(aNode.right); 
			System.out.println(aNode.value + " " + aNode.isRed);
		}
	}
}
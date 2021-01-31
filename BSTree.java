// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    private Boolean isSentinel(BSTree T){
        if (T==null) return false;
        else return (T.parent==null && T.left==null);
    }

    private BSTree getSentinel(BSTree root){
        while(root.parent!=null){
            root=root.parent;
        }
        return root;
    }

    private BSTree getRoot(BSTree node){
        if (isSentinel(node)) return node.right;
        else {
            while(!isSentinel(node.parent)){
            node=node.parent;
            }
        }
        return node;
    }


    private void swap(BSTree T1, BSTree T2){
        int temp = T1.key;
        T1.key= T2.key;
        T2.key = temp;
        int temp2 = T1.address;
        T1.address = T2.address;
        T2.address = temp2;
        int temp3 = T1.size;
        T1.size= T2.size;
        T2.size = temp3;
    }    



    public BSTree Insert(int address, int size, int key){
        BSTree root = getRoot(this);
        BSTree Node = new BSTree(address, size, key);
        Node.left=null;
        Node.right=null;
        Node.parent=null;
        BSTree prev=getSentinel(this);
        if (root==null){
            
            Node.parent = prev;
            prev.right = Node;
            return Node;
        }
        while(root!=null) {
            prev=root;
            if (key<root.key){
                root=root.left;
            }
            else if (root.key<key){
                root=root.right;
            }
            else if (address<root.address){
                    root=root.left;
            }
            else root=root.right;
        }
        if (key<prev.key){
            prev.left = Node;
            Node.parent = prev;
            return Node;
        } else if (key>prev.key){
            prev.right = Node;
            Node.parent = prev;
            return Node;
        } else if (address<prev.address){
            prev.left = Node;
            Node.parent = prev;
            return Node;
        } else {
            prev.right = Node;
            Node.parent = prev;
            return Node;
        }
        // return null;
    }

    


    public boolean Delete(Dictionary e){
        BSTree root = getRoot(this);
        BSTree prev=getSentinel(this);
        if (root==null) return false;
        else {
            // if (root.key!=e.key || root.address!=e.address){
                while(root!=null && (root.key!=e.key || root.address!=e.address)){
                    prev=root;
                    if (e.key<root.key){
                        root=root.left;
                    }
                    else if (root.key<e.key){
                        root=root.right;
                    }
                    else {
                        // prev=root;
                        if (e.address<root.address){
                            root=root.left;
                        }
                        else if (root.address<e.address){
                            root=root.right;
                        }
                    }
                    // if (root==null) return false;
                }
            // }
            if (root==null) return false;
            
            if(root.left==null && root.right==null){
                if (prev.left==root) {
                    prev.left=null;
                    return true;
                }
                else {
                    prev.right=null;
                    return true;
                }
            } else if (root.left==null){
                if (prev.left==root) {
                    prev.left=root.right;
                    root.right.parent=prev;
                    return true;
                }
                else {
                    prev.right=root.right;
                    root.right.parent=prev;
                    return true;
                }
            } else if (root.right==null){
                if (prev.left==root) {
                    prev.left=root.left;
                    root.left.parent=prev;
                    return true;
                }
                else {
                    prev.right=root.left;
                    root.left.parent=prev;
                    return true;
                }
            } else {
                BSTree succ = root.getNext();
                swap(succ,root);
                // Delete(succ);
                if (succ.right==null){
                    if (succ.parent.left==succ){
                        succ.parent.left = null;
                        return true;
                    } else {
                        succ.parent.right = null;
                        return true;
                    }
                } else {
                    if (succ.parent.left==succ){
                        succ.parent.left = succ.right;
                        succ.right.parent = succ.parent;
                        return true;
                    } else {
                        succ.parent.right = succ.right;
                        succ.right.parent = succ.parent;
                        return true;
                    }
                }
            }
        }
    }   
        
    private BSTree FindHelper(int key, boolean exact, BSTree here) {
       BSTree curr = here;
        
        if (curr!=null){
            if (exact){
                while(curr!=null){
                    if(curr.key>key){
                        curr=curr.left;
                    } else if (curr.key<key) {
                        curr=curr.right;
                    } else {
                        BSTree leftFind = FindHelper(key, exact, curr.left);
                        // BSTree rightFind = curr.right.Find(key, exact);
                        if (leftFind==null) return curr;
                        else return leftFind; 
                    }
                }
                return null;
            } else {
                while (curr!=null){
                    if (curr.key<key){
                        curr=curr.right;
                    }
                    else {
                        BSTree leftFind = FindHelper(key, exact, curr.left);
                        if (leftFind!=null && (leftFind.key<curr.key || (leftFind.key==curr.key && leftFind.address<curr.address) )) return leftFind;
                        else return curr;
                    }
                }
                return null;
            }
        }
        return null;
    }


    public BSTree Find(int key, boolean exact){
        BSTree root = getRoot(this);
        return FindHelper(key, exact, root);
    }

    
    
    
    // The getFirst and getNext functions are for inorder traversal of the BST. 
    // The getFirst() returns the first (smallest) element of the BST subtree and null if the sbutree is empty
    // The getNext() returns the next element after in the inorder traversal of the BST
    // The Unlike the abstract Dictionary class, Tree class traverses the tree according to the inorder traversal, ordered by key
    
    // Remember this class implements dictionary using List Data Structure, so semantics of these functions should be preserved.
    // The dictionary class does not define any order in which the elements of the dictionary are to be traversed, but in the tree implementation, the traversal should be inorder
    // The additional requirement is that using the following loop, getFirst() and getNext() should be able to traverse all the elements in the dictionary (even in the presence of duplicate keys). 
    // count = 0; for (d = dict.getFirst(); d != null; d = d.getNext()) count = count + 1;
    // After the above loop, count should contain the total number of elements in the dictionary.

    public BSTree getFirst() //Check if we want first in the given subtree or the entire tree
    {   
        BSTree curr;
        // if (!isSentinel(this.parent)) {
        curr = getRoot(this);
        // } else {
            // curr = this;
        // }
        if (curr!=null) {
            while(curr.left!=null) curr=curr.left;
            return curr;
        }
        return null;
    }


    // private BSTree getSuccessor() //Specifically for delete function so that if there is some error, null is returned and not some other value from the tree
    // {   

    //     BSTree succ = this;
    //     BSTree curr = this.right;
    //     while(curr != null)
    //         {
    //         succ = curr;
    //         curr = curr.left;      
    //         }
    //     return succ;

    // }


    public BSTree getNext(){
        if (isSentinel(this)) return null;
        // Make changes to compare for both key and address
        // BSTree curr = this;
        // if (curr==null) return curr;
        BSTree root = getRoot(this);
        if (root==null) return root;
        else if (this.right != null) {
            BSTree node = this.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
        else {
            BSTree just = null;
            while (root != this) {
                if (root.key > this.key) {
                    just = root;
                    root = root.left;
                }
                else if(root.key<this.key){
                    root = root.right;
                } else if (root.address<this.address){
                    root = root.right;
                } else {
                    just = root;
                    root = root.left;
                }
            }
            return just;
        }        
    }



    // public BSTree getNext(){
    //     BSTree root = getRoot(this);
    //     if (root==null) return root;
    //     else if (this.right != null) {
    //         BSTree node = this.right;
    //         while (node.left != null) {
    //             node = node.left;
    //         }
    //         return node;
    //     } else {
    //         BSTree prev = this.parent;
    //         BSTree curr = this;
    //         while ((!isSentinel(prev.parent)) && (curr==prev.right)){
    //             curr = prev;
    //             prev = curr.parent;
    //         }
    //         return prev;
    //     }
    // }

    // private boolean sentinelSanity(BSTree x){
    //     BSTree sent = getRoot(x).parent;
    //     if (sent==null) return false;
    //     else if (sent.parent!=null || sent.left!=null) return false;
    //     return true;
    // }

    private boolean checkBSTHelper(BSTree root, int lastKey, int lastAdd) {
        if (root != null) {
            if (!checkBSTHelper(root.left, lastKey, lastAdd)) return false;
            if (root.key < lastKey) return false;
            if (root.key==lastKey && root.address < lastAdd) return false;
            lastKey = root.key;
            lastAdd = root.address;
            return checkBSTHelper(root.right, lastKey, lastAdd);
        } return true;
    }

    private boolean eachNodeSanity(BSTree root){
        if (root==null) return true;
        if (root.left==null && root.right==null) return true;
        if (root.left==null) {
            if (root.right.parent!=root) return false;
            return eachNodeSanity(root.right);
        }
        if (root.right==null) {
            if (root.left.parent!=root) return false;
            return eachNodeSanity(root.left);
        }
        if (root.left!=null && root.right!=null) {
            if (root.right.parent!=root) return false;
            if (root.left.parent!=root) return false;
            return (eachNodeSanity(root.left) && eachNodeSanity(root.right));
        }
        return true;
    }

    public boolean sanity()
    // Checks the sanity of the BST subtree and returns true if sane, false otherwise

    { 
        return (checkBSTHelper(getRoot(this), Integer.MIN_VALUE, Integer.MIN_VALUE) && eachNodeSanity(getRoot(this)));
    }

}


 



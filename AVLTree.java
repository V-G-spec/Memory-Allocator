// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    private Boolean isSentinel(AVLTree T){
        if (T==null) return false;
        else return (T.parent==null && T.left==null);
    }


    private void printTree(AVLTree root) {
        printHelper(root, "", true);
    }

    private void printHelper(AVLTree root, String indent, boolean last) {
        if (isSentinel(root)==false && root!=null) {
          System.out.print(indent);
          if (last) {
            System.out.print("R----");
            indent += "   ";
          } else {
            System.out.print("L----");
            indent += "|  ";
          }
    
          int h = root.height;
          int sum = root.address+root.size;
          System.out.println("("+ root.address + " -> " + sum + ")" + "  (" + h + ")");
          printHelper(root.left, indent, false);
          printHelper(root.right, indent, true);
        }
    }


    private AVLTree getSentinel(AVLTree root){
        while(root.parent!=null){
            root=root.parent;
        }
        return root;
    }

    private AVLTree getRoot(AVLTree node){
        if (isSentinel(node)) return node.right;
        else {
            while(!isSentinel(node.parent)){
            node=node.parent;
            }
        }
        return node;
    }


    // private void swap(AVLTree T1, AVLTree T2){
    //     int temp = T1.key;
    //     T1.key= T2.key;
    //     T2.key = temp;
    //     int temp2 = T1.address;
    //     T1.address = T2.address;
    //     T2.address = temp2;
    //     int temp3 = T1.size;
    //     T1.size= T2.size;
    //     T2.size = temp3;
    //     int temp4 = T1.height;
    //     T1.height= T2.height;
    //     T2.height = temp4;
    // }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    


    private boolean checkHeight(AVLTree N){ //Checks if tree (The node sent as parameter) is balanced
        // Making changes at 12:37 pm, 29 Nov
        // If something doesn't work, check this
        int a,b;
        if (N==null) return true;
        if (N.right==null && N.left==null) {
            a=-1;
            b=-1;
        }
        else if (N.right==null){
            a=N.left.height;
            b=-1;
        }
        else if (N.left==null){
            a=N.right.height;
            b=-1;
        }
        else {
            a=N.right.height;
            b=N.left.height;
        }

        return (Math.abs(a-b)<=1);
    }




    
    private void assignHeight(AVLTree N){
        // Making changes at 12:39 pm, 29 Nov
        // If something doesn't work, check this
        int a,b;
        if (N.right==null && N.left==null){
            // a=0;
            // b=0;
            N.height = 0;
        }
        else if (N.right==null){
            a=N.left.height;
            b=-1;
            N.height = Math.max(a,b) +1;
        }
        else if (N.left==null){
            a=N.right.height;
            b=-1;
            N.height = Math.max(a,b) +1;
        }
        else {
            a=N.right.height;
            b=N.left.height;
            N.height = Math.max(a,b) +1;
        }
    }


    private int heightImbalance(AVLTree N){ // Returns height(right) - height(left)
        if (N.right==null && N.left==null) return 0;   // Changed 1 to 0, might cause errors
        else if (N.right == null) return (-1-N.left.height);
        else if (N.left == null) return (N.right.height+1);
        else return (N.right.height - N.left.height);
    }




    private AVLTree rightRotation(AVLTree z){

        AVLTree y = z.left;
        AVLTree x = y.right;
        y.right = z;
        if (z!=null) z.parent = y;
        z.left = x;
        if (x!=null) x.parent =z;
        assignHeight(z);
        assignHeight(y);
        return y;

    }

    private AVLTree LeftRotation(AVLTree z){

        AVLTree y = z.right;
        AVLTree x = y.left;
        y.left = z;
        if (z!=null) z.parent = y;
        z.right = x;
        if (x!=null) x.parent = z;
        assignHeight(z);
        assignHeight(y);
        return y;

    }







    private AVLTree InsertHelper(AVLTree root, int address, int size, int key) 
    {
        if (root == null){
        AVLTree nChild =  new AVLTree(address, size, key);
        nChild.parent = root;
        return nChild;
        }
        if (isSentinel(root)){
            AVLTree rChild = InsertHelper(root.right, address, size, key);
            root.right = rChild;
            rChild.parent = root;
        }
        else if (key<root.key){
            AVLTree lchild = InsertHelper(root.left, address, size, key);
            root.left = lchild;
            lchild.parent = root;
        } 
        else if (key>root.key){
            AVLTree rChild = InsertHelper(root.right, address, size, key);
            root.right = rChild;
            rChild.parent = root;
        }
        else if (address<root.address){
            AVLTree lChild = InsertHelper(root.left, address, size, key);
            root.left = lChild;
            lChild.parent = root;
        }
        else if (address>root.address){
            AVLTree rChild = InsertHelper(root.right, address, size, key);
            root.right = rChild;
            rChild.parent = root;
        }
        
        int h;
        if (isSentinel(root)) return root; 
        else h=  heightImbalance(root);
        if (h>1){
            if (heightImbalance(root.right)<0){
                root.right = rightRotation(root.right);
            }
            root = LeftRotation(root);
        }
        else if (h<-1){
            if (heightImbalance(root.left)>0){
                root.left = LeftRotation(root.left);
            }
            root=rightRotation(root);
        }


    
        assignHeight(root);
        return root;
    }
    
    private AVLTree FindforInsert(int address, int size, int key){
        AVLTree curr = this;
        while(curr!=null && (curr.key!=key || curr.address!=address)){
            if (curr.key>key) curr = curr.left;
            else if (curr.key<key) curr = curr.right;
            else if (curr.address>address) curr = curr.left;
            else if (curr.address<address) curr = curr.right;
        }
        return curr;
    }


    public AVLTree Insert(int address, int size, int key){
        // System.out.println("Insert begins");
        InsertHelper(getSentinel(this), address, size, key);
        // printTree(getRoot(this));
        // System.out.println("Insert ends");
        return this.FindforInsert(address, size, key);
    }




    private AVLTree DeleteHelper(AVLTree root, Dictionary e){
        if (root==null) return null;
        if (root.key<e.key){
            AVLTree rChild = DeleteHelper(root.right, e);
            root.right = rChild;
            if (rChild!=null) rChild.parent = root;
        } 
        else if (root.key>e.key){
            AVLTree lChild = DeleteHelper(root.left, e);
            root.left = lChild;
            if (lChild!=null) lChild.parent = root;
        } 
        else if (root.address<e.address){
            AVLTree rChild = DeleteHelper(root.right, e);
            root.right = rChild;
            if (rChild!=null) rChild.parent = root;
        } 
        else if (root.address>e.address){
            AVLTree lChild = DeleteHelper(root.left, e);
            root.left = lChild;
            if (lChild!=null) lChild.parent = root;
        }
        else if(root.left!=null && root.right!=null){
            AVLTree succ = root.getNext();
            root.size = succ.size;
            root.key = succ.key;
            root.address = succ.address;
            AVLTree rChild = DeleteHelper(root.right, succ);
            root.right = rChild;
            if (rChild!=null) rChild.parent = root;
        }
        else root = (root.left!=null) ? root.left : root.right;
        
        int h;
        if (isSentinel(root) || root==null) return root; 
        else h=  heightImbalance(root);
        if (h>1){
            if (heightImbalance(root.right)<0){
                root.right = rightRotation(root.right);
            }
            root = LeftRotation(root);
        }
        else if (h<-1){
            if (heightImbalance(root.left)>0){
                root.left = LeftRotation(root.left);
            }
            root=rightRotation(root);
        }
        assignHeight(root);
        return root;
    }

    public boolean Delete(Dictionary e)
    {
        // System.out.println("Delete begins");
        AVLTree X = DeleteHelper(getSentinel(this), e);
        // printTree(getRoot(this));
        // System.out.println("Delete ends");
        if (X!=null) return true;
        return false;
    }
    
    




    private AVLTree FindHelper(int key, boolean exact, AVLTree here) {
        AVLTree curr = here;
         
        if (curr!=null){
            if (exact){
                while(curr!=null){
                    if(curr.key>key){
                        curr=curr.left;
                    } else if (curr.key<key) {
                        curr=curr.right;
                    } else {
                    AVLTree leftFind = FindHelper(key, exact, curr.left);
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
                        AVLTree leftFind = FindHelper(key, exact, curr.left);
                         if (leftFind!=null && (leftFind.key<curr.key || (leftFind.key==curr.key && leftFind.address<curr.address) )) return leftFind;
                         else return curr;
                    }
                }
               return null;
            }
        }
        return null;
    }
    
 
    public AVLTree Find(int key, boolean exact){
        AVLTree root = getRoot(this);
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
 
    public AVLTree getFirst() //Check if we want first in the given subtree or the entire tree
    {   
    AVLTree curr;
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

    public AVLTree getNext(){
        if (isSentinel(this)) return null;
        // Make changes to compare for both key and address
        // BSTree curr = this;
        // if (curr==null) return curr;
        AVLTree root = getRoot(this);
        if (root==null) return root;
        else if (this.right != null) {
        AVLTree node = this.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
        else {
        AVLTree just = null;
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


    

    private boolean checkBSTHelper(AVLTree root, int lastKey, int lastAdd) {
        if (root != null) {
            if (!checkBSTHelper(root.left, lastKey, lastAdd)) return false;
            if (root.key < lastKey) return false;
            if (root.key==lastKey && root.address < lastAdd) return false;
            lastKey = root.key;
            lastAdd = root.address;
            return checkBSTHelper(root.right, lastKey, lastAdd);
        } return true;
    }

    private boolean eachNodeSanity(AVLTree root){
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

    private boolean checkAllHeight(AVLTree root){
        if (root!=null){
            return (checkHeight(root) && checkAllHeight(root.right) && checkAllHeight(root.left));
        } else return true;
    }

    public boolean sanity()
    // Checks the sanity of the BST subtree and returns true if sane, false otherwise

    { 
        return (checkBSTHelper(getRoot(this), Integer.MIN_VALUE, Integer.MIN_VALUE) && eachNodeSanity(getRoot(this)) && checkAllHeight(getRoot(this)) );
    }
}

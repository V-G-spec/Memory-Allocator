// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    
    public int Allocate(int blockSize) {        //Corner cases remaining
        if (blockSize<=0) return -1;
        else {
            Dictionary here = freeBlk.Find(blockSize, false);
            if (here!=null){
                if (here.key>blockSize){ // i.e. splitting is needed

                    freeBlk.Insert(here.address+blockSize, here.size-blockSize, here.size-blockSize);
                    Dictionary temp2 = freeBlk.Insert(here.address, blockSize, blockSize);
                    freeBlk.Delete(here);
                    here=temp2;
                } 

                allocBlk.Insert(here.address, here.size, here.address);

                // Dictionary temp = allocBlk.Insert(here.address, here.size, here.address);
                int temp = here.address;
                freeBlk.Delete(here);
                // return temp.address;
                return temp;
            }
        }
       // This function should allocate a contiguous array of size blockSize and return the first address. 
       // It should return -1 if memory is not avaiable. 
       // The free blocks list and the allocated blocks list should be suitably modified
       // Algorithm: 
       //     1. Search in the free block dictionary to find a block of size >= blockSize using the find function
       //     2. If found, check if splitting of the block is needed. 
       //     3. If yes, split the block and insert the two blocks into the free and allocated blocks dictionary
       //     3.1. Delete the block from the free block dictionary
       //     4 If no, insert the block into allocated blocks dictionary and remove it from free blocks dictionary
       
        return -1;
    }



    // This function defragments the free block list (dictionary)
       // All the contiguous free blocks are merged into a single large free block
       // Algorithm:
       //     1. Create a new BST/AVT Tree indexed by address. Use AVL/BST depending on the type.
       //     2. Traverse all the free blocks and add them to the tree indexed by address 
       //        Note that the free blocks tree will be indexed by size, therefore a new tree indexed by address needs to be created
       //     3. Find the first block in the new tree (indexed by address) and then find the next block
       //     4. If the two blocks are contiguous, then 
       //     4.1 Merge them into a single block
       //     4.2 Remove the free blocks from the free list and the new dictionary
       //     4.3 Add the merged block into the free list and the new dictionary
       //     5. Continue traversing the new dictionary
       //     6. Once the traversal is complete, delete the new dictionary
       
        //    Dictionary iter = freeBlk.getFirst();

    public void Defragment() {
        
        if (this.type==3){

            AVLTree temp = new AVLTree (); //Indexed by address
            for(Dictionary itr = freeBlk.getFirst(); itr != null; itr = itr.getNext()){
                temp.Insert(itr.address, itr.size, itr.address);        
            }       
            
            Dictionary next;
            Dictionary iter = temp.getFirst();
            while(iter!=null){
                next = iter.getNext();
                // if (next==null) break;
                if (next!=null && next.address==iter.address+iter.size) {

                    Dictionary iterDel = new AVLTree(iter.address, iter.size, iter.size);
                    Dictionary nextDel = new AVLTree(next.address, next.size, next.size);
                    
                    freeBlk.Delete(nextDel);
                    freeBlk.Delete(iterDel);
                    freeBlk.Insert(iter.address, iter.size+next.size, iter.size+next.size);
                    
                    temp.Delete(next);
                    temp.Delete(iter);
                    iter = temp.Insert(iterDel.address, iterDel.size+nextDel.size, iterDel.address);
                    
                    // iter = temp.getFirst();
                } else iter = iter.getNext();
            }
            temp=null;
        }
        if (this.type==2){

            BSTree temp = new BSTree (); //Indexed by address
            for(Dictionary itr = freeBlk.getFirst(); itr != null; itr = itr.getNext()){
                temp.Insert(itr.address, itr.size, itr.address);        
            }       
            
            Dictionary next;
            Dictionary iter = temp.getFirst();
            while(iter!=null){
                next = iter.getNext();
                // if (next==null) break;
                if (next!=null && next.address==iter.address+iter.size) {

                    Dictionary iterDel = new BSTree(iter.address, iter.size, iter.size);
                    Dictionary nextDel = new BSTree(next.address, next.size, next.size);
                    
                    freeBlk.Delete(nextDel);
                    freeBlk.Delete(iterDel);
                    freeBlk.Insert(iter.address, iter.size+next.size, iter.size+next.size);
                    
                    temp.Delete(next);
                    temp.Delete(iter);
                    iter = temp.Insert(iterDel.address, iterDel.size+nextDel.size, iterDel.address);
                    
                    // iter = temp.getFirst();
                } else iter = iter.getNext();
            }
            temp=null;
        }
    }
}
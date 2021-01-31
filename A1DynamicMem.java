// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).




//  for freeBlk, Key = size
//  For allocblk, Key = start address
//  Because if you want to find a block of free memory, you'll obviously need to look for size


public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {

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

        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

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

                Dictionary temp = allocBlk.Insert(here.address, here.size, here.address);
                
                freeBlk.Delete(here);
                return temp.address;
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
    
    public int Free(int startAddr) {
       // This function should free the memory block starting at the startAddr
       // It should return -1 in case block not found
       // Algorithm: 
       //    1. Add the block to free blocks list (dictionary)
       //    2. Delete the bock from the allocated blocks list (dictionary)
        Dictionary here = allocBlk.Find(startAddr, true);
        if (here!=null){
            freeBlk.Insert(here.address, here.size, here.size);
            allocBlk.Delete(here);
            return 0;
        }
        return -1;
    }
}
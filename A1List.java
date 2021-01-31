// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    private boolean isTailSentinel(A1List s){
        return (s.address==-1 && s.size==-1 && s.key==-1 && s.next==null);
    }

    private boolean isHeadSentinel(A1List s){
        return (s.address==-1 && s.size==-1 && s.key==-1 && s.prev==null);
    }

    private A1List getTailSentinel(A1List s){
        A1List curr= s;
        while(isTailSentinel(curr)==false){
            curr=curr.next;
        }
        return curr;
    }
    
    private A1List getHeadSentinel(A1List s){
        A1List curr= s;
        while(isHeadSentinel(curr)==false){
            curr=curr.prev;
        }
        return curr;
    }





                    ///////////// ASSIGNMENT STARTS HERE /////////////
    
    
    public A1List Insert(int address, int size, int key) //throws IllegalArgumentException 
    {
        A1List toIns = new A1List(address, size, key);
        if (isTailSentinel(this)){
            return null;
            //throw new IllegalArgumentException(); 
        } else
        toIns.next=this.next;
        this.next.prev=toIns;
        this.next=toIns;
        toIns.prev=this;
        return toIns;
    }

    
    public boolean Delete(Dictionary d) //throws IllegalArgumentException 
    {   if (d!=null){
            A1List curr= this.getFirst();
            while(curr.next!=null){
                if (curr.key==d.key) {
                    if (curr.address==d.address) {
                        if (curr.size == d.size) {
                            if (isHeadSentinel(curr) || isTailSentinel(curr)) return false; //throw new IllegalArgumentException()
                            curr.prev.next=curr.next;
                            curr.next.prev=curr.prev;
                            return true;
                            // break;
                        }
                    }
                } else {
                    curr = curr.next;
                }
            }
            // if (curr==null){
            //     A1List curr2=this;
            //     while(curr2.prev!=null){
            //         if (curr2.key==d.key) {
            //             if (curr2.address==d.address) {
            //                 if (curr2.size == d.size) {
            //                     if (isHeadSentinel(curr2) || isTailSentinel(curr2)) return false; //throw new IllegalArgumentException()
            //                     curr2.prev.next=curr2.next;
            //                     curr2.next.prev=curr2.prev;
            //                     break;
            //                 }
            //             }
            //         } else {
            //             curr2 = curr2.prev;
            //         }
            //     }
            // }
        }
        return false;
    }

    public A1List Find(int k, boolean exact) //TBC
    {   A1List curr= this.getFirst();
        if (curr!=null){
            if (exact){
                while(curr.next!=null){
                    if (curr.key==k) return curr;
                    else curr=curr.next;
                }

            } else {
                while(curr.next!=null){
                    if (curr.key>=k) return curr;
                    else curr=curr.next;
                }
            }
        }
        return null;
    }

    public A1List getFirst() 
    {   A1List curr= this;
        while(curr.prev!=null){
            curr=curr.prev;
        }
        if (isTailSentinel(curr.next)) return null;
        return curr.next;
    }
    
    public A1List getNext() //throws IllegalArgumentException
    {   
        if (isTailSentinel(this)) return null; // throw new IllegalArgumentException(); 
        else if (isTailSentinel(this.next)) return null;
        else return this.next;
    }

                    ///////// SUPPORTING FUNCTIONS FOR SANITY ///////////////

    private boolean cycleDetectionForward(A1List s){ //Using Floyd's cycle detection algorithm which I learnt while practicing for the minor exam. I gave its proof on a Piazza question as well, long before attempting this assignment
        // This function traverses forwards to see if there is a loop by using the Floyd's algorithm
        // An explanation that I gave on Piazza post as to why this works was that first we see from a general
        // Frame of reference till both the hare and tortoise are in the cycle
        // Then we shift the frame of reference to the tortoise and now, relatively, hare is moving
        // 1 step at a time. Now since tortoise is fixed and hare moves 1 step at a time in a cycle
        // They are bound to meet. If not, they hare will reach null and it won't be a list
        A1List hare = s;
        A1List tortoise = s;
        if (tortoise == null || tortoise.next == null) {
            return false;
        }
        while (hare != null && hare.next != null) {
            tortoise = tortoise.next;
            hare = hare.next.next;
            if (hare == tortoise) {
                return true;
            }
        }
        return false;
    }

    private boolean cycleDetectionBackward(A1List s){ //Using Floyd's cycle detection algorithm which I learnt while practicing for the minor exam. I gave its proof on a Piazza question as well, long before attempting this assignment
        // This function traverses backwards to see if there is a loop by using the Floyd's algorithm
        // An explanation that I gave on Piazza post as to why this works was that first we see from a general
        // Frame of reference till both the hare and tortoise are in the cycle
        // Then we shift the frame of reference to the tortoise and now, relatively, hare is moving
        // 1 step at a time. Now since tortoise is fixed and hare moves 1 step at a time in a cycle
        // They are bound to meet. If not, they hare will reach null and it won't be a list
        A1List hare = s;
        A1List tortoise = s;
        if (tortoise == null || tortoise.prev == null) {
            return false;
        }
        while (hare != null && hare.prev != null) {
            tortoise = tortoise.prev;
            hare = hare.prev.prev;
            if (hare == tortoise) {
                return true;
            }
        }
        return false;
    }

    private boolean headTailSanity(A1List s){
        // Makes sure that there is no node previous to head sentinel nodes
        // And no node next to the tail sentinel nodes following their property
        if (getHeadSentinel(s).prev!=null || getTailSentinel(s).next!=null) return false;
        return true;
    }

    private boolean eachNodeSanity(A1List s){
        // for each node we make sure that the next node's previous node is the current node
        A1List curr;
        for (curr= s.getFirst(); curr!=null ; curr=curr.getNext()){
            if (curr.next.prev!=curr) return false;
            if (curr.prev.next!=curr) return false;
        }
        return true;

    }



    public boolean sanity() //TBC
    {   
        return (!cycleDetectionForward(this) && !cycleDetectionBackward(this) && headTailSanity(this) && eachNodeSanity(this));
    }

}



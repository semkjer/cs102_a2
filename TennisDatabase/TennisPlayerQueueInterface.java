


// Giuseppe Turini
// CS-102, Spring 2020
// Assignment 2

package TennisDatabase;

// Interface (package-private) providing the specifications for the TennisPlayerQueue class.
interface TennisPlayerQueueInterface {
   
   // Desc.: Check if the queue is empty.
   // Output: True or false.
   public boolean isEmpty();
   
   // Desc.: Insert a tennis player at the back of this queue.
   // Input: A tennis player.
   // Output: Throws a checked (critical) exception if the insertion fails.
   public void enqueue( TennisPlayer p ) throws TennisDatabaseException;
   
   // Desc.: Extract (return and remove) a tennis player from the front of this queue.
   // Output: Throws a checked (critical) exception if the extraction fails.
   public TennisPlayer dequeue() throws TennisDatabaseException;
   
   // Desc.: Return (without removing) the tennis player at the front of this queue.
   // Output: Throws a checked (critical) exception if the queue is empty.
   public TennisPlayer peek() throws TennisDatabaseException;
      
}



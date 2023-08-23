


// Giuseppe Turini
// CS-102, Spring 2020
// Assignment 2

package TennisDatabase;

// Interface (package-private) providing the specifications for the TennisPlayerNode class.
interface TennisPlayerContainerNodeInterface {
   
   // Accessors (getters).
   public TennisPlayer getPlayer();
   public SortedLinkedList<TennisMatch> getMatchList();
   public TennisPlayerContainerNode getLeftChild();
   public TennisPlayerContainerNode getRightChild();
   
   // Modifiers (setters).
   public void setPlayer( TennisPlayer p );
   public void setMatchList( SortedLinkedList<TennisMatch> ml );
   public void setLeftChild( TennisPlayerContainerNode lc );
   public void setRightChild( TennisPlayerContainerNode rc );
   
   // Desc.: Insert a TennisMatch object (reference) into this node.
   // Input: A TennisMatch object (reference).
   // Output: Throws a checked (critical) exception if match cannot be inserted in this player list.
   public void insertMatch( TennisMatch m ) throws TennisDatabaseException;
   
   // Desc.: Returns all matches of this player arranged in the output array (sorted by date, most recent first).
   // Output: Throws an unchecked (non-critical) exception if there are no matches for this player.
   public TennisMatch[] getMatches() throws TennisDatabaseRuntimeException;
   
   // Desc.: Deletes all matches of input player (id) from this node.
   // Input: The id of the tennis player.
   // Output: Throws an unchecked (non-critical) exception if no matches are deleted.
   public void deleteMatchesOfPlayer( String playerId ) throws TennisDatabaseRuntimeException;
   
}



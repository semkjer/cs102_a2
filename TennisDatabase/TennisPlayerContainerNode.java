package TennisDatabase;

class TennisPlayerContainerNode implements TennisPlayerContainerNodeInterface {
    TennisPlayerContainerNode rightChild;
    TennisPlayerContainerNode leftChild;
    TennisPlayer item;
    SortedLinkedList<TennisMatch> matches;
    
    //Basic constructor
    public TennisPlayerContainerNode( TennisPlayer inPlayer ) {
        this.item = inPlayer;
        this.rightChild = null;
        this.leftChild = null;
        this.matches = new SortedLinkedList<TennisMatch>();
    }

    // Accessors (getters).
    public TennisPlayer getPlayer() { return this.item; }
    public TennisPlayerContainerNode getLeftChild() { return this.leftChild; }
    public TennisPlayerContainerNode getRightChild() { return this.rightChild; }
    public SortedLinkedList<TennisMatch> getMatchList() { return this.matches;}


    // Modifiers (setters).
    public void setLeftChild( TennisPlayerContainerNode l ) { this.leftChild = l; }
    public void setRightChild( TennisPlayerContainerNode r ) { this.rightChild = r; }
    public void setPlayer ( TennisPlayer p ) { this.item = p; }
    public void setMatchList ( SortedLinkedList<TennisMatch> m ) { this.matches = m; }

    // Desc.: Insert a TennisMatch object (reference) into this node.
    // Input: A TennisMatch object (reference).
    // Output: Throws an exception if match cannot be inserted in this list.
    public void insertMatch( TennisMatch m ) throws TennisDatabaseException {
      boolean duplicate = false;
      for( int i = 0; i < this.matches.size(); i++){
         if( this.matches.get(i).equals(m) ) { duplicate = true; } //catches any duplicate matches
      }
      if( !duplicate ) {
         try{ this.matches.insert(m); }
         catch( Exception e ){ throw new TennisDatabaseException("Match insertion failed within matches field"); }
      }
    } 

    // Desc.: Returns copies (deep copies) of all matches of this player arranged in the output array (sorted by date, most recent first). Sorting occurs in this method.
    // Output: Throws an exception if there are no matches in this list.
    public TennisMatch[] getMatches() throws TennisDatabaseRuntimeException { 
      TennisMatch [] out = new TennisMatch[this.matches.size()];//this loop stops early
      //for loop to insert matches into an output sorted by date
      for( int i = 0; i < this.matches.size(); i++) { 
         TennisMatch nextInsert = this.matches.get(i); 
         insertToSorted( out, nextInsert );
      }
      return out;
    } 
    
    
    // Desc.: Insert a TennisMatch object (reference) into an array
    // Input: A TennisMatch object (reference).
    private static void insertToSorted( TennisMatch [] toSort, TennisMatch m ) {
       insertToSortedRec( toSort, toSort.length-1, m );
    }
    
    //Recursive implementation
    private static TennisMatch insertToSortedRec( TennisMatch [] toSort, int currIndex, TennisMatch m ) {
       //special case if array is empty or error was fed into the method
       if(toSort == null || m == null) {}
       else if( toSort.length == 1 ) { 
          toSort[0] = m; 
          return null;
       }
       else if( currIndex < 0 ) { return m; } //if 1 start of array is reached
       else if( toSort[currIndex] == null) { 
          toSort[currIndex] = insertToSortedRec(toSort, currIndex-1, m); 
          return null;
       } //iterate through all null indexes
       else if( m.compareTo(toSort[currIndex]) >= 0  ) {
          TennisMatch smaller = toSort[currIndex];
          toSort[currIndex] = insertToSortedRec(toSort, currIndex-1, m); 
          return smaller;
       } //if array requires a frame shift then take the value from the recursion and assign it, then return what used to be at this position
       else if( m.compareTo(toSort[currIndex]) < 0 ) { return m; } //if past the insertion point 
       return null; 
    }  
    
    // Desc.: Deletes all matches of input player (id) from this node.
    // Input: The id of the tennis player.
    // Output: Throws an unchecked (non-critical) exception if no matches are deleted.
    public void deleteMatchesOfPlayer( String playerId ) throws TennisDatabaseRuntimeException {}
}
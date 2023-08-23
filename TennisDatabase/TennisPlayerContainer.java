/*
Brandon Emkjer
Professor Turini
CS-102
Due 6.12.20
*/

package TennisDatabase;

class TennisPlayerContainer implements TennisPlayerContainerInterface {
    private TennisPlayerContainerNode root;
    private final int maxDepth = 15;
    private int size;
    
    //Basic constructor
    public TennisPlayerContainer() {
        this.root = null;
        this.size = 0;
    }
    
    
    // Desc.: Returns an iterator object ready to be used to iterate this container.
    // Output: The iterator object configured for this container.
    public TennisPlayerContainerIterator iterator(){ return new TennisPlayerContainerIterator( this.root ); }
    
    
    // Desc.: Returns the number of players in this container.
    // Output: The number of players in this container as an integer.
    public int getNumPlayers() { return this.size; }
    
    // Desc.: method to find the location of the current node
    private TennisPlayerContainerNode locateNodeRec( TennisPlayerContainerNode currRoot, String playerId ) {
       if( currRoot == null ) { return null; }
       else if( currRoot.item.getId().equals( playerId ) ) { return currRoot; } //found node, pass back up the tree
       else if( currRoot.item.getId().compareTo( playerId ) > 0 ) { return locateNodeRec( currRoot.leftChild, playerId ); }//curr larger than id, traverse left subtree
       else { return locateNodeRec( currRoot.rightChild, playerId ); }//else curr smaller than id, traverse right subtree
    }
        
    // Desc.: Search for a player in this container by id, and delete it with all his matches (if found).
    // Output: Throws an unchecked (non-critical) exception if there is no player with that input id.
    public void deletePlayer( String playerId ) throws TennisDatabaseRuntimeException
    {
       //Call to recursive implementation
       this.root = deletePlayerRec(this.root, playerId);
       this.size--;
    }
   
    //Internal recursive implementation of deletePlayer
    //Input: a BST and the player's ID
    //Output: the reference to the root node of the input BST, after the removal
    private TennisPlayerContainerNode deletePlayerRec(TennisPlayerContainerNode currRoot, String playerId) {
       //Check if input BST is empty
       if( currRoot == null ) { 
          //Input BST is empty, throw exception
          throw new TennisDatabaseRuntimeException("ERROR: delete player failed, id not found"); 
       }
       else {
          //Input BST NOT empty, search for the node to delete
          //Compare id against id stored in currRoot
          int res = playerId.compareTo(currRoot.getPlayer().getId());
          //Check comparison result
          if(res == 0) { // Item is in the root.
             //Input id is equal to id stored in currRoot, currRoot is the node to be deleted 
             return deletePlayerNode(currRoot); //Call to perform an actual removal of the node
          }
          else if(res < 0) { 
             // Input id is less than id in currRoot. Search left tree.
             TennisPlayerContainerNode newLeftChild = deletePlayerRec(currRoot.getLeftChild(), playerId);
             currRoot.setLeftChild(newLeftChild);
             return currRoot;
          }
          else {
             // Input id is more than id in currRoot. Search right tree.
             TennisPlayerContainerNode newRightChild = deletePlayerRec(currRoot.getRightChild(), playerId);
             currRoot.setRightChild(newRightChild);
             return currRoot;
          } 
       }
    }
   
   //Internal recursive implementation of the removal of a node in the BST
   private TennisPlayerContainerNode deletePlayerNode(TennisPlayerContainerNode nodeToBeDeleted) {
      // 4 cases to consider: 
      // input node is a leaf (1);
      // input node has no left child (2);
      // input node has no right child (3);
      // input node has 2 children (4).
      //Check if input node is case 1
      if((nodeToBeDeleted.getLeftChild() == null) && (nodeToBeDeleted.getRightChild() == null)) {
         return null;
      }
      //Check if input node is case 2
      else if(nodeToBeDeleted.getLeftChild() == null) {
         //Input node only right child, remove input node by returning its right child
         return nodeToBeDeleted.getRightChild();
      }
      //Check if input node is case 3
      else if(nodeToBeDeleted.getRightChild() == null) {
         //Input node only left child, remove input node by returning its left child
         return nodeToBeDeleted.getLeftChild();
      }
      //Check if input node is case 4
      else {
         //Input node has 2 children: retrieve inorder successor, swap, and delete 'inorder successor'
         //A. Find inorder successor as the leftmost node in the right subtree of NodeToBeDeleted
         TennisPlayerContainerNode inorderSuccessor = findLeftmostNode(nodeToBeDeleted.getRightChild());
         //B. (swap) overwrite inorder successor content into NodeToBeDeleted
         nodeToBeDeleted.setPlayer(inorderSuccessor.getPlayer());
         nodeToBeDeleted.setMatchList(inorderSuccessor.getMatchList());
         //C. delete the node at inorder successor position
         TennisPlayerContainerNode newRightChild = deleteLeftmostNode(nodeToBeDeleted.getRightChild());
         nodeToBeDeleted.setRightChild(newRightChild);
         return nodeToBeDeleted;
      }
   }
   
   // Desc.: Method called to find the leftmost node in the BST
   private TennisPlayerContainerNode findLeftmostNode(TennisPlayerContainerNode currRoot) {
      if(currRoot.getLeftChild() == null) {
         return currRoot;
      }
      else {
         return findLeftmostNode(currRoot.getLeftChild());
      }
   }
   
   // Desc.: Method to delete the leftmost node in the BST
   private TennisPlayerContainerNode deleteLeftmostNode(TennisPlayerContainerNode currRoot) {
      if(currRoot.getLeftChild() == null) {
         return currRoot.getRightChild();
      }
      else {
         currRoot.setLeftChild(deleteLeftmostNode(currRoot.getLeftChild()));
         return currRoot; 
      }
   }
   
    // Desc.: Search for a player in this container by input id, and returns a copy of that player (if found).
    // Output: Throws an unchecked (non-critical) exception if there is no player with that input id.
    public TennisPlayer getPlayer( String playerId ) throws TennisDatabaseRuntimeException { 
       return getPlayerRec( this.root, playerId ).item;
    }//Support function for getPlayer
    private TennisPlayerContainerNode getPlayerRec( TennisPlayerContainerNode currRoot, String playerId ) {
       if( currRoot == null ) { return null; }
       else if( currRoot.item.getId().equals( playerId ) ) { return currRoot; }
       else if( currRoot.item.getId().compareTo( playerId ) > 0 ) { return getPlayerRec( currRoot.leftChild, playerId ); }
       else { return getPlayerRec( currRoot.rightChild, playerId ); }
    }


    // Desc.: Insert a tennis player into this container.
    // Input: A tennis player.
    // Output: Throws a checked (critical) exception if player id is already in this container.
    //         Throws a checked (critical) exception if the container is full. 
    public void insertPlayer( TennisPlayer p ) throws TennisDatabaseException {
        SimpleDate inDate = new SimpleDate( p.getBirthYear() );
        TennisPlayer insertPlayer = new TennisPlayer( p.getId(), p.getFirstName(), p.getLastName(), inDate, p.getCountry() ); //make some clones
        TennisPlayerContainerNode inNode = new TennisPlayerContainerNode( insertPlayer ); // build playernode
        try{ 
           this.root = insertPlayerRec( this.root, inNode, 0 ); 
           this.size++;
        }
        catch(TennisDatabaseException e) { throw e; }
    }// Desc.: Support function for insertPlayer
    private TennisPlayerContainerNode insertPlayerRec( TennisPlayerContainerNode currRoot, TennisPlayerContainerNode inNode, int currentDepth ) throws TennisDatabaseException {
        if(currRoot == null){ return inNode; } //if tree is empty then this is the first insertion, or the bottom where we need to insert
        else if( currentDepth > this.maxDepth ) { throw new TennisDatabaseException("Maximum depth of player storage has been reached"); }//max dept
        else if( currRoot.item.getId().equals(inNode.item.getId()) ) { throw new TennisDatabaseException("Insertion Failed on duplicate ID."); } //curr root can never equal the insertion
        else if( currRoot.item.compareTo( inNode.item ) > 0 ) { 
           currRoot.leftChild = insertPlayerRec( currRoot.leftChild, inNode, ++currentDepth ); 
           return currRoot;
        } //currRoot greater than insertion then traverse left
        else{ 
           currRoot.rightChild = insertPlayerRec( currRoot.rightChild, inNode, ++currentDepth );
           return currRoot;
         }//else currRoot less than insertion then traverse right
    }
    
    

    // Desc.: Insert a tennis match into the lists of both tennis players of the input match.
    // Input: A tennis match.
    // Output: Throws a checked (critical) exception if the insertion is not fully successful.
    public void insertMatch( TennisMatch m ) throws TennisDatabaseException {
        if (this.root == null) { throw new TennisDatabaseException("There are no players in the container!\n"); } //case no players
        TennisPlayerContainerIterator iter = new TennisPlayerContainerIterator( this.root );
        iter.setInorder();
        TennisPlayerContainerNode curr; 
        boolean id1Found = false;
        boolean id2Found = false;
        while( iter.hasNext() ) { //loop through and find if either player is at index
            curr = iter.next();
            if( curr.getPlayer().getId().equalsIgnoreCase( m.getIdPlayer1() ) ) {
               id1Found = true;
               try{ curr.insertMatch( m ); }//attempt insertion if found and ignore failure because given code is assumed bulletproof
               catch( Exception e ) { }              
            }
            if( curr.getPlayer().getId().equalsIgnoreCase( m.getIdPlayer2() ) ) {
               id2Found = true; 
               try { curr.insertMatch( m ); }//attempt insertion if found and ignore failure because given code is assumed bulletproof
               catch( Exception e ) { }                            
            }
        }
    }
    

    // Desc.: Returns copies (deep copies) of all players in the database arranged in the output array (sorted by id, alphabetically).
    // Output: Throws an unchecked (non-critical) exception if there are no players in this container.
    public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException {
       //Case where there are no players
       if (this.root == null) { throw new TennisDatabaseRuntimeException("There are no players in the container!\n"); }
       TennisPlayer [] out = new TennisPlayer[size];
       TennisPlayerContainerNode curr;
       TennisPlayerContainerIterator iter = new TennisPlayerContainerIterator( this.root );
       iter.setInorder();
       int i = 0;
       while( iter.hasNext() ) {
            curr = iter.next();
            TennisPlayer origin = curr.item; //creates shallow copy of the item
            SimpleDate dateClone;
            try { dateClone = new SimpleDate( origin.getBirthYear() ); } //does not require check as it has already went through light ingress filters
            catch( TennisDatabaseException e) { throw new TennisDatabaseRuntimeException("Query of player: " + origin.getId() + " failed, Error: " + e); }
            TennisPlayer copy = new TennisPlayer( origin.getId(), origin.getFirstName(), origin.getLastName(), dateClone, origin.getCountry() ); //creates deep copy of the item
            origin = null; //remove shallow copy
            out[i] = copy;
            i++;
        }
        return out;
    }   

    
    // Desc.: Obtain the record of player
    // Input: A tennis player
    public String getRecordOf( TennisPlayer p ) {
       TennisPlayerContainerNode pNode = getPlayerRec(this.root, p.getId());
       TennisMatch [] out = pNode.getMatches();
       int wins = 0;
       int losses = 0;
       for( int i = 0; i < out.length; i++) {
          if( (out[i].getWinner() == 0 && out[i].getIdPlayer1().equalsIgnoreCase( p.getId() )) ||
              (out[i].getWinner() == 1 && out[i].getIdPlayer2().equalsIgnoreCase( p.getId() )) ) { wins++; }
          else{ losses++; }
       }
       return( "\tWin/loss:" + wins + "/" + (losses + wins) );
    }

    // Desc.: Checks if the match is present in both players match arrays
    // Input: A tennis match
    public boolean fullyContains( TennisMatch m ) {
       TennisPlayerContainerIterator iter = new TennisPlayerContainerIterator( this.root );
       iter.setInorder();
       boolean found1 = false;
       boolean found2 = false;
       int i = 0;
       while( iter.hasNext() ) { //loop through all players
          TennisPlayerContainerNode curr = iter.next();
          TennisMatch [] playersMatches = curr.getMatches(); //need to do this and check getmatches in playernode 
          for( int j = 0; j < curr.matches.size(); j++) { //loop through the matches of each player
             if( playersMatches[j].equals(m) &&                 //if the match is the same
                 m.getIdPlayer1().equalsIgnoreCase(curr.item.getId()) ) { //and curr is player 1 of the match
                 found1 = true; 
             }
             if( playersMatches[j].equals(m) &&                 //if the match is the same
                 m.getIdPlayer2().equalsIgnoreCase(curr.item.getId()) ){ //and curr is player 2 of the match
                 found2 = true;
             }
          }
          curr = iter.next();
          i++;
       }
       return (found1 && found2);
    }
    
    public TennisMatch [] getMatchesOfPlayer( String playerString ) throws TennisDatabaseRuntimeException {
       TennisPlayerContainerNode playerNode = locateNodeRec( this.root, playerString );
       if (playerNode == null) {
         return null; //Throw an exception since player not found
       }
       else {
         SortedLinkedList<TennisMatch> match = playerNode.getMatchList();
         TennisMatch [] array = new TennisMatch[match.size()];
         for (int i = 0; i < match.size(); i++) {
            array[i] = match.get(i);
         }
         return array;
       }
    }
    
   // Desc.: Deletes all matches of input player (id) from this container.
   // Input: The id of the tennis player.
   // Output: Throws an unchecked (non-critical) exception if no matches are deleted.
   public void deleteMatchesOfPlayer( String playerId ) throws TennisDatabaseRuntimeException {}
}
package TennisDatabase;

import java.util.ArrayList;

class TennisMatchContainer {

    private ArrayList<TennisMatch> matchHandle;
    private int size;

    //Constructor for TennisMatchContainer
    public TennisMatchContainer() {
        this.matchHandle = new ArrayList<TennisMatch>();
        this.size = 0;
    }
    
    
    // Desc.: Delete a tennis match in this container.
    // Input: A tennis match.
    // Output: Throws an exception if the container is empty.
    public void deleteMatch( TennisMatch m ) throws TennisDatabaseRuntimeException {
        matchHandle.remove(m);
        this.size--; //decrement size
    }
    

    // Desc.: Insert a tennis match into this container.
    // Input: A tennis match.
    // Output: Throws a checked (critical) exception if the container is full.
    public void insertMatch( TennisMatch m ) throws TennisDatabaseException {
        if( this.size == 0 ) { //if this is the first insertion
           this.matchHandle.add(m);
           this.size++;
        }
        else if( this.size > 0 ) {
           Object [] array = this.matchHandle.toArray();
           int insertPoint = 0;
           for( int i = 0; i < array.length; i++ ) {
               if( ((TennisMatch)array[i]).compareTo(m) > 0 ) {
                  insertPoint++; //increment insertion point if condition is true
               }
           }
           this.matchHandle.add(insertPoint, m);
           this.size++;
        }
        else {
            throw new TennisDatabaseException("Container was full.");
        }
    }

    // Desc.: Returns copies (deep copies) of all matches in the database arranged in the output array (sorted by date, most recent first).
    // Output: Throws an exception if there are no matches in this container.
    public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException {
        if( size == 0 ){ throw new TennisDatabaseRuntimeException("No matches in container."); }
        TennisMatch [] out = new TennisMatch[this.size];
        if( out != null) {
           for( int i = 0; i < this.size; i++) {
               TennisMatch origin = matchHandle.get(i); //creates a shallow copy of the TennisMatch
               TennisMatch copy = new TennisMatch( origin.getIdPlayer1(), origin.getIdPlayer2(), origin.getDate(), origin.getTournament(), origin.getMatchScore() ); //creates a deep copy of the TennisMatch
               out[i] = copy;
               origin = null;
           }
        }
        return out;   
    }

    // Desc.: Returns copies (deep copies) of all matches of input player (id) arranged in the output array (sorted by date, most recent first).
    // Input: The id of the tennis player.
    // Output: Throws a checked (critical) exception if the tennis player (id) does not exists.
    //         Throws an unchecked (non-critical) exception if there are no tennis matches in the list (but the player id exists).
    public TennisMatch[] getMatchesOfPlayer( String playerId ) throws TennisDatabaseException, TennisDatabaseRuntimeException {
        boolean exists = false;
        int j = 0;
        TennisMatch [] outTemp = new TennisMatch[this.size];
        if( outTemp != null )
        {
           for( int i = 0; i < this.size; i++) { 
              if( this.matchHandle.get(i).getIdPlayer1().equalsIgnoreCase(playerId) || 
                  this.matchHandle.get(i).getIdPlayer2().equalsIgnoreCase(playerId) ) //if either player is playerId, the player must exist
              {
                  exists = true;
                  TennisMatch origin = this.matchHandle.get(i); //creates a shallow copy of the TennisMatch
                  TennisMatch copy = new TennisMatch( origin.getIdPlayer1(), origin.getIdPlayer2(), origin.getDate(), origin.getTournament(), origin.getMatchScore() ); //creates a deep copy of the TennisMatch
                  outTemp[j] = copy;
                  origin = null;
                  j++; //j will stand for the length of out and the last used index of outTemp
              }
           }
        }
        if( !exists ) { throw new TennisDatabaseException("Tennis Player ID does not exist.\n"); }
        if( j < 1 ) { throw new TennisDatabaseRuntimeException("No matches for given player.\n"); }         
        TennisMatch [] out = new TennisMatch[j]; //drop all found values into a correctly sized array
        for( ; j > 0 ; j--) {
            out[j-1] = outTemp[j-1];
        }
        return out; //return the array
    }
    
    // Desc.: Finds if the container contains the match
    // Input: TennisMatch
    // Output: boolean whether or not the object is present 
    public boolean contains( TennisMatch m ) {
      for( int i = 0; i < this.size; i++ ) {
         if( this.matchHandle.get(i).equals(m) ) { return true; }
      }
      return false;      
    }
    
    // Accessor
    public int getSize() {
           return this.size;
    }
}

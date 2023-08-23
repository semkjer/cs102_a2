


// Giuseppe Turini
// CS-102, Spring 2020
// Assignment 2

package TennisDatabase;

// Interface (package-private) providing the specifications for the TennisPlayerContainer class.
interface TennisPlayerContainerInterface {

   // Desc.: Returns the number of players in this container.
   // Output: The number of players in this container as an integer.
   public int getNumPlayers();
   
   // Desc.: Returns an iterator object ready to be used to iterate this container.
   // Output: The iterator object configured for this container.
   public TennisPlayerContainerIterator iterator();
   
   // Desc.: Search for a player in this container by input id, and returns a copy of that player (if found).
   // Output: Throws an unchecked (non-critical) exception if there is no player with that input id.
   public TennisPlayer getPlayer( String id ) throws TennisDatabaseRuntimeException;

   // Desc.: Insert a tennis player into this container.
   // Input: A tennis player.
   // Output: Throws a checked (critical) exception if player id is already in this container.
   //         Throws a checked (critical) exception if the container is full.
   public void insertPlayer( TennisPlayer p ) throws TennisDatabaseException;
   
   // Desc.: Search for a player in this container by id, and delete it with all his matches (if found).
   // Output: Throws an unchecked (non-critical) exception if there is no player with that input id.
   public void deletePlayer( String playerId ) throws TennisDatabaseRuntimeException;
   
   // Desc.: Insert a tennis match into the lists of both tennis players of the input match.
   // Input: A tennis match.
   // Output: Throws a checked (critical) exception if the insertion is not fully successful.
   public void insertMatch( TennisMatch m ) throws TennisDatabaseException;
   
   // Desc.: Returns all players in the database arranged in the output array (sorted by id, alphabetically).
   // Output: Throws an unchecked (non-critical) exception if there are no players in this container.
   public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException;
   
   // Desc.: Returns copies (deep copies) of all matches of input player (id) arranged in the output array (sorted by date, most recent first).
   // Input: The id of a player.
   // Output: Throws a checked (critical) exception if the player (id) does not exists.
   //         Throws an unchecked (non-critical) exception if there are no matches (but the player id exists).
   public TennisMatch[] getMatchesOfPlayer( String playerId  ) throws TennisDatabaseException, TennisDatabaseRuntimeException;
   
   // Desc.: Deletes all matches of input player (id) from this container.
   // Input: The id of the tennis player.
   // Output: Throws an unchecked (non-critical) exception if no matches are deleted.
   public void deleteMatchesOfPlayer( String playerId ) throws TennisDatabaseRuntimeException;
   
}



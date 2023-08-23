/*
Brandon Emkjer
Professor Turini
CS-102
Due 6.12.20
*/

package TennisDatabase;

import java.lang.Exception;
import TennisDatabase.TennisDatabaseRuntimeException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;


public class TennisDatabase implements  TennisDatabaseInterface {

    private TennisPlayerContainer playerHandle;
    private TennisMatchContainer matchHandle;
    private TennisMatchContainer orphanMatchHandle;
    
    // Desc.: Constructor
    public TennisDatabase() {
      this.playerHandle = new TennisPlayerContainer();
      this.matchHandle = new TennisMatchContainer();
      this.orphanMatchHandle = new TennisMatchContainer();
    }

    //String input loadFromFile method that assigns the String to a file and then calls loadFromFile with file
    public void loadFromFile( String filename ) throws TennisDatabaseException, TennisDatabaseRuntimeException { 
      File inFile = new File(filename);
      try{ loadFromFile( inFile ); }
      catch( TennisDatabaseException | TennisDatabaseRuntimeException e) { throw e; }
    }
    
    // Desc.: Loads data from file following the format described in the specifications,
    //        stores all matches without players in an orphan match container called orphanMatchHandle
    //        and attempts to reinsert them on a successful player insertion
    // Output: Throws an unchecked (non-critical) exception if the loading is not fully successfull.
    //         Throws a checked (critical) exception if the file (file name) does not exist.
    public void loadFromFile( File inFile ) throws TennisDatabaseException, TennisDatabaseRuntimeException {
        Scanner dataScan = null;
        try{ dataScan = new Scanner(inFile); }
        catch( FileNotFoundException e ) {throw new TennisDatabaseException("File name was incorrect or did not exist.");}
        
        String report = ""; //error reports
        int fullyFailedInsertions = 0;
        TennisMatch [] orphanMatches = new TennisMatch[orphanMatchHandle.getSize()];
        while(dataScan.hasNextLine()) {
            String input = dataScan.nextLine().trim();
            String [] inSplit = input.split("/");
            if( inSplit.length <= 1 ) { throw new TennisDatabaseRuntimeException("Failed insertion: " + input + ", incorrect format."); }
            switch( inSplit[0].toUpperCase() ) { //ignore the case 
            
            case "PLAYER": 
                boolean playerSuccess = false;
                boolean pMatchSuccess = true;
                SimpleDate inYear = new SimpleDate(Integer.parseInt(inSplit[4]) );
                TennisPlayer inPlayer = new TennisPlayer( inSplit[1], inSplit[2], inSplit[3], inYear, inSplit[5] );
                try {
                  playerHandle.insertPlayer( inPlayer );
                  playerSuccess = true;
                }  
                //let the method handle validation
                catch( NumberFormatException e ) { throw new TennisDatabaseRuntimeException("Insertion of player" + input + " failed due to incorrectly formated number"); }
                catch( TennisDatabaseException e ) { throw new TennisDatabaseRuntimeException("Insertion of player " + input + " failed, Error:" + e); }
                
                if( playerSuccess && orphanMatchHandle.getSize() != 0 ) { //if there are unpaired matches
                  try{ orphanMatches = orphanMatchHandle.getAllMatches(); }
                  catch( TennisDatabaseRuntimeException e ) {}
                  int orphanHandleSizeStart = orphanMatchHandle.getSize();
                   for( int i = 0; i < orphanHandleSizeStart; i ++) { 
                      if ( orphanMatches[i].getIdPlayer2().equalsIgnoreCase(inPlayer.getId()) || 
                           orphanMatches[i].getIdPlayer1().equalsIgnoreCase(inPlayer.getId()) ) {
                         try{ playerHandle.insertMatch( orphanMatches[i] ); }
                         catch( TennisDatabaseException e ) { } 
                         if( this.playerHandle.fullyContains(orphanMatches[i]) ) { //delete content from orphan if in both players  
                            orphanMatchHandle.deleteMatch( orphanMatches[i] );  
                            if( !matchHandle.contains( orphanMatches[i] ) ) { 
                               try{ matchHandle.insertMatch( orphanMatches[i] ); } 
                               catch(TennisDatabaseException e) { throw e; }
                            }
                         }
                      }
                   }
                }
                break;
                
            case "MATCH":
                boolean matchSuccess = false;
                String yearStr = inSplit[3].substring(0 , 4); //these values have already been checked
                String monthStr = inSplit[3].substring(4 , 6);
                String dayStr = inSplit[3].substring(6 , 8);
                int year = Integer.parseInt(yearStr);
                int month = Integer.parseInt(monthStr);
                int day = Integer.parseInt(dayStr);
                SimpleDate inDate = new SimpleDate( year, month, day );
                TennisMatch inMatch = new TennisMatch( inSplit[1], inSplit[2], inDate, inSplit[4], inSplit[5] );
                try {
                  playerHandle.insertMatch( inMatch );
                  matchSuccess = true;
                }
                //let the method handle validation
                catch( NumberFormatException e ) { throw new TennisDatabaseRuntimeException("Insertion of match" + input + " failed due to incorrectly formated number"); }
                catch( TennisDatabaseException e ) { matchSuccess = false; }
                if(matchSuccess) {
                  try{ matchHandle.insertMatch( inMatch ); }
                  catch( TennisDatabaseException e ) { throw new TennisDatabaseRuntimeException("Insertion of match " + input + " failed, Error:" + e); }
                }
                else {
                  try{ orphanMatchHandle.insertMatch( inMatch ); }
                  catch( TennisDatabaseException e ) { fullyFailedInsertions++;/*handled at end of method may not be needed?*/ }
                }
                break;  
                  
            default: 
                throw new TennisDatabaseRuntimeException("Failed insertion: " + input + ", incorrect format.");
            }
        }
        if( fullyFailedInsertions > 0 ) { throw new TennisDatabaseRuntimeException("Loading not fully successful, some matches were incomplete."); }
        try { orphanMatches = orphanMatchHandle.getAllMatches(); }
        catch( TennisDatabaseRuntimeException e ) {}
        for(int i = 0; i < orphanMatchHandle.getSize(); i++) { //find any unpaired orphans
           if( orphanMatches[i] != null && i == (orphanMatches.length-1) ) { report = report + "\nOrphan remaining: " + orphanMatches[i].toString(); } 
        }
        if( !report.equals("") ) { throw new TennisDatabaseRuntimeException( report ); }//depending on the use of this application and the amount of strictness required this could be critical or non critical
    }
    
    // Desc.: Retrurns the record of the provided player
    // Input: Object of type TennisPlayer
    // Output: Win/Loss record for the provided player
    public String getRecordOf( Object p ) {
       TennisPlayer in = (TennisPlayer)p;
       return playerHandle.getRecordOf(in);
    }


    // Desc.: Returns copies (deep copies) of all players in the database arranged in the output array (sorted by id, alphabetically).
    // Output: Throws an unchecked (non-critical) exception if there are no players in the database.
    public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException {
        try{ return playerHandle.getAllPlayers(); }
        catch( TennisDatabaseRuntimeException e ) { throw e; }
    }


    // Desc.: Returns copies (deep copies) of all matches of input player (id) arranged in the output array (sorted by date, most recent first).
    // Input: The id of a player.
    // Output: Throws a checked (critical) exception if the player (id) does not exists.
    //         Throws an uncheckedor (non-critical) exception if there are no matches (but the player id exists).
    public TennisMatch[] getMatchesOfPlayer( String playerId  ) throws TennisDatabaseException, TennisDatabaseRuntimeException {
        return matchHandle.getMatchesOfPlayer( playerId );
    }


    // Desc.: Returns copies (deep copies) of all matches in the database arranged in the output array (sorted by date, most recent first).
    // Output: Throws an unchecked (non-critical) exception if there are no matches in the database.
    public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException {
        return matchHandle.getAllMatches();
    }


    // Desc.: Insert a player into the database.
    // Input: All the data required for a player.
    // Output: Throws a checked (critical) exception if player id is already in the database.
    public void insertPlayer( String id, String firstName, String lastName, int year, String country ) throws TennisDatabaseException {
        SimpleDate bYear = new SimpleDate(year);
        TennisPlayer inPlayer = new TennisPlayer( id, firstName, lastName, bYear, country );  
        TennisMatch [] orphanMatches = new TennisMatch[orphanMatchHandle.getSize()];
        boolean playerSuccess = false;
        boolean pMatchSuccess = true;
        try {
           playerHandle.insertPlayer( inPlayer );
           playerSuccess = true;
        }  
        catch( NumberFormatException e ) { throw new TennisDatabaseRuntimeException("Insertion of player" + id + " failed due to incorrectly formated number"); }
        catch( TennisDatabaseException e ) { throw new TennisDatabaseRuntimeException("Insertion of player " + id + " failed, Error:" + e); }
        if( playerSuccess && orphanMatchHandle.getSize() != 0 ) { //if there are unpaired matches
           try{ orphanMatches = orphanMatchHandle.getAllMatches(); }
           catch( TennisDatabaseRuntimeException e ) {} 
           int fixedSize = orphanMatchHandle.getSize();
           for( int i = 0; i < fixedSize; i ++) { 
              if ( orphanMatches[i].getIdPlayer2().equalsIgnoreCase(inPlayer.getId()) || 
                   orphanMatches[i].getIdPlayer1().equalsIgnoreCase(inPlayer.getId()) ) {
                 try{ playerHandle.insertMatch( orphanMatches[i] ); }
                 catch( TennisDatabaseException e ) { }
                 if( this.playerHandle.fullyContains(orphanMatches[i]) ) {//delete from orphan if in both players  
                    orphanMatchHandle.deleteMatch( orphanMatches[i] );  
                    if( !matchHandle.contains( orphanMatches[i] ) ) { //ensure match is in match container
                       try{ matchHandle.insertMatch( orphanMatches[i] ); } 
                       catch(TennisDatabaseException e) { throw e; }
                    }
                 }
              }
           }
        }          
    }


    // Desc.: Insert a match into the database.
    // Input: All the data required for a match.
    // Output: Throws a checked (critical) exception if a player does not exist in the database.
    //         Throws a checked (critical) exception if the match score is not valid.
    public void insertMatch( String idPlayer1, String idPlayer2, int year, int month, int day, String tournament, String score ) throws TennisDatabaseException {
       SimpleDate inDate = new SimpleDate(year, month, day);
       TennisMatch inMatch = new TennisMatch( idPlayer1, idPlayer2, inDate, tournament, score );
       boolean matchSuccess = false;
       try {
          playerHandle.insertMatch( inMatch );
          matchSuccess = true;
       }
       //let the method handle validation
       catch( NumberFormatException e ) { throw new TennisDatabaseRuntimeException("Insertion of match" + inMatch.toString() + " failed due to incorrectly formated number"); }
       catch( TennisDatabaseException e ) { matchSuccess = false; }
       if(matchSuccess) {
          try{ matchHandle.insertMatch( inMatch ); } //attempts to insert match if successful
          catch( TennisDatabaseException e ) { throw new TennisDatabaseRuntimeException("Insertion of match " + inMatch.toString() + " failed, Error:" + e); }
       }
       else {
          try{ orphanMatchHandle.insertMatch( inMatch ); }
          catch( TennisDatabaseException e ) { throw new TennisDatabaseRuntimeException(""+e); }
       }
    }
   
   // Desc.: Search for a player in the database by id, and delete it with all the matches (if found).
   // Output: Throws an unchecked (non-critical) exception if there is no player with that input id.
   public void deletePlayer( String playerId ) throws TennisDatabaseRuntimeException { 
      //try-catch to delete matches
      try{ 
         TennisMatch [] toDelete = playerHandle.getMatchesOfPlayer( playerId );
         for( int i = 0; toDelete != null && i < toDelete.length; i++ ) {
            try{orphanMatchHandle.insertMatch(toDelete[i]);} 
            catch(TennisDatabaseException e) { throw new TennisDatabaseRuntimeException( "Warning: data loss in orphan container: " + toDelete[i] ); }
            matchHandle.deleteMatch(toDelete[i]);   
         }
      }
      catch( TennisDatabaseRuntimeException e ) { throw e; }
      //try-catch to delete player
      try{ playerHandle.deletePlayer( playerId ); }
      catch( TennisDatabaseRuntimeException e ) { throw e; }     
   }
   
   // Desc.: Saves data to file following the format described in the specifications.
   // Output: Throws a checked (critical) exception if the file open for writing fails.
   public void saveToFile( String fileName ) throws TennisDatabaseException { 
      File inFile = new File( fileName ); //create new file
      try{ saveToFile( inFile ); }catch( TennisDatabaseException e ) { throw e; }
   }
   public void saveToFile( File inFile ) throws TennisDatabaseException {
      try{
         FileWriter outFile = new FileWriter(inFile); //create fileWriter
         //set all arrays to size 0 so exceptions will not be thrown
         TennisPlayer [] outP = new TennisPlayer[0];
         TennisMatch [] outM = new TennisMatch[0];
         TennisMatch [] outO = new TennisMatch[0];
         //3 seperate try-catch loops for exceptions
         try {
            outP = playerHandle.getAllPlayers();
         } catch(TennisDatabaseRuntimeException e) {}
         try {
            outM = matchHandle.getAllMatches();
         } catch(TennisDatabaseRuntimeException e) {}
         try {
            outO = orphanMatchHandle.getAllMatches();
         } catch(TennisDatabaseRuntimeException e) {}
         //write contents in desired format
         for( int i = 0; i < outP.length; i++ ){ outFile.write( outP[i].toExportString() ); }
         for( int i = 0; i < outM.length; i++ ){ outFile.write( outM[i].toExportString() ); }
         for( int i = 0; i < outO.length; i++ ){ outFile.write( outO[i].toExportString() ); }
         outFile.flush();
         outFile.close(); //close fileWriter
      }catch( IOException e ) { throw new TennisDatabaseException("Writing to file failed: " + e); }
   }
   
   // Desc.: Resets the databaseby assigning the existing containers to new containers, making it empty.
   public void reset() { 
      this.playerHandle = new TennisPlayerContainer();
      this.matchHandle = new TennisMatchContainer();
      this.orphanMatchHandle = new TennisMatchContainer();
   } 
   
   public TennisPlayer getPlayer( String playerId ) throws TennisDatabaseRuntimeException{ return playerHandle.getPlayer( playerId ); }
}
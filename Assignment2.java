/*
X Emkjer
Professor Turini
CS-102
Due 6.12.20
*/

import TennisDatabase.TennisDatabase; //to use TennisDatabase
import TennisDatabase.TennisPlayer; //to use TennisPlayer
import TennisDatabase.TennisMatch; //to use TennisMatch
import TennisDatabase.TennisDatabaseRuntimeException; //for runtime exceptions
import TennisDatabase.TennisDatabaseException; //for database exceptions

import java.io.File; //for files
import java.io.FileWriter; //for writing to files
import java.io.IOException; //for file exceptions
import java.text.SimpleDateFormat; //For date verification
import java.util.Date; //For date verification
import java.util.Scanner; //To read inputs
import static java.lang.Integer.parseInt; //To change Strings into integers
import java.lang.String; //To convert to upper case

//InputFiles/a.txt
//The main class Assignment2 interacts with the database and displays options to the user 
public class Assignment2 {
    public static void main(String[] args) {
        Assignment2 assignment2 = new Assignment2(); //Create an instance of assignment 2
        TennisDatabase database = new TennisDatabase(); //Create an instance of TennisDatabase
        System.out.println("Enter the directory path of the file: ");
        Scanner fileNameScanner = new Scanner(System.in);
        String fileName = fileNameScanner.nextLine(); //Create an instance of scanner to read from console
        try {
            database.loadFromFile(fileName); //call the database to load information from given file
        }
        catch (TennisDatabaseException e) {System.out.println("A file of the name " + fileName + " does not exist\n");};
        assignment2.displayOptions(database); //call to display options
    }

    //controls the primary functions of the database, as the user input determines which methods will be implemented
    private void displayOptions(TennisDatabase database) {
        System.out.println("Welcome to my second CS-102 assignment, a TennisDatabase Manager");
        int val;
        String id;
        String fName;
        TennisPlayer player1;
        Scanner scan = new Scanner(System.in);
        Object [] output = null;
        do {
             System.out.println("Brandon Emkjer's CS-102 Tennis Manager - Available commands:"); //Create a menu displayed to user
             System.out.println("1 --> Print All Players");
             System.out.println("2 --> Print All Matches of a Player");  
             System.out.println("3 --> Print All Matches");   
             System.out.println("4 --> Insert New Player");   
             System.out.println("5 --> Insert New Match");  
             System.out.println("6 --> Exit");
             System.out.println("7 --> Print a Player");
             System.out.println("8 --> Delete a Player");
             System.out.println("9 --> Reset the Database");
             System.out.println("10 --> Import from File");
             System.out.println("11 --> Export to File");
             System.out.print("Enter user's choice: ");
             val = scan.nextInt(); //Assign val to user's selection
             while (val > 11 || val < 1) { //while loop for input validation
               System.out.print("Invalid input. Enter user's choice: ");
               val = scan.nextInt();
             }

            switch (val) { //switch statement for all 8 cases in the assignment
               case 1: //for print all players
                  try{ output = database.getAllPlayers();} //Calls the database to print all tennis players
                  catch( TennisDatabaseRuntimeException e ) { System.out.println("Player print failed: " + e); }
                  if(output == null) { System.out.println("The container is empty"); } //if true, should be accompanied by a critical exception origin player container
                        else {
                            for (int i = 0; i < output.length; i++) {
                                String winLoss = database.getRecordOf( output[i] ); //testing
                                System.out.println(output[i].toString() + winLoss + "\n"); //print the players                            
                            }
                        }
                  break;
               case 2: //for get matches of player
                  id = enterUserId(scan); //Calls a method to enter and verify the user ID
                  try{ output = database.getMatchesOfPlayer( id.toLowerCase() ); }//attempt print
                  catch( TennisDatabaseException e ) { System.out.println("Player " + id + " does not exist in database. Error: " + e); }
                  catch( TennisDatabaseRuntimeException e ) { System.out.println("Error occured querying matches of player: " + id + ". error: " + e); }
                  if( output != null) {
                    for( int i = 0; i < output.length; i++ ) {
                        System.out.println( output[i].toString() );
                    }
                  }
                  break;
               case 3: //for get all matches
                  try{ output = database.getAllMatches(); } //Calls the database to print all matches for a player
                  catch( TennisDatabaseRuntimeException e ) { System.out.println("Failed to query matches: " + e); }
                  if( output != null) {
                     for( int i = 0; i < output.length; i++ ) {
                         System.out.println( output[i].toString() );
                     }
                  }
                  break;
               case 4: //for insert player
                  boolean isValidPlayer; //boolean to test validity
                  String [] playerInformation; //an array of strings containing the player information
                  do {
                        System.out.println("Enter the player information that contains the ID, first name, last name, year, and country of birth.");
                        System.out.println("EXAMPLE: /BME01/BRANDON/EMKJER/2001/USA");
                        String pInfo = scan.next(); //reads the user's input
                        pInfo = pInfo.toUpperCase(); //Converting the string to upper case
                        playerInformation = pInfo.split("/"); //splits the input into separate strings
                        isValidPlayer = isValidPlayer(playerInformation);
                   } while (!isValidPlayer);
                   try {
                        database.insertPlayer(playerInformation[1], playerInformation[2], playerInformation[3], parseInt(playerInformation[4]), playerInformation[5]);
                   }
                   catch (TennisDatabaseException e) {};
                   break;
               case 5: //for insert match
                   boolean isValidMatch; //boolean to test validity
                   String [] matchInformation; //an array of strings containing the match information
                   do {
                       System.out.println("Enter the match information that contains the first player's ID, the second player's ID, the date, the location, and the scores.");
                       System.out.println("Response must be in format: /BME01/AMA01/2015/08/15/OAKLAND/7-6,6-4/");
                       String mInfo = scan.next(); //reads the user's input
                       mInfo = mInfo.toUpperCase(); //Converting the string to upper case
                       matchInformation = mInfo.split("/"); //splits the input into separate strings
                       isValidMatch = isValidMatch(matchInformation);
                   } while (!isValidMatch);
                   try {
                        database.insertMatch(matchInformation[1], matchInformation[2], parseInt(matchInformation[3]), parseInt(matchInformation[4]), parseInt(matchInformation[5]), matchInformation[6], matchInformation[7]);
                   }
                   catch (TennisDatabaseException e) {};
                   break;
               case 6: //for exit
                   System.out.print("Thank you for using my program!");
                   break;
               case 7: //for print a player
                  id = enterUserId(scan); //Calls a method to enter and verify the user ID
                  try{ 
                     player1 = database.getPlayer( id.toUpperCase() );
                     if (player1 != null) {
                        System.out.println(player1.toString()); //attempt print
                     } 
                  }
                  catch( TennisDatabaseRuntimeException e ) { System.out.println("Error occured querying player: " + id + ". Error: " + e); }
                  break;
               case 8: //for delete player
                  id = enterUserId(scan); //Calls a method to enter and verify the user ID
                  try{ database.deletePlayer( id.toUpperCase() );}
                  catch( TennisDatabaseRuntimeException e ) { System.out.println("Error occured during player deletion: " + id + ". Error: " + e); }
                  break;
               case 9: //for reset database
                  database.reset();
                  System.out.println("Database reset successfully!");
                  break;
               case 10: //for import file
                  database.reset(); //call to reset database
                  fName = enterFileName(scan); //call to method to enter file name
                  try {
                      database.loadFromFile(fName); //call the database to load information from given file
                  }
                  catch (TennisDatabaseException e) {System.out.println("A file of the name " + fName + " does not exist\n");};
                  break;
               case 11: //for export file
                  fName = enterFileName(scan); //call to method to enter file name
                  File fil = new File(fName); //instantiates a new file
                  try {
                     FileWriter fileWriter = new FileWriter(fil); //create FileWriter
         
                     // access arrays containing players/matches
                     TennisPlayer[] players = database.getAllPlayers();
                     TennisMatch[] matches = database.getAllMatches();
                     
                     //copy contents from arrays
                     for (TennisPlayer player : players) {
                         fileWriter.write(player.toString() + "\r\n");
                     }
                     for (TennisMatch match : matches) {
                         fileWriter.write(match.toString() + "\r\n");
                     }
                     fileWriter.flush();
                     fileWriter.close(); //close fileWriter
                  } catch (IOException | TennisDatabaseRuntimeException e) {throw new TennisDatabaseRuntimeException(e.getMessage());}
                  try {
                     database.saveToFile(fil); //save the data to new file
                  } catch (TennisDatabaseException e) {System.out.println("A file of the name " + fName + " does not exist\n");};
                  break;
               default:
                   System.out.println("ERROR: Input is not valid."); //For inputs x < 1 or x > 11
            }
        } while (val != 6); //6 is case for quit
    }

    
    //Method to input the player's ID
    private String enterUserId(Scanner input) {
      String id;
      do {
         System.out.print("Enter the player's ID: ");
         id = input.next(); //Reads input from scanner
      } while (!isValidId(id)); //calls the isValidId method to verify length
      return id;
    }

    //Method to verify if the player is valid
    private boolean isValidPlayer(String [] player) {
        return isValidId(player[1]) && isValidFirstOrLastName(player[2]) && isValidFirstOrLastName(player[3]) && isValidYear(player[4]) && isValidCountry(player[5]);
    }
    
    //Verifying if the length of the ID is proper
    private boolean isValidId(String id) {
        if(!id.isEmpty() && id.length() != 5) {
            System.out.println("The ID (" + id + ") must be 5 alphanumeric characters in length.");
            return false;
        }
        return true;
    }

    //Verifying if the names are not empty
    private boolean isValidFirstOrLastName(String fLName) {
        if(fLName.isEmpty()) {
            System.out.println(fLName + " is not a valid input.");
            return false;
        }
        return true;
    }

    //Verifying if the year is the proper digits
    private boolean isValidYear(String year) {
        if(!isNumeric(year) || year.length() != 4) {
            System.out.println("Year must have 4 digits!");
            return false;
        }
        return true;
    }
    
    //Verifying if the month is the proper digits
    private boolean isValidMonth(String month) {
        int m = parseInt(month);
        if(!isNumeric(month) || month.length() != 2) {
            System.out.println("Month must have 2 digits!");
            return false;
        }
        if(m < 1 || m > 12) {
            System.out.println("Month must be from 1-12!");
            return false;
        }
        return true;
    }

    //Verifying if the day is the proper digits
    private boolean isValidDay(String day) {
        if(!isNumeric(day) || day.length() != 2) {
            System.out.println("Day must have 2 digits!");
            return false;
        }
        return true;
    }
    
    //Verifying that the string is numeric
    private boolean isNumeric(String str)
    {
        try
        {
            double valueToCheckNumeric = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe) //Catching exception for number validity
        {
            return false;
        }
        return true;
    }

    //Verifying if the location is valid
    private boolean isValidCountry(String country) {
        return !country.isEmpty();
    }

    //Verifying if the match is valid by other methods
    private boolean isValidMatch(String [] match) {
        return isValidId(match[1]) && isValidId(match[2]) && isValidYear(match[3]) && isValidMonth(match[4]) && isValidDay(match[5]) && isValidCountry(match[6]) && isValidScore(match[7]);
    }
   
    //Uses simpleDateFormat to verify structure of the date
    private boolean isValidDate(String date) {
        try {
            Date date1 = new SimpleDateFormat("YYYYMMDD").parse(date); //setting a date in set format
        }
        catch (Exception e) {
            System.out.println("Date is not valid, " + date); //catching exception if the date is not valid
            return false;
        }
        if (date.length() != 8) {
            System.out.println("Date must be in YYYYMMDD format. You entered: " + date); //If result is not 8
            return false;
        }
        return true;
    }

   //Verifying if there are enough digits for score
    private boolean isValidScore(String score) {
        return !score.isEmpty() && score.length() >= 3;
    }
    
    //Method to scan file
    private String enterFileName(Scanner input) {
         String fileName_temp;
         System.out.println("Enter the directory path of the file: ");
         input.nextLine();
         fileName_temp = input.nextLine(); //Create an instance of scanner to read from console
         return fileName_temp;
    }
}


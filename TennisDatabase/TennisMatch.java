/*
Brandon Emkjer
Professor Turini
CS-102
Due 6.12.20
*/

package TennisDatabase;

public class TennisMatch implements TennisMatchInterface {
    private String idPlayer1;
    private String idPlayer2;
    private SimpleDate date;
    private String tournament;
    private String matchScore;
    private int winner;

    // Desc.: Constructor
    public TennisMatch(String idPlayer1, String idPlayer2, SimpleDate date, String tournament, String score) {
        this.idPlayer1 = idPlayer1;
        this.idPlayer2 = idPlayer2;
        this.date = date;
        this.tournament = tournament;
        this.matchScore = score;
        int rWinner = this.findWinner();
        if( rWinner > 0) { this.winner = 1; }
        else if( rWinner < 0) { this.winner = 0; }
        else{ this.winner = -1; }
    }
    
    // Desc.: Finds the winner of a match by calling a recursive funciton
    // Input: String of score, with or without commas, with or without spacess
    // Output: int, 0 for player 1, 1 for player 2
    public int findWinner() {
       return findWinnerRec( this.matchScore.trim().replace(',',' ') );
    }
    
    public int findWinnerRec( String score ) {
       if( score.length() >= 3 ) {
          String nextScore = score.substring( 0, 3 ).trim();
          String restScore = score.substring(3).trim();
          String [] currScore = nextScore.split("-");
          if( Integer.parseInt( currScore[0].trim() ) > Integer.parseInt( currScore[1].trim() ) ) { return -1 + findWinnerRec( restScore ); }
          else { return 1 + findWinnerRec( restScore ); }
       }
       else{ return 0; }
    }
    
    //Accessors
    public String getIdPlayer1() { return idPlayer1; }
    public String getIdPlayer2() { return idPlayer2; }    
    public SimpleDate getDate() {  return date; }    
    public String getDateString() { return date.toString(); }
    public int getDateDay() { return date.getDay(); } 
    public int getDateMonth() { return date.getMonth(); } 
    public int getDateYear() { return date.getYear(); }    
    public String getTournament() { return tournament; }
    public String getMatchScore() { return matchScore; }
    public int getWinner() { return winner; }
    

    //Mutators
    void setIdPlayer1(String idPlayer1) { this.idPlayer1 = idPlayer1; }
    void setIdPlayer2(String idPlayer2) { this.idPlayer2 = idPlayer2; }
    void setDate(SimpleDate date) { this.date = date; }
    void setTournament(String tournament) { this.tournament = tournament; }
    void setMatchScore(String matchScore) { this.matchScore = matchScore; }

    // Desc.: Basic toString
    // Output: String Cat'd data with formating and gift wrapping 
    public String toString() {
        return ( "\tPlayer 1 Id: " + this.idPlayer1 + " \tPlayer 2 Id: " + this.idPlayer2 + " \tMatch Date: " + this.date.toString() + " \tTournament: "
                + this.tournament + " \tMatch Score: " + this.matchScore + "\tWinner: Player " + (this.winner + 1) );
    }
    public String toExportString() {
        return("MATCH/" + this.idPlayer1 + "/" + this.idPlayer2 + "/" + this.date.toString() + "/" + this.tournament + "/" + this.matchScore + "\n");  
    }
    
    // Desc.: Prints an objects value, used for debugging
    // Output: toString of the given object
    public void print() {
        System.out.println( this.toString() );
    }
    
    // Desc.: Standard equals function to compare 2 objects
    // Input: TennisMatch object
    // Output: boolean of whether this equals the input match
    public boolean equals( TennisMatch in) {
       if(
          this.idPlayer1.equalsIgnoreCase( in.idPlayer1 ) &&
          this.idPlayer2.equalsIgnoreCase( in.idPlayer2 ) &&
          this.date.equals( in.date ) &&
          this.tournament.equalsIgnoreCase( in.tournament ) &&
          this.matchScore.equals( in.matchScore ) ) { return true; } //if everything matches then true
       else {return false;}
    }
    
    // Desc.: Standard compareTo method
    // Input: TennisMatch object
    // Output: priority to date not equal, -1 for this smaller than that, 1 for larger, if same then check other fields for a duplicate
    public int compareTo( TennisMatch in ) {
      if( !(this.date.compareTo( in.date ) == 0) ) { return this.date.compareTo( in.date ); }
      else if( !(this.idPlayer1.toLowerCase().compareTo(in.idPlayer1) == 0 ) ) { return this.idPlayer1.toLowerCase().compareTo(in.idPlayer1); }
      else if( !(this.idPlayer2.toLowerCase().compareTo(in.idPlayer2) == 0 ) ) { return this.idPlayer2.toLowerCase().compareTo(in.idPlayer2); }  
      else if( !(this.tournament.toLowerCase().compareTo(in.tournament) == 0 ) ) { return this.tournament.toLowerCase().compareTo(in.tournament); }
      else if( !(this.matchScore.toLowerCase().compareTo(in.matchScore) == 0 ) ) { return this.matchScore.toLowerCase().compareTo(in.matchScore); }
      else{ return 0; }
    }
}
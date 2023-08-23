/*
Brandon Emkjer
Professor Turini
CS-102
Due 6.12.20
*/

package TennisDatabase;

public class TennisPlayer implements TennisPlayerInterface {
    private String id;
    private String firstName;
    private String lastName;
    private SimpleDate birthYear;
    private String country;

    public TennisPlayer(String id, String firstName, String lastName, SimpleDate birthYear, String country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
        this.country = country;
    }

    //Accessors
    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getBirthYear() { return birthYear.getYear(); }
    public String getCountry() { return country; }

    //Mutators
    void setId(String id) { this.id = id; }
    void setFirstName(String firstName) { this.firstName = firstName; }
    void setLastName(String lastName) { this.lastName = lastName; }
    void setBirthYear(int birthYear) { this.birthYear.setYear(birthYear); }
    void setCountry(String country) { this.country = country;  }

    // Desc.: Basic toString
    // Output: String Cat'd data with formatting and giftwrapping
    public String toString() {
        return( "\tPlayer Id: " + this.id + " \tFirst Name: " + this.firstName + " \tLast Name: " + this.lastName + " \tBirth Year: " + this.birthYear.toString() + " \tCountry: " + this.country );
    }
    
    // Desc.: toString for file exports for player
    // Output: String Cat'd data
    public String toExportString() {
        return( "PLAYER/" + this.id + "/" + this.firstName + "/" + this.lastName + "/" + this.birthYear.toString() + "/" + this.country + "\n");
    }

    // Desc.: Print toString to the console
    // Output: toString printed to console
    public void print() {
       System.out.println( this.toString() );
    }
    
    // Desc.: ID ONLY compareTo method
    // Input: TennisPlayer object
    // Output: comparison of player ID's
    public int compareTo( TennisPlayer in ) {
      return this.id.toLowerCase().compareTo( in.id.toLowerCase() );
    }
    
    // Desc.: Basic equals function to check if two objects are the same
    // Input: TennisPlayer object
    // Output: boolean of same object or different one
    public boolean equals( TennisPlayer in ){  
       if( this.id.equalsIgnoreCase( in.id ) &&
           this.firstName.equalsIgnoreCase( in.firstName ) &&
           this.lastName.equalsIgnoreCase( in.lastName ) &&
           this.birthYear.getYear() == birthYear.getYear() &&
           this.country.equalsIgnoreCase( in.country ) ) { return true; }
       else{ return false; }
    }
}
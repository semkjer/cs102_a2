/*
Brandon Emkjer
Professor Turini
CS-102
Due 6.12.20
*/

package TennisDatabase;

class SimpleDate {

    int year;
    int month;
    int day;
    
    //Accessors
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getDay() { return day; }
    
    //Mutators
    void setYear( int year ) { this.year = year; }
    void setMonth( int month ) { this.month = month; }
    void setDay( int day ) { this.day = day; }

    //Overload Constructor
    public SimpleDate(int year, int month, int day ) throws TennisDatabaseRuntimeException {
        if( year < 0 || year > 9999 || month > 12 || month < 1 || day < 1 || day > 31 ) { throw new TennisDatabaseRuntimeException("Invalid input for date.\n"); }
        this.year = year;
        this.month = month;
        this.day = day;
    }
    
    //Overload Constructor
    public SimpleDate(int year) throws TennisDatabaseException {
        if( year < 0 || year > 9999) { throw new TennisDatabaseException("Invalid input for date.\n"); }
        this.year = year;
        this.month = 0;
        this.day = 0;
    }
    
    
    // Desc.: Compare this date to provided date, -1 is this is smaller, 0 if same, 1 is this is bigger
    // Input: Any files to be loaded
    // Output: All program output
    public int compareTo( SimpleDate other ) { //all values are known to be posisitve ints
        if( this.year > other.year ) { return 1; }
        else if( other.year > this.year ) { return -1; }
        else if( this.month > other.month ) { return 1; }
        else if( other.month > this.month ) { return -1; }
        else if( this.day > other.day ) { return 1; }
        else if( other.day > this.day ) { return -1; }
        else{ return 0; }
    }
    
    // Desc.: toString, prints with slashes    
    public String toString() {
       if( this.day == 0 || this.month == 0 ) {
          return (""+year);
       }
       else {
          return (year + "/" + this.month + "/" + this.day);
       }
    }
    
    public SimpleDate deepClone(){
       SimpleDate out = new SimpleDate( this.getYear(), this.getMonth(), this.getDay() );
       return out;
    }
}
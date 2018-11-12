/**
 * Class to represent one student.
 * @author Pete Lutz
 * @version 11-22-2017
 */
public class Student {
   private String lastName;
   private String firstName;
   private int id;
   private double gpa;
   private String email;
   private String street;
   private String city;
   private String state;
   private int zipcode;
   private String phone;
   
   /** Constructor */
   public Student(
      String _lastName, String _firstName,
      int _id, double _gpa, 
      String _email,
      String _street, String _city, String _state, int _zipcode,
      String _phone) {
      
      lastName = _lastName; firstName = _firstName;
      id = _id; gpa = _gpa;
      email = _email;
      street = _street; city = _city; state = _state; zipcode = _zipcode;
      phone = _phone;
   }
   
   /** Accessors for each field */
   String getLastName() { return lastName; };
   String getFirstName() { return firstName; };
   int getId() { return id; };
   double getGPA() { return gpa; };
   String getStreet() { return street; };
   String getCity() { return city; };
   String getState() { return state; };
   int getZipcode() { return zipcode; }
   String getPhone() { return phone; }
   
   /**
    * toString - return the student info as a String
    */
   public String toString() {
      String retVal = "";
      retVal += String.format("%-11.11s %-11.11s %d %3.1f ", lastName, firstName, id, gpa);
      retVal += String.format("%-25.25s %-15.15s %-2s %d %s", street, city, state, zipcode, phone);
      return retVal;
   }
   
   /**
    * headers - return a string which is the column headers
    * formatted to fit above toString above
    */
   public static String headers() {
      return "Last Name   First Name     ID GPA Street                    " +
         "City            ST   Zip Phone";
   }
}
import java.util.*;
import java.io.*;

/**
 * Collections Sort
 * Sorts an array of Students on Last Name. Uses a
 * class that implements the Comparator interface.
 * @author Pete Lutz
 * @version 11-23-2017
 */
public class StuSort3 {
   // 'list' is the list to sort ... read in from the
   // file named in STU_DATA
   private ArrayList<Student> list = new ArrayList<Student>();
   public static final String STU_DATA = "StuData.dat";
   
   /** main program ... just instantiate StuSort3 */
   public static void main(String[] args) {
   
      String field = "";
      String reverse = "";
      
      if(args.length < 1 || args.length > 2) {
         System.out.println("Usage: StuStor 3 FIELD [REVERSE]");
         System.exit(1);
      }
      else {
         field = args[0].toUpperCase();
            
         if(args.length == 2) {
            reverse = args[1].toUpperCase();
         }
      }
      
      new StuSort3(field, reverse);
   }
   
   /** constructor ... the effective main program */
   public StuSort3(String _field, String _reverse) {
      String field = _field;
      String reverse = _reverse;
   
      readData(); 
      
      if(reverse.equals("REVERSE")) 
         System.out.println("Sorting by field: " + field + " in descending order"); 
      else 
         System.out.println("Sorting by field: " + field + " in ascending order"); 
         
   
      switch(field) {
         case "LASTNAME":
            Collections.sort(list, new SortByLastName(reverse));  
            break;
         case "FIRSTNAME":
            Collections.sort(list, new SortByFirstName(reverse));  
            break;
         case "ID":
            Collections.sort(list, new SortById(reverse));  
            break;
         case "ZIPCODE":
            Collections.sort(list, new SortByZipCode(reverse));  
            break;
         default:
            System.out.println("Illegal field: " + field);
            System.exit(1);
            break;
      }    
      
      dispList();       // print data out
   }
   
   /**
    * SortByLastName ... inner class that implements Comparator<Student>
    * with method compare to compare two students any way we wish.
    */
   class SortByLastName implements Comparator<Student> {
      private String reverse = "";
      
      public SortByLastName(String _reverse) {
         reverse = _reverse;
      }
      
      public int compare(Student s1, Student s2) {
         if(reverse.equals("REVERSE"))
            return -s1.getLastName().compareTo(s2.getLastName()); 
         else
            return s1.getLastName().compareTo(s2.getLastName());
      
      }
   }
   
   class SortByFirstName implements Comparator<Student> {
      private String reverse = "";
      
      public SortByFirstName(String _reverse) {
         reverse = _reverse;
      }
      
      
      public int compare(Student s1, Student s2) {
         if(reverse.equals("REVERSE"))
            return -s1.getFirstName().compareTo(s2.getFirstName()); 
         else
            return s1.getFirstName().compareTo(s2.getFirstName());
      
      }
   }
   
   class SortById implements Comparator<Student> {
      private String reverse = "";
      
      public SortById(String _reverse) {
         reverse = _reverse;
      }
      
      
      public int compare(Student s1, Student s2) {
         if(reverse.equals("REVERSE"))
            return -Integer.compare(s1.getId(), s2.getId()); 
         else
            return Integer.compare(s1.getId(), s2.getId());
      }
   }
   
   class SortByZipCode implements Comparator<Student> {
      private String reverse = "";
      
      public SortByZipCode(String _reverse) {
         reverse = _reverse;
      }
      
      
      public int compare(Student s1, Student s2) {
         if(reverse.equals("REVERSE"))
            return -Integer.compare(s1.getZipcode(), s2.getZipcode()); 
         else
            return Integer.compare(s1.getZipcode(), s2.getZipcode());
      }
   }
   
   private void dispList() {
      System.out.println(Student.headers());
      for(Student s: list) {
         System.out.println(s);
      }
   }
   
   private void readData() {       
      Student oneStudent = null;   
      try {
         DataInputStream dis = new DataInputStream(new FileInputStream(new File(STU_DATA)));
         while(true) {
            int i = 0;
            String lastName = dis.readUTF();           // lastName
            String firstName = dis.readUTF();          // firstName
            int id = new Integer(dis.readInt());       // ID
            double gpa = new Double(dis.readDouble()); // GPA
            String email = dis.readUTF();              // email
            String street = dis.readUTF();             // street
            String city = dis.readUTF();                // city
            String state = dis.readUTF();              // state
            int zipcode = new Integer(dis.readInt());  // zip
            String phone = dis.readUTF();             // phone
            
            oneStudent = new Student(
               lastName, firstName, id, gpa,
               email,
               street, city, state, zipcode,
               phone);
            list.add(oneStudent);
         } // while
      }
      catch(EOFException eofe) {
         return;
      }
      catch(Exception e) {
         System.out.println("Exception: " + e);
      }
   }
}

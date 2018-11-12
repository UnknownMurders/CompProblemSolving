import java.util.*;
import java.io.*;

/**
 * Collections Sort
 * Sorts an array of Students on Last Name. Uses a
 * class that implements the Comparator interface.
 * @author Pete Lutz
 * @version 11-23-2017
 */
public class StuSort2 {
   // 'list' is the list to sort ... read in from the
   // file named in STU_DATA
   private ArrayList<Student> list = new ArrayList<Student>();
   public static final String STU_DATA = "StuData.dat";
   
   /** main program ... just instantiate StuSort2 */
   public static void main(String[] args) {
      new StuSort2();
   }
   
   /** constructor ... the effective main program */
   public StuSort2() {
      readData();       // read data in from the file
      
      // This line does the sorting. Comparisons are done
      // by calling a SortByName object with two Students
      Collections.sort(list, new SortByLastName());      
      
      dispList();       // print data out
   }
   
   /**
    * SortByLastName ... inner class that implements Comparator<Student>
    * with method compare to compare two students any way we wish.
    */
   class SortByLastName implements Comparator<Student> {
      public int compare(Student s1, Student s2) {
         return s1.getLastName().compareTo(s2.getLastName());
         //return -s1.getLastName().compareTo(s2.getLastName());
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
            String lastName = dis.readUTF();     // lastName
            String firstName = dis.readUTF();     // firstName
            int id = new Integer(dis.readInt());  // ID
            double gpa = new Double(dis.readDouble()); // GPA
            String email = dis.readUTF();     // email
            String street = dis.readUTF();     // street
            String city = dis.readUTF();     // city
            String state = dis.readUTF();     // state
            int zipcode = new Integer(dis.readInt());  // zip
            String phone = dis.readUTF();     // phone
            
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

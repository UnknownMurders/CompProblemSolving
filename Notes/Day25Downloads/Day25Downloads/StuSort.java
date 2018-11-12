import java.util.*;
import java.io.*;

/**
 * Student Sort
 * Sort an array of Student objects, by ID number. Finds smallest and swaps with first. 
 * Uses selection sort.
 * @author Pete Lutz
 * @version 11-17-2017
 */
public class StuSort {
   // 'list' is the list to sort ... read in from the
   // file named in STU_DATA
   private ArrayList<Student> list = new ArrayList<Student>();
   public static final String STU_DATA = "StuData.dat";
   
   /** main program ... just instantiate StuSort2 */
   public static void main(String[] args) {
      new StuSort();
   }
   
   /** constructor ... the effective main program */
   public StuSort() {
      readData();             // read data in from the file
      int start = 0;          // the index of the first element that is unsorted
      int candidate = start;  // the index of the candidate for smallest
      int current = 1;        // the index of the element being checked
      
      // One pass for each element except first
      while(start < list.size()-1) {
         // One pass ... find smallest element's index
         current = start + 1; // reset for another pass
         candidate = start;   // reset for next pass
         
         // The inner while loop == one pass
         // Looking for smallest value between list.get(start)
         // and list.get(list.size()-1]
         while(current < list.size()) {
            if(list.get(current).getId() < 
                  list.get(candidate).getId()) {  
               // current is smaller than the candidate
               candidate = current;
            }
            current++;
         }
         
         // If we found a smaller candidate than list[start], swap with
         // list[start] so the smallest value is at the start of the list
         if(start != candidate) {
            Student tmp = list.get(start);
            list.set(start, list.get(candidate));
            list.set(candidate, tmp);
         }
         
         // For next pass, increment start to start 1 place further on
         start++;
      }
      
      // When out of the outer loop we are all done
      dispList();
   }
   
   /** dispList
    * Display all elements of the list, in order
    */
   private void dispList() {
      System.out.println(Student.headers());
      for(Student s: list) {
         System.out.println(s);
      }
   }
   
   /** readData
    * read all data in the file into the list
    */
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

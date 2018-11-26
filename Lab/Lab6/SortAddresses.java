import java.util.*;
import java.io.*;
import java.text.*;

/*
NAME: Edward Riley
PROFESSOR: Erik Golen
COURSE: Computational Problem Solving in Domain II
DATE: 11/18/2018
*/


public class SortAddresses
{
   public static void main(String [] args)
   {
      ArrayList<Customer> customerList = new CustomerList();
   
      for( Customer info : customerList )
         System.out.println( info );
   	
      Comparator<Customer> zipComp = new ZipComparator();
      Collections.sort( customerList, zipComp );
    
      System.out.println("\n- - Sorted by ZIP:");
      for( Customer info : customerList )
         System.out.println( info );
                  
      Comparator<Customer> stateCityAddressComp = new StateCityAddressComparator();
      Collections.sort( customerList, stateCityAddressComp );
      
      System.out.println("\n- - Sorted by State, City, and Address:");
      for ( Customer info: customerList ) 
         System.out.println( info );

   } 
}

class ZipComparator implements Comparator<Customer>
{
   public int compare( Customer one, Customer two )
   {
      int zipOne = one.getZip();
      int zipTwo = two.getZip();
      
      if ( zipOne < zipTwo ) 
         return -1;
      if ( zipOne == zipTwo ) 
         return 0;
      return 1;
      
   } 
} 

class StateCityAddressComparator implements Comparator<Customer> {
   public int compare( Customer one, Customer two) {
   
      String stateOne = one.getState();
      String stateTwo = two.getState();
      
      if( stateOne.compareTo( stateTwo ) < 0 )
         return -1;
      
      if ( stateOne.compareTo ( stateTwo ) == 0 )
         return 0;
       return 1;
   }
}

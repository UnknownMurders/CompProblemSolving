import java.util.*;
import java.io.*;
import java.text.*;

/*
NAME: Edward Riley
PROFESSOR: Erik Golen
COURSE: Computational Problem Solving in Domain II
DATE: 11/18/2018
*/

class Customer {
   private String address;
   private int zip;
   private String city;
   private String state;   
   public Customer( String _address, String _city, String _state, int _zip ) {
      address = _address;
      city = _city;
      state = _state;
      zip = _zip;
   } 

   public String getAddress()	{ 
      return address;	
   }
   public String getCity() { 
      return city;		
   }
   public String getState() { 
      return state;	
   }
   public int getZip() { 
      return zip;     
   }
  
   public String toString() {
      return String.format("%-30s %-20s %4s %09d",
         getAddress(), getCity(), getState(), getZip());
   }
}
public class BinarySearch {
   public static void main(String[] args) {
      int[] data = {-1, 3, 5, 12, 16, 21, 28, 31, 33, 39, 40, 42};
      int target = -99999;
      
      try {
         target = 42;
         int found = binarySearch(data, target);
         System.out.println("" + target + " found at location " + found);
      }
      catch(Exception e) {
         System.out.println(e);
      }
   }
      
   static int binarySearch(int[] array, int target) throws Exception {
 		int start = 0;
 		int stop = array.length-1;
 		while(start < stop) {
 		 	int location = (start + stop) / 2;
 		 	if(target == array[location]) 	   // FOUND IT!
 			 	return location;
 		 	else if(target < array[location])	// Look in 1st half of array
 			 	stop = location - 1;
 		 	else if(target > array[location])	// Look in 2nd half of array
 			 	start = location + 1;
 		}
 		if(target == array[start])
 			return start;	// Found it at the end of the search
 		else
 			throw new Exception("Value: " + target + " not found");	
 	}
}

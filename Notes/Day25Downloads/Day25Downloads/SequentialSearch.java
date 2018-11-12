public class SequentialSearch {
   public static void main(String[] args) {
      int[] data = {-1, 3, 5, 12, 16, 21, 28, 31, 33, 39, 40, 42};
      int target = -99999;
      
      try {
         target = 21;
         int found = sequentialSearch(data, target);
         System.out.println("" + target + " found at location " + found);
      }
      catch(Exception e) {
         System.out.println(e);
      }
   }
      
   static int sequentialSearch(int[] array, int target) throws Exception {
      for(int location = 0; location < array.length; location++) {
 		 	if(target == array[location]) 	   // FOUND IT!
 			 	return location;
 		}
      throw new Exception("Value: " + target + " not found");	
 	}
}

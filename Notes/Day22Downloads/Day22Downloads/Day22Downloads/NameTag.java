import java.io.Serializable;

public class NameTag implements Serializable
{
   private String firstName = "";
   private String lastName = "";
   private String town = "";
   private String state = "";
   private String email = "";

   //Constructor
   public NameTag(String _fName, String _lName, String _town, String _state, String _email)
   {
      firstName = _fName;
      lastName = _fName;
      town = _town;
      state = _state;
      email = _email;
   }

   public String toString()
   {
      String tagText = new String(firstName +  " " + lastName +  " " + town +  " " + state +  " " + email);
      return tagText;
   }



}
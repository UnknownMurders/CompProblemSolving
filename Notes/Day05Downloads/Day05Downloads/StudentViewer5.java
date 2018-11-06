import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.stage.FileChooser.*;
import javafx.geometry.*;
import java.io.*;
import java.util.*;

public class StudentViewer5 extends Application {
   // File IO
   private Scanner scn = null;
   
   // Window setup
   Stage stage = null;
   Scene scene = null;
   VBox root = null;
   
   // Menu
   private MenuBar mbarBar = new MenuBar();
      // File Menu
      private Menu mnuFile = new Menu("File");
         // Items
         private MenuItem miOpen = new MenuItem("Open");
         private MenuItem miClose = new MenuItem("Close");
      
      // System Menu
      private Menu mnuSystem  = new Menu("System");
         // Items
         private MenuItem miAbout = new MenuItem("About");
         private MenuItem miExit = new MenuItem("Exit");
   
   // NORTH
   private TextField tfFile = new TextField();
   
   // CENTER
   private TextArea taData = new TextArea();
   
   public static void main(String[] args) {
      launch(args);
   }
   
   public void start(Stage _stage) {
      stage = _stage;
      stage.setTitle("Student Viewer 5 Example");
      
      // Set up menu
      mnuFile.getItems().addAll(miOpen, miClose);
      mnuSystem.getItems().addAll(miAbout, miExit);
      mbarBar.getMenus().addAll(mnuFile, mnuSystem);
      root = new VBox(mbarBar);
            
      // Handlers for menu items ... using anonymous inner classes
      miOpen.setOnAction(new EventHandler<ActionEvent>() {
         public void handle(ActionEvent evt) {
            doOpen();
         }
      });
      
      miClose.setOnAction(new EventHandler<ActionEvent>() {
         public void handle(ActionEvent evt) {
            doClose();
         }
      });
      
      miAbout.setOnAction(new EventHandler<ActionEvent>() {
         public void handle(ActionEvent evt) {
            doAbout();
         }
      });
      
      miExit.setOnAction(new EventHandler<ActionEvent>() {
         public void handle(ActionEvent evt) {
            System.exit(0);
         }
      });
      
      // Create the top as a FlowPane
      FlowPane fpTop = new FlowPane(8, 8);
      tfFile.setPrefColumnCount(25);
      tfFile.setEditable(false);
      fpTop.getChildren().add(tfFile);
      root.getChildren().add(fpTop);
      
      // Create the TextArea
      Font currentFont = taData.getFont();
      Font newFont = 
    		Font.font("MONOSPACED", FontWeight.NORMAL,   
    			currentFont.getSize());
      taData.setFont(newFont);
      root.getChildren().add(taData);

      scene = new Scene(root, 375, 175);
      stage.setScene(scene);
      stage.show();           
   }
   
   /**
    * readData
    * Process a 'Next' button click.
    * Read one line from the file (see open above) and
    * display it in the GUI
    */
   private void readData() {
      try {
         while(scn.hasNextLine()) {
            // hasNextLine is true ... read, split, and display
            String line = scn.nextLine();
            String[] field = line.split(":");
            taData.appendText(
               String.format("%-15.15s %5.5s %-10.10s %4s\n",
                  field[0], field[1], field[2], field[3]));
         }
      }
      // Exception ...
      catch(Exception e) {
         Alert alert = new Alert(AlertType.ERROR, "Exception (" + e + "} - Fatal");
         alert.showAndWait();
         System.exit(1);
      }
   } 
   
   /**
    * doOpen
    * Display a file chooser to allow the user to choose a file. Then open it.
    */
   private void doOpen() {
      if(scn != null)
         doClose();
         
      // As the user to choose a file
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Open Student File");
      fileChooser.getExtensionFilters().addAll(
         new ExtensionFilter("Text Files", "*.txt"),
         new ExtensionFilter("All Files", "*.*"));
         
      // Show the chooser
      File selectedFile = fileChooser.showOpenDialog(stage);
      if (selectedFile == null) {
         // Canceled
         return;
      }
      
      // File selected. Display file name in text field
      tfFile.setText(selectedFile.getAbsolutePath());
      
      // Open the file
      try {
         scn = new Scanner(new FileInputStream(selectedFile));
         readData();
      }
      catch(Exception e) {
         Alert alert = new Alert(AlertType.ERROR, "Exception (" + e + ") opening file ... fatal.");
         Optional<ButtonType> result = alert.showAndWait();
         System.exit(1);
      }
   }
   
   /**
    * doClose
    * Close the file, clear the tfFile text field
    */
   private void doClose() {
      try {
         scn.close();
      }
      catch(Exception e) {
         Alert alert = new Alert(AlertType.ERROR, "Exception (" + e + ") closing file ... fatal.");
         Optional<ButtonType> result = alert.showAndWait();
         System.exit(1);
      }
      
      // Clear the tfFile field and the TextArea
      tfFile.setText("");
      taData.setText("");
   }
   
   /**
    * doAbout
    * The about menu item
    */
   public void doAbout() {
      Alert alert = new Alert(AlertType.INFORMATION, "About ...\n     by Peter Lutz");
      Optional<ButtonType> result = alert.showAndWait();
      System.exit(1);
   }
}	

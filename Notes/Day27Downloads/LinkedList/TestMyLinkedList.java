import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TestMyLinkedList extends javafx.application.Application implements javafx.event.EventHandler<ActionEvent>
{
  private Stage stage;
  private javafx.scene.Scene scene;
  private VBox root = new VBox(8.0D);
  

  private TextArea taDisplay = new TextArea();
  private Button btnInsert = new Button("Insert");
  private TextField tfInsert = new TextField();
  private TextField tfAfter = new TextField();
  private Button btnRemove = new Button("Remove");
  private TextField tfRemove = new TextField();
  private TextField tfRemoved = new TextField();
  

  MyLinkedList<Integer> list = new MyLinkedList();
  
  public TestMyLinkedList() {}
  
  public static void main(String[] args) { launch(args); }
  


  public void start(Stage _stage)
  {
    stage = _stage;
    stage.setTitle("Test MyLinkedList");
    int WIDTH = 400;
    int HEIGHT = 300;
    stage.setOnCloseRequest(new javafx.event.EventHandler() {
      public void handle(WindowEvent evt) {
        System.exit(0);
      }
      
    });
    setupMenu();
    

    root.getChildren().add(taDisplay);
    

    GridPane gpRows2_3 = new GridPane();
    gpRows2_3.setHgap(8.0D);
    gpRows2_3.setVgap(8.0D);
    

    tfInsert.setPrefColumnCount(5);
    tfAfter.setPrefColumnCount(5);
    gpRows2_3.add(new Label("Insert: "), 0, 0);
    gpRows2_3.add(btnInsert, 1, 0);
    gpRows2_3.add(tfInsert, 2, 0);
    gpRows2_3.add(new Label("  After: "), 3, 0);
    gpRows2_3.add(tfAfter, 4, 0);
    

    tfRemove.setPrefColumnCount(5);
    tfRemoved.setEditable(false);
    tfRemoved.setPrefColumnCount(5);
    gpRows2_3.add(new Label("Remove: "), 0, 1);
    gpRows2_3.add(btnRemove, 1, 1);
    gpRows2_3.add(tfRemove, 2, 1);
    gpRows2_3.add(new Label("  Removed: "), 3, 1);
    gpRows2_3.add(tfRemoved, 4, 1);
    root.getChildren().add(gpRows2_3);
    

    btnInsert.setOnAction(this);
    btnRemove.setOnAction(this);
    

    scene = new javafx.scene.Scene(root, 400.0D, 300.0D);
    stage.setScene(scene);
    stage.show();
    
    doTest();
  }
  
  private void doTest()
  {
    taDisplay.setText(list.toString());
    tfInsert.requestFocus();
  }
  
  public void handle(ActionEvent ae)
  {
    String label = ((Button)ae.getSource()).getText();
    try {
      switch (label) {
      case "Insert": 
        doInsert();
        break;
      case "Remove": 
        doRemove();
      }
    }
    catch (MyLinkedListException mlle)
    {
      Alert alert = new Alert(Alert.AlertType.ERROR, mlle.toString(), new ButtonType[0]);
      alert.setHeaderText("MyLinkedListException");
      alert.showAndWait();
      return;
    }
    catch (NullPointerException npe) {
      Alert alert = new Alert(Alert.AlertType.ERROR, npe.toString(), new ButtonType[0]);
      alert.setHeaderText("NullPointerException");
      alert.showAndWait();
      return;
    }
  }
  
  private void doInsert()
    throws MyLinkedListException
  {
    if (tfInsert.getText().trim().equals("")) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Enter a number to insert ...", new ButtonType[0]);
      alert.setHeaderText("Missing number");
      alert.showAndWait();
      return;
    }
    

    if (tfAfter.getText().trim().equals("")) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Enter a index after which to insert ...", new ButtonType[0]);
      alert.setHeaderText("Missing index");
      alert.showAndWait();
      return;
    }
    

    Integer val = Integer.valueOf(0);
    try {
      val = Integer.valueOf(Integer.parseInt(tfInsert.getText()));
    }
    catch (NumberFormatException nfe) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Poorly formatted integer ...", new ButtonType[0]);
      alert.setHeaderText("Number Format");
      alert.showAndWait();
      return;
    }
    

    Integer index = Integer.valueOf(0);
    try {
      index = Integer.valueOf(Integer.parseInt(tfAfter.getText()));
    }
    catch (NumberFormatException nfe) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Poorly formatted integer ...", new ButtonType[0]);
      alert.setHeaderText("Number Format");
      alert.showAndWait();
      return;
    }
    

    Node<Integer> newNode = new Node(val);
    Node<Integer> current = list.getHead();
    Node<Integer> previous = null;
    Integer localInteger1; Integer localInteger2; for (; index.intValue() > -1; 
        

        localInteger2 = index = Integer.valueOf(index.intValue() - 1))
    {
      previous = current;
      current = current.getLink();
      localInteger1 = index;
    }
    if (previous == null) {
      newNode.setLink(list.getHead());
      list.setHead(newNode);
    }
    else {
      previous.insertAfter(newNode);
    }
    

    taDisplay.setText(list.toString());
    tfInsert.setText("");
    tfAfter.setText("");
    tfInsert.requestFocus();
  }
  
  private void doRemove()
    throws MyLinkedListException
  {
    if (tfRemove.getText().trim().equals("")) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Enter a index to remove ...", new ButtonType[0]);
      alert.setHeaderText("Missing index");
      alert.showAndWait();
      return;
    }
    

    Integer index = Integer.valueOf(0);
    try {
      index = Integer.valueOf(Integer.parseInt(tfRemove.getText()));
    }
    catch (NumberFormatException nfe) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Poorly formatted integer ...", new ButtonType[0]);
      alert.setHeaderText("Number Format");
      alert.showAndWait();
      return;
    }
    

    Node<Integer> current = list.getHead();
    Node<Integer> previous = null;
    Integer localInteger1; Integer localInteger2; for (; index.intValue() > 0; 
        

        localInteger2 = index = Integer.valueOf(index.intValue() - 1))
    {
      previous = current;
      current = current.getLink();
      localInteger1 = index;
    }
    if (previous == null) {
      list.setHead(list.getHead().getLink());
    }
    else {
      previous.setLink(current.getLink());
    }
    

    tfRemove.setText("");
    tfRemoved.setText("" + current.getData());
    taDisplay.setText(list.toString());
    tfRemove.requestFocus();
  }
  
  private void setupMenu()
  {
    javafx.scene.control.MenuItem miInstr = new javafx.scene.control.MenuItem("Instructions");
    Menu mnuHelp = new Menu("Help");
    mnuHelp.getItems().add(miInstr);
    javafx.scene.control.MenuBar mnuBar = new javafx.scene.control.MenuBar();
    mnuBar.getMenus().add(mnuHelp);
    root = new VBox(new javafx.scene.Node[] { mnuBar });
    
    miInstr.setOnAction(new javafx.event.EventHandler()
    {
      public void handle(ActionEvent ae) {
        String message = "To use this tester, you either add or remove integers from the list.\n\nTo add an integer, enter an value in the field to the right of the Insert\nbutton, and a position in the field labeled AFTER. Then, click Insert.\nThis will insert the value AFTER the given position in the list. Use the\nposition -1 to insert at the start of the list.\n\nTo remove an integer, enter its position in the field to the right of the\nRemove button and click Remove. The removed value will be displayed in the\nfield labeled Removed.\n\nThe state of the list will be displayed in the TextArea at the top of the\nwindow after every change\n";
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, new ButtonType[0]);
        alert.setHeaderText("Instructions");
        alert.showAndWait();
      }
    });
  }
}

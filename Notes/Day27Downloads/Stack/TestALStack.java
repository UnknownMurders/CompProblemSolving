import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TestALStack extends javafx.application.Application implements EventHandler<ActionEvent>
{
  private Stage stage;
  private javafx.scene.Scene scene;
  private VBox root = new VBox(8.0D);
  

  private TextArea taDisplay = new TextArea();
  private Button btnPush = new Button("Push");
  private TextField tfPush = new TextField();
  private Button btnPop = new Button("Pop");
  private TextField tfPop = new TextField();
  private Button btnPeek = new Button("Peek");
  private TextField tfPeek = new TextField();
  

  ALStack<Integer> myStack = new ALStack();
  
  public TestALStack() {}
  
  public static void main(String[] args) { launch(args); }
  


  public void start(Stage _stage)
  {
    stage = _stage;
    stage.setTitle("Test ALStack");
    int WIDTH = 400;
    int HEIGHT = 300;
    stage.setOnCloseRequest(new EventHandler() {
      public void handle(WindowEvent evt) {
        System.exit(0);
      }
      
    });
    root.getChildren().add(taDisplay);
    

    GridPane gpRows2_4 = new GridPane();
    gpRows2_4.setHgap(8.0D);
    gpRows2_4.setVgap(8.0D);
    

    tfPush.setPrefColumnCount(5);
    gpRows2_4.add(new Label("Push: "), 0, 0);
    gpRows2_4.add(btnPush, 1, 0);
    gpRows2_4.add(tfPush, 2, 0);
    

    tfPop.setPrefColumnCount(5);
    tfPop.setEditable(false);
    gpRows2_4.add(new Label("Pop:   "), 0, 1);
    gpRows2_4.add(btnPop, 1, 1);
    gpRows2_4.add(tfPop, 2, 1);
    

    tfPeek.setPrefColumnCount(5);
    tfPeek.setEditable(false);
    gpRows2_4.add(new Label("Peek: "), 0, 2);
    gpRows2_4.add(btnPeek, 1, 2);
    gpRows2_4.add(tfPeek, 2, 2);
    root.getChildren().add(gpRows2_4);
    

    btnPop.setOnAction(this);
    btnPush.setOnAction(this);
    btnPeek.setOnAction(this);
    

    scene = new javafx.scene.Scene(root, 400.0D, 300.0D);
    stage.setScene(scene);
    stage.show();
    
    doTest();
  }
  
  private void doTest()
  {
    taDisplay.setText(myStack.toString());
    tfPush.requestFocus();
  }
  
  public void handle(ActionEvent ae)
  {
    String label = ((Button)ae.getSource()).getText();
    try {
      switch (label) {
      case "Push": 
        doPush();
        break;
      case "Pop": 
        doPop();
        break;
      case "Peek": 
        doPeek();
      }
    }
    catch (ALStackException alse)
    {
      Alert alert = new Alert(Alert.AlertType.ERROR, alse.toString(), new ButtonType[0]);
      alert.setHeaderText("ALStackException");
      alert.showAndWait();
      return;
    }
  }
  
  private void doPush()
    throws ALStackException
  {
    if (tfPush.getText().trim().equals("")) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Enter a number to push ...", new ButtonType[0]);
      alert.setHeaderText("Missing Number");
      alert.showAndWait();
      return;
    }
    

    Integer val = Integer.valueOf(0);
    try {
      val = Integer.valueOf(Integer.parseInt(tfPush.getText()));
    }
    catch (NumberFormatException nfe) {
      Alert alert = new Alert(Alert.AlertType.ERROR, nfe.toString(), new ButtonType[0]);
      alert.setHeaderText("Number Format Exception");
      alert.showAndWait();
      return;
    }
    

    myStack.push(val);
    taDisplay.setText(myStack.toString());
    tfPush.setText("");
    tfPush.requestFocus();
  }
  
  private void doPop() throws ALStackException
  {
    Integer val = (Integer)myStack.pop();
    tfPop.setText("" + val);
    taDisplay.setText(myStack.toString());
    tfPush.requestFocus();
  }
  
  private void doPeek() throws ALStackException
  {
    Integer val = (Integer)myStack.peek();
    tfPeek.setText("" + val);
    taDisplay.setText(myStack.toString());
    tfPush.requestFocus();
  }
}

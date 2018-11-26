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

public class TestALQueue extends javafx.application.Application implements EventHandler<ActionEvent>
{
  private Stage stage;
  private javafx.scene.Scene scene;
  private VBox root = new VBox(8.0D);
  

  private TextArea taDisplay = new TextArea();
  private Button btnEnqueue = new Button("Enqueue");
  private TextField tfEnqueue = new TextField();
  private Button btnDequeue = new Button("Dequeue");
  private TextField tfDequeue = new TextField();
  private Button btnPeek = new Button("Peek");
  private TextField tfPeek = new TextField();
  

  ALQueue<Integer> myQueue = new ALQueue();
  
  public TestALQueue() {}
  
  public static void main(String[] args) { launch(args); }
  


  public void start(Stage _stage)
  {
    stage = _stage;
    stage.setTitle("Test ALQueue");
    int WIDTH = 400;
    int HEIGHT = 300;
    
    root.getChildren().add(taDisplay);
    

    GridPane gpRows2_4 = new GridPane();
    gpRows2_4.setHgap(8.0D);
    gpRows2_4.setVgap(8.0D);
    

    tfEnqueue.setPrefColumnCount(5);
    gpRows2_4.add(new Label("Enqueue: "), 0, 0);
    gpRows2_4.add(btnEnqueue, 1, 0);
    gpRows2_4.add(tfEnqueue, 2, 0);
    

    tfDequeue.setPrefColumnCount(5);
    tfDequeue.setEditable(false);
    gpRows2_4.add(new Label("Dequeue: "), 0, 1);
    gpRows2_4.add(btnDequeue, 1, 1);
    gpRows2_4.add(tfDequeue, 2, 1);
    

    tfPeek.setPrefColumnCount(5);
    tfPeek.setEditable(false);
    gpRows2_4.add(new Label("Peek: "), 0, 2);
    gpRows2_4.add(btnPeek, 1, 2);
    gpRows2_4.add(tfPeek, 2, 2);
    
    root.getChildren().add(gpRows2_4);
    

    btnDequeue.setOnAction(this);
    btnEnqueue.setOnAction(this);
    btnPeek.setOnAction(this);
    

    scene = new javafx.scene.Scene(root, 400.0D, 300.0D);
    stage.setScene(scene);
    stage.show();
    
    doTest();
  }
  
  private void doTest()
  {
    taDisplay.setText(myQueue.toString());
    tfEnqueue.requestFocus();
  }
  
  public void handle(ActionEvent ae)
  {
    String label = ((Button)ae.getSource()).getText();
    try {
      switch (label) {
      case "Enqueue": 
        doEnqueue();
        break;
      case "Dequeue": 
        doDequeue();
        break;
      case "Peek": 
        doPeek();
      }
    }
    catch (ALQueueException alqe)
    {
      Alert alert = new Alert(Alert.AlertType.ERROR, alqe.toString(), new ButtonType[0]);
      alert.setHeaderText("ALQueueException");
      alert.showAndWait();
      return;
    }
  }
  
  private void doEnqueue()
    throws ALQueueException
  {
    if (tfEnqueue.getText().trim().equals("")) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Enter a number to enqueue ...", new ButtonType[0]);
      alert.setHeaderText("Missing number");
      alert.showAndWait();
      return;
    }
    

    Integer val = Integer.valueOf(0);
    try {
      val = Integer.valueOf(Integer.parseInt(tfEnqueue.getText()));
    }
    catch (NumberFormatException nfe) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Poorly formatted integer ...", new ButtonType[0]);
      alert.setHeaderText("Number Format");
      alert.showAndWait();
      return;
    }
    

    myQueue.enqueue(val);
    taDisplay.setText(myQueue.toString());
    tfEnqueue.setText("");
    tfEnqueue.requestFocus();
  }
  
  private void doDequeue() throws ALQueueException
  {
    Integer val = (Integer)myQueue.dequeue();
    tfDequeue.setText("" + val);
    taDisplay.setText(myQueue.toString());
    tfEnqueue.requestFocus();
  }
  
  private void doPeek() throws ALQueueException
  {
    Integer val = (Integer)myQueue.peek();
    tfPeek.setText("" + val);
    taDisplay.setText(myQueue.toString());
    tfEnqueue.requestFocus();
  }
}

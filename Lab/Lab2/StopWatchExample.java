import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Class StopWatch
 * Implements a simple stop watch that will keep a counter of minutes, seconds, 
 * and tenths of seconds until stopped.
 * @author Pete Lutz
 * @version 10-15-2017
 */
public class StopWatchExample extends JFrame implements ActionListener {
   // GUI Components
   private JTextField jtfTime = new JTextField("00:00.0");
   private JButton jbStartStop = new JButton("Start");
   
   // Other attributes
   private javax.swing.Timer timer; // a timer to update the time
   private long currentTime = 0;    // the current time in msec
   
   /** the main program */
   public static void main(String[] args) {
      new StopWatchExample();
   }
   
   /** The constructor */
   public StopWatchExample() {
      // Set up the window
      this.setTitle("Stop Watch");
      this.setSize(400, 200);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // CENTER = timer
      jtfTime.setHorizontalAlignment(JTextField.CENTER);
      Font currentFont = jtfTime.getFont();
      jtfTime.setFont(new Font(currentFont.getFontName(), Font.BOLD, 48));
      this.add(jtfTime, BorderLayout.CENTER);
      
      // SOUTH = buttons
      JPanel jpSouth = new JPanel();
         jpSouth.add(jbStartStop);
         jbStartStop.addActionListener(this);
      this.add(jpSouth, BorderLayout.SOUTH);
      
      this.setVisible(true);
   }
   
   /** event dispatcher */
   public void actionPerformed(ActionEvent ae) {
      // the Start button toggles to Stop and vice versa
      switch(ae.getActionCommand()) {
         case "Start":
            jtfTime.setText("00:00.0");
            currentTime = 0;
            jbStartStop.setText("Stop");
            timer = new javax.swing.Timer(100, this);
            timer.setActionCommand("Timer");
            timer.start();
            break;
         case "Stop":
            timer.stop();
            jbStartStop.setText("Start");
            break;
         case "Timer":
            currentTime += 100;
            jtfTime.setText(
               // This formats the time as minutes, seconds, and tenths of seconds
               String.format("%02d:%02d.%d",
                  currentTime / 60000, (currentTime % 60000) / 1000, (currentTime % 1000) / 100) );
            break;
      }
   }
}
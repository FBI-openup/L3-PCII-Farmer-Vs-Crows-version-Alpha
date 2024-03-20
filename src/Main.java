import model.GameFrame;

public class Main {
    public static void main(String[] args) {

        //Schedule a job for the event-dispatching thread:
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameFrame(); //create and show the GUI
            }
        }
        );
    }
}
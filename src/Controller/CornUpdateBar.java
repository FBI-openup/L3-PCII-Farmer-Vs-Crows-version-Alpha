package Controller;


import Model.*;
import View.*;

/*
    * CornUpdateBar class
    * This class is the controller for the corn
    * It listens for mouse events on the corn panel
*/
public class CornUpdateBar extends Thread {
    private CornStateThread cornStateThread;
    private final CornStateView cornStateView = new CornStateView(cornStateThread);

    public CornUpdateBar(CornStateThread cornStateThread) {
        this.cornStateThread = cornStateThread;
    }

    @Override
    public void run() {
        try {
            while (true) {
                cornStateView.updateProgressBar();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

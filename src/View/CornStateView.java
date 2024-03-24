package View;

import Model.CornStateThread;
import Model.*;

import javax.swing.*;
import java.awt.Color;

/*
    * CornStateView class
    * It displays the state of the corn as a progress bar
*/
public class CornStateView extends JPanel {
    private final JProgressBar progressBar = new JProgressBar(0,10);
    private final CornStateThread cornStateThread;

    public CornStateView(CornStateThread cornStateThread) {
        this.cornStateThread = cornStateThread;
        updateProgressBar();
        this.add(progressBar);
    }

    public void updateProgressBar() {
        Corn corn = cornStateThread.getCorn();
        switch (corn.getState()) {
            case GROWING:
                progressBar.setValue(1);
                progressBar.setForeground(Color.GREEN);
                break;
            case READY_TO_HARVEST:
                progressBar.setValue(8);
                progressBar.setForeground(Color.YELLOW);
                break;
            case WILTED:
                progressBar.setValue(10);
                progressBar.setForeground(Color.RED);
                break;
        }
    }
}

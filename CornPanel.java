package view;
import javax.swing.*;
import model.*;
import java.awt.Color;

public class CornPanel extends JPanel {
    private Corn corn;
    private JProgressBar progressBar;

    public CornPanel(Corn corn) {
        this.corn = corn;
        this.progressBar = new JProgressBar(0, 10);
        updateProgressBar();
        this.add(progressBar);
    }

    public void updateProgressBar() {
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
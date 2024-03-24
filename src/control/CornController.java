package control;

import java.awt.event.*;
import java.util.Random;
import model.*;
import view.*;

public class CornController implements MouseListener {
    private Corn corn;
    private CornPanel cornPanel;
    private Random random;
    private  int REVENU_READY_TO_HARVEST = 20;
    private  int REVENU_WILTED = 8;
    private  int REVENU_GROWING = 5;


    public CornController(Corn corn, CornPanel cornPanel) {
        this.corn = corn;
        this.cornPanel = cornPanel;
        this.random = new Random();
        this.cornPanel.addMouseListener(this); // Add this controller as the mouse listener for the panel
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (corn.getState() == CornState.GROWING) {
            corn.setRevenue(REVENU_GROWING); // Set the revenue directly
        } else if (corn.getState() == CornState.WILTED) {
            corn.setRevenue(REVENU_WILTED);// Set the revenue directly
        }
        else { // CornState.READY_TO_HARVEST
            corn.setRevenue(REVENU_READY_TO_HARVEST); // Set the revenue directly
        }

        System.out.println("Revenue generated: " + corn.getRevenue());

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Do nothing
    }
}
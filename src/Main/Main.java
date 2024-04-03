package Main;

import Controller.*;
import Model.*;
import View.GameInterface;
import View.GamePanel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameInterface::new);
    }
}
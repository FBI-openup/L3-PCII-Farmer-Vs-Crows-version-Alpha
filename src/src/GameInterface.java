import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameInterface extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public GameInterface() {
        setTitle("Game Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Définir l'opération de fermeture
        setLayout(new BorderLayout());
        setSize(800, 600);// Définir la taille de la fenêtre


        cardLayout = new CardLayout();// Créer une nouvelle instance de CardLayout
        cardPanel = new JPanel(cardLayout);// Créer le panneau des cartes avec le CardLayout

        // Créer le panneau de départ avec une image de fond et un bouton
        BackgroundPanel startPanel = new BackgroundPanel("src/1.jpg");
        startPanel.setLayout(new GridBagLayout()); // 设置布局以使按钮居中
        JButton startButton = new JButton("Start Game");
        startPanel.add(startButton);
        cardPanel.add(startPanel, "Start");

        // Créer le panneau de jeu
        JPanel gamePanel = new JPanel(new BorderLayout());

        // Créer le panneau de contrôle avec un GridLayout pour les boutons de contrôle
        JPanel controlPanel = new JPanel(new GridLayout(2, 2)); // 修改为4行1列的GridLayout
        String[] buttonLabels = {"Move", "Plant a seed", "Harvest", "Stay"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(e -> System.out.println(label + " action performed!"));
            controlPanel.add(button);// Ajouter les boutons au panneau de contrôle
        }
        gamePanel.add(controlPanel, BorderLayout.EAST);


        JPanel mapPanel = new JPanel();
        mapPanel.setBackground(Color.WHITE);// Définir la couleur de fond du panneau de la carte
        gamePanel.add(mapPanel, BorderLayout.CENTER);// Ajouter le panneau de la carte au panneau de jeu


        cardPanel.add(gamePanel, "Game");

        // Ajouter un écouteur au bouton de démarrage pour changer de carte
        startButton.addActionListener((ActionEvent e) -> cardLayout.show(cardPanel, "Game"));

        add(cardPanel, BorderLayout.CENTER);// Ajouter le panneau des cartes à la fenêtre

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
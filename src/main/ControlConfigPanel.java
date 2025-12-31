package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class ControlConfigPanel extends JDialog {
    private final String[] actions = {"Gauche", "Droite", "Rotation", "Descente Rapide"};
    private final String[] defaultKeys = {"Q", "D", "Z", "S"};
    private final String[] defaultPad = {"←", "→", "↺", "↓"};
    private final JButton[][] buttons = new JButton[4][2];
    private int waitingRow = -1, waitingCol = -1;

    public HashMap<String, String> keyBindings = new HashMap<>();
    public HashMap<String, String> padBindings = new HashMap<>();

    public ControlConfigPanel() {
        setTitle("Configuration des contrôles");
        setModal(false);
        setSize(420, 260);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel tablePanel = new JPanel(new GridLayout(5, 3));
        tablePanel.add(new JLabel("", SwingConstants.CENTER));
        tablePanel.add(new JLabel("Clavier", SwingConstants.CENTER));
        tablePanel.add(new JLabel("Manette", SwingConstants.CENTER));

        for (int i = 0; i < actions.length; i++) {
            tablePanel.add(new JLabel(actions[i], SwingConstants.CENTER));
            JButton keyBtn = new JButton(defaultKeys[i]);
            JButton padBtn = new JButton(defaultPad[i]);
            buttons[i][0] = keyBtn;
            buttons[i][1] = padBtn;
            keyBindings.put(actions[i], defaultKeys[i]);
            padBindings.put(actions[i], defaultPad[i]);

            int row = i;
            keyBtn.addActionListener(e -> {
                waitingRow = row;
                waitingCol = 0;
                keyBtn.setText("...");
                requestFocus();
            });
            padBtn.addActionListener(e -> {
                waitingRow = row;
                waitingCol = 1;
                padBtn.setText("...");
                requestFocus();
            });

            tablePanel.add(keyBtn);
            tablePanel.add(padBtn);
        }

        add(tablePanel, BorderLayout.CENTER);
        add(new JLabel("Cliquez sur une touche pour la modifier. Appuyez sur P pour fermer.", SwingConstants.CENTER), BorderLayout.SOUTH);

        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setVisible(false);

        // Capture clavier
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (waitingRow != -1 && waitingCol == 0) {
                    String key = KeyEvent.getKeyText(e.getKeyCode());
                    buttons[waitingRow][0].setText(key);
                    keyBindings.put(actions[waitingRow], key);
                    waitingRow = -1;
                    waitingCol = -1;
                }
            }
        });

        // Capture manette (ici, on simule avec les flèches du clavier)
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (waitingRow != -1 && waitingCol == 1) {
                    String pad = "";
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT: pad = "←"; break;
                        case KeyEvent.VK_RIGHT: pad = "→"; break;
                        case KeyEvent.VK_UP: pad = "↺"; break;
                        case KeyEvent.VK_DOWN: pad = "↓"; break;
                        default: pad = KeyEvent.getKeyText(e.getKeyCode()); break;
                    }
                    buttons[waitingRow][1].setText(pad);
                    padBindings.put(actions[waitingRow], pad);
                    waitingRow = -1;
                    waitingCol = -1;
                }
            }
        });
    }
}

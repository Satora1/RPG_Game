package Main;

import javax.swing.*;

public class Main {
    public static JFrame window;

    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Game");


        // Tworzenie panelu gry
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // Wczytanie konfiguracji (jeśli to konieczne)
        gamePanel.config.loadConfig();

        // Ustawienie trybu pełnoekranowego, jeśli jest włączony
        if (gamePanel.fullScreenOn) {
            window.setUndecorated(true);
        }

        // Wyświetlenie okna
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Ustawienie gry (np. wczytanie mapy lub zasobów)
        gamePanel.setupGame();

        // Rozpoczęcie głównego wątku gry
        gamePanel.startGameThread();

    }
}

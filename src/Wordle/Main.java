package Wordle;

import java.io.IOException;
import java.util.List;

/**
 * Clase principal para iniciar el juego de Wordle.
 * @author Francisco Alejandro Fernández Ferrón
 * @date 04/02/2025
 */
public class Main {
    
    /**
     * Método principal que inicia el juego.
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        try {
            // Cargar palabras desde words.txt
            List<String> words = WordleFileManager.loadWords();
            WordleGame game = new WordleGame(words.toArray(new String[0]));

            // Iniciar el juego
            game.start();

            // Guardar el historial de la partida
            WordleFileManager.saveGameHistory("Partida finalizada");

        } catch (IOException e) {
            System.out.println("Error al cargar o guardar archivos: " + e.getMessage());
        }
    }
}
package Wordle;

import java.io.IOException;
import java.util.List;

public class Main {
	
	/**
	 * Clase principal para iniciar el juego de Wordle.
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

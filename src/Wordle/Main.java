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
    	// Introducción al juego
        System.out.println("¡Bienvenido a Wordle!");
        System.out.println("Reglas del juego:");
        System.out.println("- Debes adivinar una palabra oculta " + WordleGame.getWordLength() + " letras");
        System.out.println("- Cada intento debe ser una palabra válida de la misma longitud.");
        System.out.println("- Colores de las letras:");
        System.out.println("  \u001B[32mVerde\u001B[0m  -> La letra está en la palabra y en la posición correcta.");
        System.out.println("  \u001B[33mAmarillo\u001B[0m -> La letra está en la palabra, pero en la posición incorrecta.");
        System.out.println("  Blanco -> La letra no está en la palabra.");
        System.out.println("- Tienes un número limitado de"+ WordleGame.getMaxTries() + "intentos. ¡Buena suerte!");
        System.out.println();
    	
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
package Wordle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de la gestión de archivos para el juego Wordle.
 * Permite leer palabras desde un archivo y guardar el historial de partidas.
 */
public class WordleFileManager {

    private static final String WORDS_FILE = "words.txt";
    private static final String HISTORY_FILE = "game_history.txt";

    /**
     * Lee palabras desde el archivo words.txt.
     *
     * @return Lista de palabras leídas desde el archivo.
     * @throws IOException Si ocurre un error de lectura.
     */
    public static List<String> loadWords() throws IOException {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(WORDS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim().toLowerCase());
            }
        }
        return words;
    }

    /**
     * Guarda el historial de la partida en un archivo game_history.txt.
     *
     * @param content Texto que se desea guardar.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public static void saveGameHistory(String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE, true))) {
            writer.write(content);
            writer.newLine();
        }
    }
}




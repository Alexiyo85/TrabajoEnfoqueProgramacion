package Wordle;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase que gestiona la lógica del juego Wordle.
 * @author Francisco Alejandro Fernández Ferrón
 * @date 04/02/2025
 */
public class WordleGame {
    private static final int MAX_TRIES = 6;
    private static final int WORD_LENGTH = 5;
    private final String[] fileWords;
    private String secretWord;
    private int remainingAttempts;
    private StringBuilder triesHistory;

    /**
     * Constructor de la clase WordleGame.
     * @param fileWords Array de palabras cargadas desde el archivo.
     */
    public WordleGame(String[] fileWords) {
        this.fileWords = fileWords;
        initializeGame();
    }

    public static int getMaxTries() {
		return MAX_TRIES;
	}

	public static int getWordLength() {
		return WORD_LENGTH;
	}

	/**
     * Inicializa el juego seleccionando una palabra aleatoria y reiniciando los intentos.
     */
    private void initializeGame() {
        if (fileWords.length == 0) {
            throw new IllegalStateException("El archivo de palabras está vacío.");
        }
        this.secretWord = selectRandomWord(fileWords);
        this.remainingAttempts = MAX_TRIES;
        this.triesHistory = new StringBuilder();
    }

    /**
     * Selecciona una palabra aleatoria del array de palabras.
     * @param words Array de palabras.
     * @return Una palabra aleatoria.
     */
    private String selectRandomWord(String[] words) {
        Random random = new Random();
        return words[random.nextInt(words.length)];
    }

    /**
     * Inicia el juego y maneja la interacción con el usuario.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain;

        do {
            initializeGame();

            while (remainingAttempts > 0) {
                showTriesHistory();
                System.out.print("Ingrese una palabra de " + WORD_LENGTH + " letras: ");
                String userInput = getUserInput(scanner);

                if (userInput.equalsIgnoreCase(secretWord)) {
                    System.out.println("¡Felicidades! Has adivinado la palabra secreta: " + secretWord);
                    break;
                } else {
                    remainingAttempts--;
                    String feedback = WordleFeedback.feedBackString(userInput, secretWord);
                    triesHistory.append(userInput).append(" - ").append(feedback).append("\n");
                    System.out.println(feedback);
                }
            }

            if (remainingAttempts == 0) {
                System.out.println("Lo siento, te has quedado sin intentos. La palabra secreta era: " + secretWord);
            }

            try {
                WordleFileManager.saveGameHistory("Partida finalizada.\nHistorial:\n" + getTriesHistory());
            } catch (IOException e) {
                System.out.println("Error al guardar el historial de la partida: " + e.getMessage());
            }
            playAgain = askToPlayAgain(scanner);

        } while (playAgain);

        System.out.println("Gracias por jugar. ¡Hasta la próxima!");
        scanner.close();
    }

    /**
     * Pregunta al usuario si desea jugar de nuevo.
     * @param scanner Scanner para leer la entrada del usuario.
     * @return true si el usuario desea jugar de nuevo, false en caso contrario.
     */
    private boolean askToPlayAgain(Scanner scanner) {
        System.out.print("¿Quieres jugar de nuevo? (s/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("s") || response.equals("si");
    }

    /**
     * Muestra el historial de intentos del usuario.
     */
    private void showTriesHistory() {
        System.out.println("Historial de intentos:");
        if (triesHistory.length() == 0) {
            System.out.println("(Sin intentos previos)");
        } else {
            String[] attempts = triesHistory.toString().split("\n");
            for (int i = 0; i < attempts.length; i++) {
                System.out.println((i + 1) + ". " + attempts[i]);
            }
        }
    }

    /**
     * Obtiene la entrada del usuario, asegurándose de que sea válida.
     * @param scanner Scanner para leer la entrada del usuario.
     * @return La palabra ingresada por el usuario.
     */
    private String getUserInput(Scanner scanner) {
        String input;
        do {
            input = scanner.nextLine().trim().toLowerCase();
            if (!input.matches("^[a-zA-Z]{" + WORD_LENGTH + "}$")) {
                System.out.println("Entrada inválida. Debe contener exactamente " + WORD_LENGTH + " letras y solo caracteres alfabéticos.");
                System.out.print("Ingrese una palabra de " + WORD_LENGTH + " letras: ");
            }
        } while (!input.matches("^[a-zA-Z]{" + WORD_LENGTH + "}$"));
        return input;
    }

    /**
     * Obtiene el historial de intentos.
     * @return El historial de intentos como una cadena de texto.
     */
    public String getTriesHistory() {
        return triesHistory.toString();
    }

}

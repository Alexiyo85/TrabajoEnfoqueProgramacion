package Wordle;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class WordleGame {
    private final int MAX_TRIES = 6;
    private final int WORD_LENGTH = 5;
    private String[] fileWords;
    private String secretWord;
    private int remainingAttempts;
    private StringBuilder triesHistory;

    public WordleGame(String[] fileWords) {
        this.fileWords = fileWords;
        initializeGame();
    }

    private void initializeGame() {
        if (fileWords.length == 0) {
            throw new IllegalStateException("El archivo de palabras está vacío.");
        }
        this.secretWord = selectRandomWord(fileWords);
        this.remainingAttempts = MAX_TRIES;
        this.triesHistory = new StringBuilder();
    }

    private String selectRandomWord(String[] words) {
        Random random = new Random();
        return words[random.nextInt(words.length)];
    }

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

    private boolean askToPlayAgain(Scanner scanner) {
        System.out.print("¿Quieres jugar de nuevo? (s/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("s") || response.equals("si");
    }

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

    private String getUserInput(Scanner scanner) {
        String input;
        do {
            input = scanner.nextLine().trim().toLowerCase();
            if (!input.matches("^[a-zA-Z]{" + WORD_LENGTH + "}$")) {
                System.out.println("Entrada inválida. Debe contener exactamente " + WORD_LENGTH + " letras y solo caracteres alfabéticos.");
            }
        } while (!input.matches("^[a-zA-Z]{" + WORD_LENGTH + "}$"));
        return input;
    }

    public String getTriesHistory() {
        return triesHistory.toString();
    }
}

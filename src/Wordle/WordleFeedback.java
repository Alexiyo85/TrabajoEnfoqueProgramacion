package Wordle;

/**
 * Clase que proporciona feedback visual al usuario sobre su intento.
 * @author Francisco Alejandro Fernández Ferrón
 * @date 04/02/2025
 */
public class WordleFeedback {
    private static final int WORD_LENGTH = 5;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Aplica un color ANSI a la letra proporcionada.
     * @param letter Letra a la que se aplicará el color.
     * @param color Código de color ANSI.
     * @return La letra con el color aplicado.
     */
    private static String applyColor(String letter, String color) {
        return color + letter + ANSI_RESET;
    }

    /**
     * Genera un feedback coloreado para la palabra ingresada.
     * @param guess Palabra ingresada por el usuario.
     * @param secretWord Palabra secreta que se debe adivinar.
     * @return Cadena de texto con el feedback coloreado.
     */
    public static String feedBackString(String guess, String secretWord) {  
        StringBuilder feedback = new StringBuilder("_____");
        boolean[] secretUsed = new boolean[WORD_LENGTH];
        boolean[] guessUsed = new boolean[WORD_LENGTH];

        // Letras correctas en la posición correcta (verde)
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (guess.charAt(i) == secretWord.charAt(i)) {
                feedback.setCharAt(i, '*'); // Marcamos temporalmente con '*'
                guessUsed[i] = true;
                secretUsed[i] = true;
            }
        }

        // Letras correctas en posición incorrecta (amarillo)
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (feedback.charAt(i) != '*') { // Si no está marcada como verde
                for (int j = 0; j < WORD_LENGTH; j++) {
                    if (!secretUsed[j] && !guessUsed[i] && guess.charAt(i) == secretWord.charAt(j)) {
                        feedback.setCharAt(i, '?'); // Marcamos temporalmente con '?'
                        secretUsed[j] = true;
                        guessUsed[i] = true;
                        break;
                    }
                }
            }
        }

        // Aplicamos los colores a las letras en la palabra ingresada
        StringBuilder coloredFeedback = new StringBuilder();
        for (int i = 0; i < WORD_LENGTH; i++) {
            char guessChar = guess.charAt(i);
            if (feedback.charAt(i) == '*') {
                coloredFeedback.append(applyColor(String.valueOf(guessChar), ANSI_GREEN));
            } else if (feedback.charAt(i) == '?') {
                coloredFeedback.append(applyColor(String.valueOf(guessChar), ANSI_YELLOW));
            } else {
                coloredFeedback.append(applyColor(String.valueOf(guessChar), ANSI_WHITE));
            }
        }
        return coloredFeedback.toString();
    }
}

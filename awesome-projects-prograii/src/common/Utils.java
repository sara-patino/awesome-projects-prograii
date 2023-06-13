package common;

public class Utils {
    /**
     * Método para limpiar String cambiando un token por otro valor
     * 
     * @param tokens
     * @param token
     * @param replacement
     * @return
     */
    public static String[] clearString(String[] tokens, String token, String replacement) {
        int length = tokens.length;
        String[] items = new String[length];
        int index = 0;

        for (index = 0; index < length; index++) {
            items[index] = tokens[index].replaceAll(token, replacement);
        }
        return items;
    }

    public static void cleanTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
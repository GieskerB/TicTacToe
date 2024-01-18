package main.java.de.gieskerb.tictactoe.model;

/**
 * GameState is a simple container for the two Bitmaps of both players.
 * Primarily used for simply exporting only the GameState with only the most important Methods.
 */
public class GameState {

    /**
     * number of rows and columns of the board.
     */
    final byte size;

    /**
     * Bitmaps contain information about the location where a player made a move.
     * A one in the map says the player made a move in that location.
     * Going from LSB to MSB in the bitmap, it will represent the game tile
     * from the bottom right going from right to left and bottom to top.
     */
    long bitMapPlayer1,bitMapPlayer2;

    public GameState(byte size) {
        this.size = size;
        this.bitMapPlayer1 = 0;
        this.bitMapPlayer2 = 0;
    }

    /**
     * Allowing for public access to the bitmap
     * @return bitmap form player one
     */
    public long getBitMapPlayer1() {
        return this.bitMapPlayer1;
    }

    /**
     * Allowing for public access to the bitmap
     * @return bitmap form player two
     */
    public long getBitMapPlayer2() {
        return this.bitMapPlayer2;
    }

    /*
     * One of the Main reasons for separating the GameState into a singular class is a compression algorithm.
     * Converting the combined 128-Bit bitmap into at most 4 to 20 character Strings makes this easier to handle.
     */


    /**
     * The Precalculated number of characters needed to store the bitmap depending on the size of the board.
     * Board.size - two = index of this array.
     */
    private static final byte[] keyLengthByBoardSize = new byte[]{1, 2, 3, 4, 6, 7, 10};

    /**
     * Lookup char array for conversion to base 64.
     */
    private static final char[] BASE64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    /**
     * Converting a number from base10 into a base64 stored in a String.
     * @param stringBuilder For better performance to base64 is constructed in a StringBuilder.
     * @param number Number that will be converted.
     * @param size The Size of the board is necessary to know to determine the length of the key. May need to add zeros.
     */
    private static void longBase10toStringBase64(StringBuilder stringBuilder, long number, byte size) {
        while (number > 0) {
            byte index = (byte) (number % 64);
            stringBuilder.append(BASE64[index]);
            number /= 64;
        }
        while (stringBuilder.length() < keyLengthByBoardSize[size-2]) {
            stringBuilder.append('0');
        }

    }

    /**
     * Upon calling this method, the current GameState will be converted into a perfectly indefinably Key / ID.
     * @param gameState Since this is a static Method, the GameState must be provided by argument.
     * @return Key as a String.
     */
    public static String getKey(GameState gameState) {
        // Allocated to needed space beforehand. And use StringBuilder for better performance.
        StringBuilder stringBuilder = new StringBuilder(keyLengthByBoardSize[gameState.size] * 2);

        // Constructing the Key back to front. Because of that, we only need to use StringBuild.append() and not
        // StringBuilder.insert(). -> Better performance again.
        longBase10toStringBase64(stringBuilder, gameState.bitMapPlayer2, gameState.size);
        longBase10toStringBase64(stringBuilder, gameState.bitMapPlayer1, gameState.size);

        // Reversing the String because it was build back to front.
        return stringBuilder.reverse().toString();
    }

}

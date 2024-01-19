package main.java.de.gieskerb.tictactoe.model;

public class DebugPrinter {

    /**
     * Pointer to Board. Get access to package Vars like BitMaps and size
     */
    private final Board boardPointer;

    /**
     * Converts a long (in this case used as a Bitmap) into a binary string representation of that number.
     * Leading zeros will be added so that the length of String matches the size of bitmap.
     *
     * @param binary bitmap which will be converted into a string
     * @return binaryString
     */
    private String longToBinary(long binary) {
        StringBuilder binaryString = new StringBuilder(Long.toBinaryString(binary));
        while (binaryString.length() < this.boardPointer.size * this.boardPointer.size) {
            binaryString.insert(0, '0');
        }
        return binaryString.reverse().toString();
    }


    /**
     * Prints the pattern to the console. Not simply in one line, but in multiple aligned like a 2D table.
     *
     * @param patternString will be printed to console in a "good-looking" way
     */
    private void printBitMap(String patternString) {
        StringBuilder output = new StringBuilder((this.boardPointer.size + 1) * this.boardPointer.size);
        for (byte i = (byte) (this.boardPointer.size * this.boardPointer.size - 1); i >= 0; i--) {
            output.append(patternString.charAt(i));
            if (i % this.boardPointer.size == 0) {
                output.append('\n');
            }
        }
        System.out.print(output.toString() + '\n');
    }

    public DebugPrinter(Board corresponding) {
        this.boardPointer = corresponding;
    }

    public void printPatterns() {
        for (long pattern : boardPointer.bitMapPatterns) {
            String patternString = this.longToBinary(pattern);
            this.printBitMap(patternString);
        }
    }

    public void printBitMapP1() {
        String patternString = this.longToBinary(this.boardPointer.bitMapPlayer1);
        printBitMap(patternString);
    }

    public void printBitMapP2() {
        String patternString = this.longToBinary(this.boardPointer.bitMapPlayer2);
        printBitMap(patternString);
    }

}

package main.java.de.gieskerb.tictactoe.board;

public class WinningBitMapGenerator {

    private static long[] getHorizontalBitMaps(byte boardSize) {
        final long[] bitMaps = new long[boardSize];

        long bitMap = 0;
        for(byte i = 0; i< boardSize; i++) { // repeat until one row is complete
            bitMap <<= 1; // move it over by one
            bitMap |= 1; // add another one
        }

        for(byte i = 0; i< boardSize; i++) { // repeat until every row is covered
            bitMaps[i]=bitMap; // store bitmap of current row
            bitMap <<= boardSize; // move row one up
        }
        return bitMaps;
    }

    private static long[] getVerticalBitMaps(byte boardSize) {
        final long[] bitMaps = new long[boardSize];

        long bitMap = 0;
        for(byte i = 0; i< boardSize; i++) { // repeat until one column is complete
            bitMap <<= boardSize; // move it over by length of one row
            bitMap |= 1; // add another one
        }

        for(byte i = 0; i< boardSize; i++) { // repeat until every column is covered
            bitMaps[i]=bitMap; // store bitmap of current row
            bitMap <<= 1; // move colum one to the left
        }
        return bitMaps;
    }

    private static long[] getDiagonalBitMaps(byte boardSize) {
        final long[] bitMaps = new long[2];
        long bitMap = 0;
        for(byte i = 0; i< boardSize; i++) { // repeat until TL-BR diagonal is covered
            bitMap <<= boardSize + 1; // move it over by one more than length of row
            bitMap |= 1; // add on to BR part of board
        }
        bitMaps[0] = bitMap;

        final long bottomLeftTile =  1L << boardSize-1; // this represents BL tile on board
        bitMap = 0;
        for(byte i = 0; i< boardSize; i++) { // repeat until TR-BL diagonal is covered
            bitMap <<= boardSize - 1; // move it over by one less than length of row
            bitMap |=  bottomLeftTile; // add on to BL part of board
        }
        bitMaps[1] = bitMap;
        return bitMaps;
    }


    private static long[] createAllBitMaps(byte boardSize) {
        final long[] bitMaps = new long[boardSize + boardSize + 2];
        int i =0;
        long[] horizontalBitMaps = getHorizontalBitMaps(boardSize);
        long[] verticalBitMaps = getVerticalBitMaps(boardSize);
        long[] diagonalBitMaps = getDiagonalBitMaps(boardSize);

        for (long horizontalBitMap : horizontalBitMaps) {
            bitMaps[i++] = horizontalBitMap;
        }
        for (long verticalBitMap : verticalBitMaps) {
            bitMaps[i++] = verticalBitMap;
        }
        for (long diagonalBitMap : diagonalBitMaps) {
            bitMaps[i++] = diagonalBitMap;
        }

        return bitMaps;
    }

    private static final long[][] allBitMapsPerSize = new long[7][];

    public static long[] getBitMaps(int boardSize) {
        if(boardSize < 2 ||  boardSize > 8) {
            throw  new IllegalArgumentException("Board size must be between 2 and 8");
        }
        if (allBitMapsPerSize[boardSize-2] == null) {
            allBitMapsPerSize[boardSize-2] = createAllBitMaps((byte) boardSize);
        }
        return allBitMapsPerSize[boardSize - 2];
    }
}

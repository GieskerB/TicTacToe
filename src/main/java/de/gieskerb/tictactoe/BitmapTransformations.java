package main.java.de.gieskerb.tictactoe;

public class BitmapTransformations {

    private static final long[] HORIZONTAL_MASK = new long[]{
            0b0, 0b1, 0b11, 0b111, 0b1111, 0b11111, 0b111111, 0b1111111, 0b11111111};
    private static final long[] VERTICAL_MASK = new long[]{
            0b0, 0b1, 0b101, 0b1001001, 0b1000100010001, 0b100001000010000100001,
            0b1000001000001000001000001000001, 0b1000000100000010000001000000100000010000001L,
            0b100000001000000010000000100000001000000010000000100000001L};

    public enum Transformations {
        ORIGINAL, FLIPPED_HORIZONTAL, FLIPPED_VERTICAL, FLIPPED_BOTH,
        REVERSED, REVERSED_HORIZONTAL, REVERSED_VERTICAL, REVERSED_BOTH
    }

    public static long flipHorizontal(long bitmap, byte size) {
        final long mask = HORIZONTAL_MASK[size];
        long flipped = 0;
        for (byte i = 1; i <= size; i++) {
            flipped |= bitmap & mask;
            if (i != size) {
                flipped <<= size;
                bitmap >>= size;
            }
        }
        return flipped;
    }

    public static long flipVertical(long bitmap, byte size) {
        final long mask = VERTICAL_MASK[size];
        long flipped = 0;
        for (byte i = 1; i <= size; i++) {
            flipped |= bitmap & mask;
            if (i != size) {
                flipped <<= 1;
                bitmap >>= 1;
            }
        }
        return flipped;
    }

    public static long reverse(long bitmap, byte size) {
        long reversed = 0;
        for (byte i = 1; i <= size * size; i++) {
            reversed |= bitmap & 0b1;
            if (i != size * size) {
                bitmap >>= 1;
                reversed <<= 1;
            }
        }
        return reversed;
    }

    public static Pair<Long,Transformations>getMinimalBitmap(long originalBitmap, byte size) {

        long minBitMap = originalBitmap;
        Transformations transformation = Transformations.ORIGINAL;

        long hFlipped = flipHorizontal(originalBitmap, size);
        if(hFlipped < minBitMap) {
            transformation = Transformations.FLIPPED_HORIZONTAL;
            minBitMap = hFlipped;
        }
        long vFlipped = flipVertical(originalBitmap, size);
        if(vFlipped < minBitMap) {
            transformation = Transformations.FLIPPED_VERTICAL;
            minBitMap = vFlipped;
        }
        long hvFlipped = flipHorizontal(vFlipped, size);
        if(hvFlipped < minBitMap) {
            transformation = Transformations.FLIPPED_BOTH;
            minBitMap = hvFlipped;
        }

        long revOrig = reverse(originalBitmap, size);
        if(revOrig < minBitMap) {
            transformation = Transformations.REVERSED;
            minBitMap = revOrig;
        }
        long revHFlip = reverse(hFlipped, size);
        if(revHFlip < minBitMap) {
            transformation = Transformations.REVERSED_HORIZONTAL;
            minBitMap = revHFlip;
        }
        long revVFlip = reverse(vFlipped, size);
        if(revVFlip < minBitMap) {
            transformation = Transformations.REVERSED_VERTICAL;
            minBitMap = revVFlip;
        }
        long revVHFlip = reverse(hvFlipped, size);
        if(revVHFlip < minBitMap) {
            transformation = Transformations.REVERSED_BOTH;
            minBitMap = revVHFlip;
        }
        return new Pair<>(minBitMap, transformation);
    }
}

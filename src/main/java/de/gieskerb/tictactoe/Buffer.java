package main.java.de.gieskerb.tictactoe;

public class Buffer {

    private final static int[] primes = new int[]{63361, 30403, 45707, 88301, 90059, 27823, 27653, 80489};
    private final int mask;
    private final byte[] table;
    private final long[] playerOneBitmap;

    public Buffer(int size) {
        this.mask = (1 << size) - 1;
        this.table = new byte[1 << size];
        this.playerOneBitmap = new long[1 << size];
    }

    private static Pair<Long, Long> getMinimalBitmapPair(long bitmapOne, long bitmapTwo, byte size) {
        Pair<Long, BitmapTransformations.Transformations> minimalBitmapCalculation = BitmapTransformations.getMinimalBitmap(bitmapOne, size);

        switch (minimalBitmapCalculation.second) {
            case ORIGINAL -> {
                // Keep it as it is!
            }
            case FLIPPED_HORIZONTAL -> {
                bitmapTwo = BitmapTransformations.flipHorizontal(bitmapTwo, size);
            }
            case FLIPPED_VERTICAL -> {
                bitmapTwo = BitmapTransformations.flipVertical(bitmapTwo, size);
            }
            case FLIPPED_BOTH -> {
                bitmapTwo = BitmapTransformations.flipHorizontal(BitmapTransformations.flipVertical(bitmapTwo, size), size);
            }
            case REVERSED -> {
                bitmapTwo = BitmapTransformations.reverse(bitmapTwo, size);
            }
            case REVERSED_HORIZONTAL -> {
                bitmapTwo = BitmapTransformations.reverse(BitmapTransformations.flipHorizontal(bitmapTwo, size), size);
            }
            case REVERSED_VERTICAL -> {
                bitmapTwo = BitmapTransformations.reverse(BitmapTransformations.flipVertical(bitmapTwo, size), size);
            }
            case REVERSED_BOTH -> {
                bitmapTwo = BitmapTransformations.reverse(BitmapTransformations.flipHorizontal(BitmapTransformations.flipVertical(bitmapTwo, size), size), size);
            }
        }
        return new Pair<>(bitmapOne, bitmapTwo);
    }

    private int getHashKey(long bitmapOne, long bitmapTwo, byte size) {
        var pair = Buffer.getMinimalBitmapPair(bitmapOne, bitmapTwo, size);

        long octet1 = pair.first & 0xFFFF;
        long octet2 = (pair.first >> 16) & 0xFFFF;
        long octet3 = (pair.first >> 32) & 0xFFFF;
        long octet4 = (pair.first >> 48) & 0xFFFF;
        long octet5 = pair.second & 0xFFFF;
        long octet6 = (pair.second >> 16) & 0xFFFF;
        long octet7 = (pair.second >> 32) & 0xFFFF;
        long octet8 = (pair.second >> 48) & 0xFFFF;

        octet1 *= primes[0];
        octet2 *= primes[1];
        octet3 *= primes[2];
        octet4 *= primes[3];
        octet5 *= primes[4];
        octet6 *= primes[5];
        octet7 *= primes[6];
        octet8 *= primes[7];

//        return (short) ((bitmapOne ^ (bitmapTwo << 1 | bitmapTwo >> (size - 1))) & this.mask);
        return (int) ((octet1 ^ octet2 ^ octet3 ^ octet4 ^ octet5 ^ octet6 ^ octet7 ^ octet8) & this.mask);
    }

    public boolean contains(long bitmapOne, long bitmapTwo, byte size) {
        var pair = Buffer.getMinimalBitmapPair(bitmapOne, bitmapTwo, size);
        final int hashKey = getHashKey(pair.first, pair.second, size);

        return pair.first == this.playerOneBitmap[hashKey];

    }

    public void setValue(long bitmapOne, long bitmapTwo, byte size, byte value) {
        var pair = Buffer.getMinimalBitmapPair(bitmapOne, bitmapTwo, size);
        final int hashKey = getHashKey(pair.first, pair.second, size);

        this.table[hashKey] = value;
        this.playerOneBitmap[hashKey] = bitmapOne;
    }

    public byte getValue(long bitmapOne, long bitmapTwo, byte size) {
        var pair = Buffer.getMinimalBitmapPair(bitmapOne, bitmapTwo, size);
        final int hashKey = getHashKey(pair.first, pair.second, size);

        return this.table[hashKey];
    }
}

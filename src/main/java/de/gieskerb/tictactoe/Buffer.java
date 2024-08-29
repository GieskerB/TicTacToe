package main.java.de.gieskerb.tictactoe;

public class Buffer {

    private final int mask;
    private final byte[] table;
    private final long[] playerOneBitmap;

    private final int[] primes = new int[]{63361, 30403, 45707, 88301, 90059, 27823, 27653, 80489};

    public Buffer(int size) {
        this.mask = (1 << size) - 1;
        this.table = new byte[1 << size];
        this.playerOneBitmap = new long[1 << size];
    }

    public int createHashKey(long bitmapOne, long bitmapTwo, byte size) {
//        return (short) ((bitmapOne ^ (bitmapTwo << 1 | bitmapTwo >> (size - 1))) & this.mask);
        long octet1 = bitmapOne & 0xFFFF;
        long octet2 = (bitmapOne >> 16) & 0xFFFF;
        long octet3 = (bitmapOne >> 32) & 0xFFFF;
        long octet4 = (bitmapOne >> 48) & 0xFFFF;
        long octet5 = bitmapTwo & 0xFFFF;
        long octet6 = (bitmapTwo >> 16) & 0xFFFF;
        long octet7 = (bitmapTwo >> 32) & 0xFFFF;
        long octet8 = (bitmapTwo >> 48) & 0xFFFF;

        octet1 *= primes[0];
        octet2 *= primes[1];
        octet3 *= primes[2];
        octet4 *= primes[3];
        octet5 *= primes[4];
        octet6 *= primes[5];
        octet7 *= primes[6];
        octet8 *= primes[7];

        return (int) ((octet1 ^ octet2 ^ octet3 ^ octet4 ^ octet5 ^ octet6 ^ octet7 ^ octet8) & this.mask);
    }

    public void set(int key, byte value, long bitmapOne) {
        this.table[key] = value;
        this.playerOneBitmap[key] = bitmapOne;
    }

    public byte getValue(int key) {
        return this.table[key];
    }

    public long getBitmap(int key) {
        return this.playerOneBitmap[key];
    }

}

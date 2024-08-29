package main.java.de.gieskerb.tictactoe;

    public class BitmapTransformations {

        public static long rotate90Degrees(long bitmap, byte size) {
            long mask = (1L << (size * size)) - 1;
            long rotated = 0;
            for (long i = 0; i < size; i++) {
                rotated |= (bitmap & (mask << (i * size))) >>> (size * (size - i - 1));
                rotated <<= size;
            }
            return rotated >>> size;
        }

        public static long rotate180Degrees(long bitmap, byte size) {
            return Long.reverse(bitmap) >>> (Long.SIZE - (size * size));
        }


        public static long rotate270Degrees(long bitmap, byte size) {
            long mask = (1L << (size * size)) - 1;
            long rotated = 0;
            for (long i = 0; i < size; i++) {
                rotated |= (bitmap & (mask << (i * size))) << (i * size + 1);
                rotated >>>= size;
            }
            return rotated << size;
        }

        public static long flipHorizontal(long bitmap, byte size) {
            long mask = (1L << (size * size)) - 1;
            long flipped = 0;
            for (long i = 0; i < size; i++) {
                flipped |= (bitmap & (mask << (i * size))) >>> (i * size + size - 1);
                flipped <<= size;
            }
            return flipped >>> size;
        }

        public static long flipVertical(long bitmap, byte size) {
            long mask = (1L << (size * size)) - 1;
            long flipped = 0;
            for (long i = 0; i < size; i++) {
                flipped |= (bitmap & (mask << (i * size))) >>> (size - i - 1);
                flipped <<= size;
            }
            return flipped >>> size;
        }
    }

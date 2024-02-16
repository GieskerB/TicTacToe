package main.java.de.gieskerb.tictactoe.util;

public class Pair<T1, T2> {

    public T1 first;
    public T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        return this.first.hashCode() * this.second.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair<?, ?> temp)) {
            return false;
        }
        return this.first.equals(temp.first) && this.second.equals(temp.second);
    }

    @Override
    public String toString() {
        return "{First: "+ this.first.toString() + " Second: " + this.second.toString() + "}";
    }
}

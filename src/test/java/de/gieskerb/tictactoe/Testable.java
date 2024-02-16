package test.java.de.gieskerb.tictactoe;

/**
 * This class in combination with the interface "FriendTestAccess" tries to recreate the
 * friend relationship between classes like C.
 * Every class implementing this interface has to provide a method for reacting to the
 * FriendTestAccess object. Call the method and also return the value that method returned.
 * Reason being to test the package private Methods via JUnit Test. By knowing
 * exactly the name and argument list of a method one can call even those.
 * IDEA: <a href="https://stackoverflow.com/questions/182278/is-there-a-way-to-simulate-the-c-friend-concept-in-java">...</a>
 */
public interface Testable {

    /**
     *
     * @param fta object contains name and arguments for package private Methods.
     * @return value from the calling of the determent method
     */
    Object invokeMethod(FriendTestAccess fta);

}

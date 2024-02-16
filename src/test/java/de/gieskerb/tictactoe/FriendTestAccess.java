package test.java.de.gieskerb.tictactoe;

/**
 * This class in combination with the interface "Testable" tries to recreate the
 * friend relationship between classes like C.
 * This class is public but only has a package private constructor. This ensures,
 * that only test-classes from within this package can instantiate an object of typ
 * FriendTestAccess.
 * Reason being to test the package private Methods via JUnit Test. By knowing
 * exactly the name and argument list of a method one can call even those.
 * IDEA: <a href="https://stackoverflow.com/questions/182278/is-there-a-way-to-simulate-the-c-friend-concept-in-java">...</a>
 */
public class FriendTestAccess {

    /**
     * Exact name of the method. Character for character.
     */
    private final String methodName;

    /**
     * List of arguments provided to call the method. Order is important, obviously.
     */
    private final Object[] args;

    /**
     * As mentioned above: Constructor only package private for junit test access only!
     * @param methodName of the method that should be called.
     * @param args for the method
     */
    FriendTestAccess(String methodName, Object... args) {
        this.methodName=  methodName;
        this.args = args;
    }

    /**
     * @return the method name.
     */
    public String getMethodName() {
        return this.methodName;
    }

    /**
     * @return the arguments.
     */
    public Object getArg(int index) {
        return  this.args[index];
    }

    /**
     * @return the number of arguments.
     */
    public int argsLength() {
        return this.args.length;
    }

}

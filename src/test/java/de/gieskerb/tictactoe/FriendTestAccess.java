package test.java.de.gieskerb.tictactoe;

public class FriendTestAccess {

    private final String methodName;

    private final Object[] args;

    FriendTestAccess(String methodName, Object... args) {
        this.methodName=  methodName;
        this.args = args;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public Object getArg(int index) {
        return  this.args[index];
    }

    public int argsLength() {
        return this.args.length;
    }

}

package exceptions;

public class InvalidCardCode extends Exception {
    public InvalidCardCode(String message) {
        super(message);
    }
}

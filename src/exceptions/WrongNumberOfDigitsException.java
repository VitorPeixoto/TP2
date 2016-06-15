package exceptions;

/**
 * Created by Peixoto on 15/06/2016.
 */
public class WrongNumberOfDigitsException extends Exception {
    public WrongNumberOfDigitsException() {}

    public WrongNumberOfDigitsException(String message) {
        super(message);
    }
}

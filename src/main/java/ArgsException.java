public class ArgsException extends RuntimeException{
    private String message;

    public ArgsException() {
    }

    public ArgsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}

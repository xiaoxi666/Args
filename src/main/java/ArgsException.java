public class ArgsException extends RuntimeException{
    private String message;
    private ErrorCode errorCode;

    public ArgsException(String message) {
        this.message = message;
    }

    public ArgsException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message == null ? errorCode.getMessage() : message;
    }

    protected enum ErrorCode {
        MISS_VALUE("Miss value"),
        INVALID_VALUE("Invalid value"),
        EXPLICIT_ASSIGNMENT("Explicit assignment");

        private String message;

        ErrorCode(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}

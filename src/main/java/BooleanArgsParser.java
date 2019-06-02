public class BooleanArgsParser implements ArgsParser {
    private Boolean value = false;

    @Override
    public void setValue(String arg) throws ArgsException{
        if (arg != null) {
            throw new ArgsException(ArgsException.ErrorCode.EXPLICIT_ASSIGNMENT);
        }
        this.value = true;
    }

    @Override
    public Boolean getValue() {
        return this.value;
    }
}

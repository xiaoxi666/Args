public class StringArgsParser<T> implements ArgsParser {
    private String value = "";

    @Override
    public void setValue(String arg) throws ArgsException{
        if (arg == null) {
            throw new ArgsException(ArgsException.ErrorCode.MISS_VALUE);
        }
        this.value = arg;
    }

    @Override
    public String getValue() {
        return value;
    }
}

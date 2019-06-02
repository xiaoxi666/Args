public class IntegerArgsParser<T> implements ArgsParser {
    private Integer value = 0;

    @Override
    public void setValue(String arg) throws ArgsException{
        if (arg == null) {
            throw new ArgsException(ArgsException.ErrorCode.MISS_VALUE);
        }
        if (arg.startsWith("-")) {
            throw new ArgsException(ArgsException.ErrorCode.INVALID_VALUE);
        }
        try {
            this.value = Integer.valueOf(arg);
        } catch (NumberFormatException e) {
            throw new ArgsException(ArgsException.ErrorCode.INVALID_VALUE);
        }
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}

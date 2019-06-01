public class IntegerArgsParser<T> implements ArgsParser {
    private Integer value = 0;

    @Override
    public void setValue(String arg) throws ArgsException{
        if (arg == null) {
            throw new ArgsException("Flag for int type: Miss value.");
        }
        try {
            this.value = Integer.valueOf(arg);
        } catch (NumberFormatException e) {
            throw new ArgsException("Flag for int type: Invalid value.");
        }
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}

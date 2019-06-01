public class StringArgsParser<T> implements ArgsParser {
    private String value = "";

    @Override
    public void setValue(String arg) {
        if (arg == null) {
            throw new ArgsException("Flag for String type: Miss value.");
        }
        this.value = arg;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}

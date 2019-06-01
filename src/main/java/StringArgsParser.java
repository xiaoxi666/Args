public class StringArgsParser<T> implements ArgsParser {
    private String value = "";

    @Override
    public void setValue(String flagArg) {
        String[] flagAndArg = flagArg.split(" ");
        try {
            this.value = flagAndArg[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArgsException("Flag for String type: Miss value.");
        }
    }

    @Override
    public String getValue() {
        return this.value;
    }
}

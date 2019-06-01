public class BooleanArgsParser implements ArgsParser {
    private Boolean value = false;

    @Override
    public void setValue(String arg) throws ArgsException{
        if (arg != null) {
            throw new ArgsException("Flag for boolean type: Explicit assignment is prohibited.");
        }
        this.value = true;
    }

    @Override
    public Boolean getValue() {
        return this.value;
    }
}

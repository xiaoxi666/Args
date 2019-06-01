public class IntegerArgsParser<T> implements ArgsParser {
    private Integer value = 0;

    @Override
    public void setValue(String flagArg) throws ArgsException{
        String[] flagAndArg = flagArg.split(" ");
        try {
            this.value = Integer.valueOf(flagAndArg[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArgsException("Flag for int type: Miss value.");
        }
        catch (NumberFormatException e) {
            throw new ArgsException("Flag for int type: Invalid value.");
        }
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}

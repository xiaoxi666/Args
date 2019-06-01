import com.sun.org.apache.xpath.internal.operations.Bool;

public class BooleanArgsParser implements ArgsParser {
    private Boolean value = false;

    @Override
    public void setValue(String flagArg) throws ArgsException{
        String[] flagAndArg = flagArg.split(" ");
        if (flagAndArg.length == 1) {
            this.value = true;
        } else {
            throw new ArgsException("Flag for boolean type: Explicit assignment is prohibited.");
        }
    }

    @Override
    public Boolean getValue() {
        return this.value;
    }
}

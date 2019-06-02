import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringListArgsParser implements ArgsParser{
    private List<String> value = Collections.emptyList();

    @Override
    public void setValue(String arg) throws ArgsException {
        this.value = Arrays.asList(arg.split(","));
    }

    @Override
    public List<String> getValue() {
        return value;
    }
}

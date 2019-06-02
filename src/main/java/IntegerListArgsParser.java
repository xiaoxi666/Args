import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IntegerListArgsParser implements ArgsParser{
    private List<Integer> value = Collections.emptyList();

    @Override
    public void setValue(String arg) throws ArgsException {
        List<String> strings = Arrays.asList(arg.split(","));
        try {
            this.value = strings.stream().map(Integer::valueOf).collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new ArgsException(ArgsException.ErrorCode.INVALID_VALUE);
        }
    }

    @Override
    public List<Integer> getValue() {
        return value;
    }
}

public interface ArgsParser {
    void setValue(String flagArg) throws ArgsException;
    <T> T getValue();
}

public interface ArgsParser {
    void setValue(String arg) throws ArgsException;
    <T> T getValue();
}

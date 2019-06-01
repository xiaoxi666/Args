import org.junit.Assert;
import org.junit.Test;

public class ParserTest {
    private void parseAndVerify(String params, String query, String expected) {
        Parser parser = new Parser(params);
        Assert.assertEquals(expected, parser.query(query));
    }
    
    @Test
    public void should_return_default_false_with_flag_l() {
        parseAndVerify("-l", "-l", String.valueOf(true));
    }

    @Test
    public void should_return_actual_value_without_flag_l() {
        parseAndVerify("", "-l", String.valueOf(false));
    }

    @Test
    public void should_return_default_zero_without_flag_p() {
        parseAndVerify("-p", "-p", String.valueOf(0));
    }

    @Test
    public void should_return_actual_port_with_flag_p() {
        parseAndVerify("-p 8080", "-p", String.valueOf(8080));
    }

    @Test
    public void should_return_default_empty_without_flag_d() {
        parseAndVerify("-d ", "-d", "");
    }

    @Test
    public void should_return_actual_dir_with_flag_d() {
        parseAndVerify("-d /usr/logs", "-d", "/usr/logs");
    }
    
    @Test
    public void should_return_error_info_with_nonexist_flag() {
        parseAndVerify("-d /usr/logs", "-e", "NonExist flag.");
    }
}

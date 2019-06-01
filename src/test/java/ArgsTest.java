import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ArgsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldBeAbleToParseSchema() {
        thrown.expect(ArgsException.class);
        thrown.expectMessage("Schema definition not support: Lack specified type.");
        Args args = new Args("l:boolean p:int d:String x:other", "");
    }

    @Test
    public void shouldReturnDefaultValue() {
        Args args = new Args("l:boolean p:int d:String", "");
        Assert.assertEquals(false, args.get("l"));
        Assert.assertEquals(0, (int)args.get("p"));
        Assert.assertEquals("", args.get("d"));
    }

    @Test
    public void shouldReturnActualValue() {
        Args args = new Args("l:boolean p:int d:String", "-l -p 8080 -d /usr/logs");
        Assert.assertEquals(true, args.get("l"));
        Assert.assertEquals(8080, (int)args.get("p"));
        Assert.assertEquals("/usr/logs", args.get("d"));
    }

    @Test
    public void combinedCase0() {
        Args args = new Args("l:boolean p:int d:String", "-p 8080 -d /usr/logs");
        Assert.assertEquals(false, args.get("l"));
        Assert.assertEquals(8080, (int)args.get("p"));
        Assert.assertEquals("/usr/logs", args.get("d"));
    }

    @Test
    public void combinedCase1() {
        Args args = new Args("l:boolean p:int d:String", "-d /usr/logs");
        Assert.assertEquals(false, args.get("l"));
        Assert.assertEquals(0, (int)args.get("p"));
        Assert.assertEquals("/usr/logs", args.get("d"));
    }

    @Test
    public void combinedCase2() {
        Args args = new Args("l:boolean p:int d:String", "-l -d /usr/logs");
        Assert.assertEquals(true, args.get("l"));
        Assert.assertEquals(0, (int)args.get("p"));
        Assert.assertEquals("/usr/logs", args.get("d"));
    }

    @Test
    public void shouldThrowExceptionForBooleanAssignment() {
        thrown.expect(ArgsException.class);
        thrown.expectMessage("Flag for boolean type: Explicit assignment is prohibited.");
        Args args = new Args("l:boolean p:int d:String", "-l true");
    }

    @Test
    public void shouldThrowExceptionForIntegerMissValue() {
        thrown.expect(ArgsException.class);
        thrown.expectMessage("Flag for int type: Miss value.");
        Args args = new Args("l:boolean p:int d:String", "-l -p");
    }

    @Test
    public void shouldThrowExceptionForIntegerInvalidValue() {
        thrown.expect(ArgsException.class);
        thrown.expectMessage("Flag for int type: Invalid value.");
        Args args = new Args("l:boolean p:int d:String", "-l -p /usr/logs");
    }

    @Test
    public void shouldThrowExceptionForStringMissValue() {
        thrown.expect(ArgsException.class);
        thrown.expectMessage("Flag for String type: Miss value.");
        Args args = new Args("l:boolean p:int d:String", "-l -p 8080 -d");
    }

}

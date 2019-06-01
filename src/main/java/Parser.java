import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private static List<Schema> schemas;

    public Parser(String params) {
        schemas = new ArrayList<>();
        parse(params);
    }

    public void parse(String params) {
        List<String> exps = Arrays.asList(params.split(" "));
        Schema schema = new Schema();
        for (String exp : exps) {
            if (!exp.startsWith("-")) {
                schema.setValue(exp);
                continue;
            }
            schema.setFlag(exp);
            if ("-l".equals(exp)) {
                schema.setValue(String.valueOf(true));
            }
            schemas.add(schema);
        }
        setDefaultValue();
    }

    private static void setDefaultValue() {
        for (Schema schema : schemas) {
            if ("-p".equals(schema.getFlag())) {
                schema.setDefaultValue(String.valueOf(0));
            }
            if ("-d".equals(schema.getFlag())) {
                schema.setDefaultValue("");
            }
        }
    }

    @Data
    static class Schema {
        String flag;
        String value = null;
        String defaultValue;
    }

    public static String query(String flag) {
        for (Schema schema : schemas) {
            if (flag.equals(schema.getFlag())) {
                return schema.getValue() == null ? schema.getDefaultValue() : schema.getValue();
            }
        }
        if ("-l".equals(flag)) {
            return String.valueOf(false);
        }
        return "NonExist flag.";
    }
}

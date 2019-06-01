import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Args {
    private  Map<String, Class> typeParserMap =
        new ImmutableMap.Builder<String, Class>()
             .put("boolean", BooleanArgsParser.class)
             .put("int", IntegerArgsParser.class)
             .put("String", StringArgsParser.class)
             .build();

    private Map<String, ArgsParser> flagParserMap = Maps.newHashMap();

    public Args(String schema, String args) {
        parseSchema(schema);
        parseArgs(args);
    }

    private void parseSchema(String schema) throws ArgsException {
        List<String> schemaRules = Arrays.asList(schema.split(" "));
        schemaRules.stream().forEach(sr -> {
            String[] schemaRule = sr.split(":");
            try {
                flagParserMap.put(schemaRule[0], (ArgsParser) typeParserMap.get(schemaRule[1]).newInstance());
            } catch (NullPointerException e) {
                throw new ArgsException("Schema definition not support: Lack specified type.");
            } catch (InstantiationException | IllegalAccessException e) {
                throw new ArgsException("Inner Error.");
            }
        });
    }

    private void parseArgs(String args) {
        List<String> flagArgs = Arrays.asList(args.split("-"));
        flagArgs.stream()
            .filter(flagArg -> !flagArg.isEmpty())
            .map(flagArg -> flagArg.trim())
            .forEach(flagArg -> parseArg(flagArg));
    }

    private void parseArg(String flagArg) {
        flagParserMap.get(flagArg.split(" ")[0]).setValue(flagArg);
    }

    public <T> T get(String flag) {
        return flagParserMap.get(flag).getValue();
    }

}

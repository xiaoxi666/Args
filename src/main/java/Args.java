import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Args {
    private static final String COLON = ":";
    private static final String SPACE = " ";
    private static final String DASH = "-";

    private static final Map<String, Class> TYPE_PARSER_MAP =
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
        List<String> schemaRules = Arrays.asList(schema.split(SPACE));
        schemaRules.stream().forEach(sr -> {
            String[] schemaRule = sr.split(COLON);
            try {
                flagParserMap.put(schemaRule[0], (ArgsParser) TYPE_PARSER_MAP.get(schemaRule[1]).newInstance());
            } catch (NullPointerException e) {
                throw new ArgsException("Schema definition not support: Lack specified type.");
            } catch (InstantiationException | IllegalAccessException e) {
                throw new ArgsException("Inner Error.");
            }
        });
    }

    private void parseArgs(String args) {
        List<String> flagArgs = Arrays.asList(args.split(DASH));
        flagArgs.stream()
            .filter(flagArg -> !flagArg.isEmpty())
            .map(flagArg -> flagArg.trim())
            .forEach(flagArg -> parseArg(flagArg));
    }

    private void parseArg(String flagArg) {
        if (flagArg.isEmpty()) {
            throw new ArgsException("Miss flag.");
        }
        String[] flagAndArg = flagArg.split(SPACE);
        if (flagAndArg.length > 2) {
            throw new ArgsException("Flag cannot have multi separate params.");
        }
        String flag = flagAndArg[0];
        String arg = flagAndArg.length == 1 ? null : flagAndArg[1];
        flagParserMap.get(flag).setValue(arg);
    }

    public <T> T get(String flag) {
        return flagParserMap.get(flag).getValue();
    }

}

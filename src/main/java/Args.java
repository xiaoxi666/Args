import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class Args {
    private static final String COLON = ":";
    private static final String SPACE = " ";
    private static final String DASH = "-";
    private static final String EMPTY = "";

    // schame类型关联定义
    private static final Map<String, Class> TYPE_PARSER_MAP =
        new ImmutableMap.Builder<String, Class>()
             .put("boolean", BooleanArgsParser.class)
             .put("int", IntegerArgsParser.class)
             .put("String", StringArgsParser.class)
             .put("[String]", StringListArgsParser.class)
             .put("[int]", IntegerListArgsParser.class)
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
        List<String> flagArgs = correctArgWithNegativeNum(Arrays.asList(args.split(DASH)));
        flagArgs.stream()
            .filter(flagArg -> !flagArg.isEmpty())
            .map(flagArg -> flagArg.trim())
            .forEach(flagArg -> parseArg(flagArg));
    }

    /**
     * 参数直接用"-"分割，负数也会被分割，因此需要纠正。若无需支持负数，可省略此步骤
     * @param flagArgs
     * @return
     */
    private List<String> correctArgWithNegativeNum(List<String> flagArgs) {
        List<String> flagArgsAfterCorrect = Lists.newArrayList();
        for (String flagArg : flagArgs) {
            flagArgsAfterCorrect.add(flagArg);
            if (flagArg.length() < 2) {
                continue;
            }
            char ch = 0;
            if ((((ch = flagArg.charAt(0)) - '0') | ('9' - ch)) >= 0) {
                StringJoiner sj = new StringJoiner(DASH, EMPTY, EMPTY);
                flagArgsAfterCorrect.stream().skip(flagArgsAfterCorrect.size() - 2).forEach(f -> sj.add(f));
                flagArgsAfterCorrect = flagArgsAfterCorrect.subList(0, flagArgsAfterCorrect.size() - 2);
                flagArgsAfterCorrect.add(sj.toString());
            }
        }
        return flagArgsAfterCorrect;
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
        try {
            flagParserMap.get(flag).setValue(arg);
        } catch (ArgsException e) {
            throw new ArgsException(
                String.format("Parse error(%s %s), Cause by: %s", flag, arg, e.getMessage()));
        }
    }

    public <T> T get(String flag) {
        return flagParserMap.get(flag).getValue();
    }
}

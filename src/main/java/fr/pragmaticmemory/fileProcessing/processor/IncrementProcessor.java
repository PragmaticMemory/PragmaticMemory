package fr.pragmaticmemory.fileProcessing.processor;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class IncrementProcessor extends IndependentLineProcessor {
    static final int DEFAULT_STEP = 1;
    private final Pattern defaultPattern = Pattern.compile("\\d+");
    private final Integer step;
    private Pattern specificPattern;


    public IncrementProcessor(Integer step) {
        this.step = step;
    }


    public IncrementProcessor(Integer step, String specificPattern) {
        this(step);
        this.specificPattern = Pattern.compile(specificPattern);
    }


    public IncrementProcessor() {
        this(DEFAULT_STEP);
    }


    @Override
    protected String processLine(String line) {
        if (specificPattern == null) {
            return incrementAll(line);
        }
        return incrementOnlyPattern(line);
    }


    private String incrementOnlyPattern(String line) {
        final Matcher matcher = specificPattern.matcher(line);
        final StringBuilder newLineBuilder = new StringBuilder();
        int firstIndex = 0;
        while (matcher.find()) {
            final int startIndex = matcher.start();
            final int endIndex = matcher.end();
            newLineBuilder.append(line.substring(firstIndex, startIndex));
            newLineBuilder.append(incrementAll(line.substring(startIndex, endIndex)));
            firstIndex = endIndex;
        }
        newLineBuilder.append(line.substring(firstIndex, line.length()));
        return newLineBuilder.toString();
    }


    private String incrementAll(String line) {
        final Matcher matcher = defaultPattern.matcher(line);
        final StringBuilder newLineBuilder = new StringBuilder();
        int firstIndex = 0;
        while (matcher.find()) {
            final int startIndex = matcher.start();
            final int endIndex = matcher.end();
            newLineBuilder.append(line.substring(firstIndex, startIndex));
            newLineBuilder.append(incrementIntegerString(line.substring(startIndex, endIndex)));
            firstIndex = endIndex;
        }
        newLineBuilder.append(line.substring(firstIndex, line.length()));
        return newLineBuilder.toString();
    }


    private String incrementIntegerString(String input) {
        final BigInteger bigInteger = new BigInteger(input);
        final BigInteger newbigInteger = bigInteger.add(new BigInteger(String.valueOf(step)));
        return newbigInteger.toString();
    }
}

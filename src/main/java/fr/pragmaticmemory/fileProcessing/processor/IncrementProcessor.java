package fr.pragmaticmemory.fileProcessing.processor;

import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class IncrementProcessor extends IndependentLineProcessor {
    static final int DEFAULT_STEP = 1;
    private final Pattern simplePattern = Pattern.compile("\\d+");
    private final Integer step;
    private Pattern specificPattern;


    public IncrementProcessor(RouteProvider routeProvider, Integer step) {
        super(routeProvider);
        this.step = step;
    }


    public IncrementProcessor(RouteProvider routeProvider, Integer step, String specificPattern) {
        this(routeProvider, step);
        this.specificPattern = Pattern.compile(specificPattern);
    }


    public IncrementProcessor(RouteProvider routeProvider) {
        this(routeProvider, DEFAULT_STEP);
    }


    @Override
    protected String processLine(String line) {
        if (specificPattern == null) {
            return fullIncrement(line);
        }
        return partialIncrement(line);
    }


    private String partialIncrement(String line) {
        final Matcher matcher = specificPattern.matcher(line);
        final StringBuilder newLineBuilder = new StringBuilder();
        int firstIndex = 0;
        while (matcher.find()) {
            final int startIndex = matcher.start();
            final int endIndex = matcher.end();
            newLineBuilder.append(line.substring(firstIndex, startIndex));
            newLineBuilder.append(fullIncrement(line.substring(startIndex, endIndex)));
            firstIndex = endIndex;
        }
        newLineBuilder.append(line.substring(firstIndex, line.length()));
        return newLineBuilder.toString();
    }


    private String fullIncrement(String line) {
        final Matcher matcher = simplePattern.matcher(line);
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

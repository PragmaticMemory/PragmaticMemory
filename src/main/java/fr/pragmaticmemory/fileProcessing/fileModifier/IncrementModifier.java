package fr.pragmaticmemory.fileProcessing.fileModifier;

import fr.pragmaticmemory.fileProcessing.fileProvider.RouteProvider;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class IncrementModifier extends IndependentLineModifier {
    Pattern pattern = Pattern.compile("\\d+");
    private Integer step;


    public IncrementModifier(RouteProvider sourceTargetProvider, Integer step) {
        super(sourceTargetProvider);
        this.step = step;
    }


    public IncrementModifier(RouteProvider sourceTargetProvider) {
        this(sourceTargetProvider, 1);
    }


    @Override
    protected String processLine(String line) {
        Matcher matcher = pattern.matcher(line);
        int firstIndex = 0;
        StringBuilder newLineBuilder = new StringBuilder();
        while (matcher.find()) {
            final int startIndex = matcher.start();
            final int endIndex = matcher.end();
            newLineBuilder.append(line.substring(firstIndex, startIndex));
            final int oldInteger = Integer.parseInt(line.substring(startIndex, endIndex));
            newLineBuilder.append(oldInteger + step);
            firstIndex = endIndex;
        }
        newLineBuilder.append(line.substring(firstIndex, line.length()));
        return newLineBuilder.toString();
    }
}

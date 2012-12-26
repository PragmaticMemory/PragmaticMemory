package fr.pragmaticmemory.fileProcessing.processor;

import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegExpProcessor extends IndependentLineProcessor {

    private final String sourceRegExp;
    private final String replacementRegExp;


    public RegExpProcessor(RouteProvider routeProvider, String sourceRegExp, String replacementRegExp) {
        super(routeProvider);
        this.sourceRegExp = sourceRegExp;
        this.replacementRegExp = replacementRegExp;
    }


    @Override
    protected String processLine(String line) {
        final Pattern pattern = Pattern.compile(sourceRegExp);
        final Matcher matcher = pattern.matcher(line);
        return matcher.replaceAll(replacementRegExp);
    }
}

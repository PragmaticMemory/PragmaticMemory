package fr.pragmaticmemory.fileProcessing.processor;

import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegExpProcessor extends IndependentLineProcessor {

    private String sourceRegExp;
    private String replacementRegExp;


    public RegExpProcessor(RouteProvider routeProvider, String sourceRegExp, String replacementRegExp) {
        super(routeProvider);
        this.sourceRegExp = sourceRegExp;
        this.replacementRegExp = replacementRegExp;
    }


    @Override
    protected String processLine(String line) {
        Pattern pattern = Pattern.compile(sourceRegExp);
        Matcher matcher = pattern.matcher(line);
//        if (matcher.groupCount() > 1) {
//            throw new Exception("Erreur : plus d'une occurrence de modelIndex sur la même ligne");
//        }
        return matcher.replaceAll(replacementRegExp);
    }
}

package fr.pragmaticmemory.fileProcessing.fileModifier;

import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegExpFileModifier extends IndependentLineFileModifier {

    private String sourceRegExp;
    private String replacementRegExp;


    public RegExpFileModifier(FileProvider fileProvider, String sourceRegExp, String replacementRegExp) {
        super(fileProvider);
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

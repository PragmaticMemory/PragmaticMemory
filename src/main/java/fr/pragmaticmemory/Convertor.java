package fr.pragmaticmemory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Convertor {

    private final static String SPACE = " ";
    static int indentLevel = 2;
    static final String OPENING_TARGET = "<";
    static final String CLOSING_TAG = ">";
    static final String OPENING_TAG_ENTITY = "&openTag;";
    static final String CLOSING_TAG_ENTITY = "&closeTag;";
    static String oneLevelIndent;


    public static void main(String[] args) throws IOException {
        if (args.length < 3 || (!"code2xml".equals(args[0]) && !"xml2code".equals(args[0]))) {
            String helpMessage = "usage : \n"
                                 + "premier argument : type de conversion (\"code2xml\" ou \"xml2code\") \n"
                                 + "second argument : fichier d'entree\n"
                                 + "troisieme argument : fichier de sortie\n"
                                 + "quatrieme argument (optionel): nombre d'espaces pour un niveau d'indentation (2 par defaut)";
            System.out.println(helpMessage);
            return;
        }

        if (args.length > 3) {
            try {
                indentLevel = Integer.parseInt(args[3]);
            }
            catch (NumberFormatException e) {
                System.out.println("ERREUR :Le quatrieme argument doit etre un entier");
                return;
            }
        }

        oneLevelIndent = "";
        for (int i = 0; i < indentLevel; i++) {
            oneLevelIndent += SPACE;
        }

        for (String arg : args) {
            System.out.println("arg = " + arg);
        }

        String inputFilePath = args[1];
        String outputFilePath = args[2];
        List<String> outputLines = new ArrayList<String>();
        List<String> inputLines = FileUtils.readLines(inputFilePath);
        if ("code2xml".equals(args[0])) {
            Pattern pattern = Pattern.compile("(\\s*)\\.*");
            for (String inputLine : inputLines) {
                Matcher matcher = pattern.matcher(inputLine);
                matcher.find();
                String spaces = matcher.group(1);
                int level = getLevel(spaces);
                String outputLine = inputLine.replace(OPENING_TARGET, OPENING_TAG_ENTITY).replace(CLOSING_TAG,
                                                                                                  CLOSING_TAG_ENTITY);
                outputLine = outputLine.trim();
                outputLine = "<codeLine level=\"" + level + "\">" + outputLine + "</codeLine>";
                outputLines.add(outputLine);
            }
        }
        else if ("xml2code".equals(args[0])) {
            Pattern pattern = Pattern.compile("<codeLine\\s+level=\"(\\d+)\">(.*?)</codeLine>");
            for (String inputLine : inputLines) {
                Matcher matcher = pattern.matcher(inputLine);
                matcher.find();
                Integer level = Integer.parseInt(matcher.group(1)) - 1;
                String line = matcher.group(2);
                String outputLine = "";
                for (int i = 0; i < level; i++) {
                    outputLine += oneLevelIndent;
                }
                outputLine += line.replace(OPENING_TAG_ENTITY, OPENING_TARGET).replace("&closeTag;", CLOSING_TAG);
                outputLines.add(outputLine);
            }
        }
        FileUtils.writeLines(outputLines, outputFilePath);
    }


    private static int getLevel(String group) {
        return group.length() / oneLevelIndent.length() + 1;
    }
}

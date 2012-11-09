package fr.pragmaticmemory;
import fr.pragmaticmemory.fileProcessing.utils.FileUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Convertor {

    static final String OPENING_TAG = "<";
    static final String CLOSING_TAG = ">";
    static final String OPENING_TAG_CODE = "&#60;";
    static final String CLOSING_TAG_CODE = "&#62;";
    private final static String SPACE = " ";
    private int oneLevelIndentSize = 2;
    private String oneLevelIndent = null;


    public Convertor(int oneLevelIndentSize) {
        this.oneLevelIndentSize = oneLevelIndentSize;
    }


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

        int oneLevelIndentSize = 2;
        if (args.length > 3) {
            try {
                oneLevelIndentSize = Integer.parseInt(args[3]);
            }
            catch (NumberFormatException e) {
                System.out.println("ERREUR : Le quatrieme argument doit etre un entier");
                return;
            }
        }

        Convertor convertor = new Convertor(oneLevelIndentSize);
        for (String arg : args) {
            System.out.println("arg = " + arg);
        }

        String inputFilePath = args[1];
        String outputFilePath = args[2];

        List<String> inputLines = FileUtils.readLines(inputFilePath);
        final List<String> outputLines = convertor.convert(args[0], inputLines);
        FileUtils.writeLines(outputLines, outputFilePath);
    }


    protected List<String> convert(String convertionMode, List<String> inputLines) {
        List<String> outputLines = new ArrayList<String>();
        if ("code2xml".equals(convertionMode)) {
            Pattern pattern = Pattern.compile("(\\s*)\\.*");
            for (String inputLine : inputLines) {
                Matcher matcher = pattern.matcher(inputLine);
                matcher.find();
                String spaces = matcher.group(1);
                int level = getLevel(spaces);
                String outputLine = inputLine.replace(OPENING_TAG, OPENING_TAG_CODE).replace(CLOSING_TAG,
                                                                                                CLOSING_TAG_CODE);
                outputLine = outputLine.trim();
                outputLine = "<codeLine level=\"" + level + "\">" + outputLine + "</codeLine>";
                outputLines.add(outputLine);
            }
        }
        else if ("xml2code".equals(convertionMode)) {
            Pattern pattern = Pattern.compile("<codeLine\\s+level=\"(\\d+)\">(.*?)</codeLine>");
            for (String inputLine : inputLines) {
                Matcher matcher = pattern.matcher(inputLine);
                matcher.find();
                Integer level = Integer.parseInt(matcher.group(1)) - 1;
                String line = matcher.group(2);
                String outputLine = "";
                for (int i = 0; i < level; i++) {
                    outputLine += getOneLevelIndent();
                }
                outputLine += line.replace(OPENING_TAG_CODE, OPENING_TAG).replace(CLOSING_TAG_CODE, CLOSING_TAG);
                outputLines.add(outputLine);
            }
        }
        return outputLines;
    }


    private int getLevel(String group) {
        return group.length() / getOneLevelIndent().length() + 1;
    }


    private String getOneLevelIndent() {
        if (oneLevelIndent == null) {
            oneLevelIndent = "";
            for (int i = 0; i < oneLevelIndentSize; i++) {
                oneLevelIndent += SPACE;
            }
        }
        return oneLevelIndent;
    }
}

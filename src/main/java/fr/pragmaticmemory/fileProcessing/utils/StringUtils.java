package fr.pragmaticmemory.fileProcessing.utils;

public class StringUtils {

    private StringUtils() {
    }


    public static String leftPad(String s, int width) {
        return String.format("%" + width + "s", s);
    }


    public static String rightPad(String s, int width) {
        return String.format("%-" + width + "s", s);
    }
}

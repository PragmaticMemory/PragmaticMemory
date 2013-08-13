package fr.pragmaticmemory.fileProcessing.routeProvider;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class StringListWriter extends Writer {

    private List<StringWriter> stringWriterList = new ArrayList<StringWriter>();
    private int currentWriterIndex = 0;


    public StringListWriter() {
    }


    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        char[] subArray = Arrays.copyOfRange(cbuf, off, off + len);
        String substring = new String(subArray);
        String[] lines = substring.split("\\n", -1);

        int toBeWrittenlength = len;
        for (int i = 0, linesLength = lines.length; i < linesLength - 1; i++) {
            String line = lines[i];
            if (line.length() == 0) {

            }
            else if (toBeWrittenlength <= line.length()) {
                getCurrentWriter().write(subArray, 0, toBeWrittenlength);
                return;
            }
            else {
                getCurrentWriter().write(cbuf, off, line.length());
                toBeWrittenlength = toBeWrittenlength - line.length();
            }
            getCurrentWriter();
            currentWriterIndex++;
            getCurrentWriter();
        }
        if (lines.length > 0) {
            String lastLine = lines[lines.length - 1];
            if (!lastLine.isEmpty()) {
                getCurrentWriter().write(lastLine, 0, lastLine.length());
            }
        }
    }


    private StringWriter getCurrentWriter() {
        if (stringWriterList.size() < currentWriterIndex + 1) {
            stringWriterList.add(new StringWriter());
        }
        return stringWriterList.get(currentWriterIndex);
    }


    @Override
    public void close() throws IOException {
        for (Writer stringWriter : stringWriterList) {
            stringWriter.close();
        }
    }


    @Override
    public void flush() throws IOException {
        // Todo
    }


    public int getLineNumber() {
        return stringWriterList.size();
    }


    public String getLine(int lineIndex) {
        return stringWriterList.get(lineIndex).toString();
    }


    public List<String> getStringList() {
        List<String> stringList = new ArrayList<String>();
        for (Writer stringWriter : stringWriterList) {
            stringList.add(stringWriter.toString());
        }
        return stringList;
    }
}

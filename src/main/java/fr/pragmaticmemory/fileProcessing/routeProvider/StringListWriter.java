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
    private String separator = "\n";
    private StringBuilder cache = new StringBuilder();


    public StringListWriter(String separator) {
        this.separator = separator;
    }


    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        char[] subArray = Arrays.copyOfRange(cbuf, off, off + len);
        String substring = new String(subArray);
        cache.append(substring);

        String[] lines = cache.toString().split(separator, -1);

        if (lines.length == 1) {
            cache = new StringBuilder();
            cache.append(lines[0]);
            return;
        }

        int lastIndex = lines.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            String line = lines[i];
            getCurrentWriter().write(line, 0, line.length());
            currentWriterIndex++;
        }

        cache = new StringBuilder();
        cache.append(lines[lastIndex]);
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
        getCurrentWriter().write(cache.toString());
        cache = new StringBuilder();
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

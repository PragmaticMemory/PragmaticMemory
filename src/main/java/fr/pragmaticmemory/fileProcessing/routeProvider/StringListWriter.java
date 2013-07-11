package fr.pragmaticmemory.fileProcessing.routeProvider;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
public class StringListWriter extends Writer {

    private List<StringWriter> writerList = new ArrayList<StringWriter>();


    public StringListWriter() {
    }


    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        // Todo
    }


    @Override
    public void close() throws IOException {
        // Todo
    }


    @Override
    public void flush() throws IOException {
        // Todo
    }


    public int getLineNumber() {
        return writerList.size();
    }


    public String getLine(int lineIndex) {
        return writerList.get(lineIndex).getBuffer().toString();
    }
}

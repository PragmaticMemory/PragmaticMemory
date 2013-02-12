package fr.pragmaticmemory.fileProcessing.routeProvider;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class StringListReader extends Reader {

    private List<Reader> readerList = new ArrayList<Reader>();
    private int currentReaderIndex = 0;


    public StringListReader(List<String> lineList) {
        for (int i = 0, stringListSize = lineList.size(); i < stringListSize; i++) {
            readerList.add(new StringReader(lineList.get(i) + "\n"));
        }
    }


    @Override
    public int read(char[] cbuffer, int offset, int length) throws IOException {
        StringBuilder builder = new StringBuilder();
        int readNumber = recursiveRead(builder, length, 0);
        char[] newBuffer = builder.toString().toCharArray();
        System.arraycopy(newBuffer, 0, cbuffer, offset, builder.length());
        return readNumber;
    }


    public int recursiveRead(StringBuilder builder, int length, int readNumber) throws IOException {
        char[] cbuffer = new char[length];
        Reader reader = readerList.get(currentReaderIndex);

        int localReadNumber = reader.read(cbuffer, 0, length);

        if (localReadNumber == -1) {
            if (currentReaderIndex < readerList.size() - 1) {
                currentReaderIndex++;
                return recursiveRead(builder, length, readNumber);
            }
            else {
                return -1;
            }
        }

        builder.append(Arrays.copyOfRange(cbuffer, 0, localReadNumber));
        readNumber += localReadNumber;

        if (localReadNumber == length) {
            return readNumber;
        }

        if (currentReaderIndex < readerList.size() - 1) {
            currentReaderIndex++;
            return recursiveRead(builder, length - localReadNumber, readNumber);
        }
        else {
            return readNumber;
        }
    }


    @Override
    public void close() throws IOException {
        for (Reader reader : readerList) {
            reader.close();
        }
    }


    @Override
    public void reset() throws IOException {
        currentReaderIndex = 0;
        for (Reader reader : readerList) {
            reader.reset();
        }
    }
}

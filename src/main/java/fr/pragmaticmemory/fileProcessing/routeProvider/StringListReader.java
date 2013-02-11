package fr.pragmaticmemory.fileProcessing.routeProvider;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class StringListReader extends Reader {

    private List<String> stringList = new ArrayList<String>();
    private List<Reader> readerList = new ArrayList<Reader>();


    public StringListReader(List<String> stringList) {
        this.stringList = stringList;
        for (int i = 0, stringListSize = stringList.size(); i < stringListSize; i++) {
            readerList.add(new StringReader(stringList.get(i)));
        }
    }


    @Override
    public int read(char[] cbuffer, int offset, int length) throws IOException {
        StringBuilder builder = new StringBuilder();
        ReaderData readerData = getReaderData(offset);
        int readNumber = recursiveRead(builder, readerData, length, -1);
        builder.append("\\n");
        char[] newBuffer = builder.toString().toCharArray();
        System.arraycopy(newBuffer, 0, cbuffer, 0, builder.length());
        return readNumber;
    }


    public int recursiveRead(StringBuilder builder, ReaderData readerData, int length, int readNumber)
          throws IOException {
        char[] cbuffer = new char[1000];
        int readerIndex = readerData.readerIndex;
        int lineLength = stringList.get(readerIndex).length();
        int relativeOffset = readerData.relativeOffset;
        Reader reader = readerList.get(readerIndex);
        if (relativeOffset + length < lineLength) {
            readNumber += reader.read(cbuffer, relativeOffset, length);
            builder.append(cbuffer);
            return readNumber;
        }
        int localReadNumber = reader.read(cbuffer, relativeOffset, lineLength);
        if (localReadNumber == -1) {
            return readNumber;
        }
        builder.append(Arrays.copyOfRange(cbuffer, 0, localReadNumber));
        readNumber += localReadNumber;
        if (readerIndex == readerList.size() - 1) {
            return readNumber;
        }
        return recursiveRead(builder, new ReaderData(readerIndex + 1, 0), length - lineLength, readNumber);
    }


    ReaderData getReaderData(int offset) {
        return recursiveGetReaderData(0, offset);
    }


    ReaderData recursiveGetReaderData(int readerIndex, int offset) {
        int lineLength = stringList.get(readerIndex).length();
        if (offset <= lineLength) {
            return new ReaderData(readerIndex, offset);
        }
        // offset > lineLength
        if (readerIndex == readerList.size() - 1) {
            return new ReaderData(readerIndex, offset);
        }
        return recursiveGetReaderData(readerIndex + 1, offset - lineLength - 1);
    }


    @Override
    public void close() throws IOException {
        for (Reader reader : readerList) {
            reader.close();
        }
    }


    static class ReaderData {
        protected int readerIndex;
        protected int relativeOffset;


        ReaderData(int readerIndex, int relativeOffset) {
            this.readerIndex = readerIndex;
            this.relativeOffset = relativeOffset;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ReaderData that = (ReaderData)o;

            if (readerIndex != that.readerIndex) {
                return false;
            }
            if (relativeOffset != that.relativeOffset) {
                return false;
            }

            return true;
        }


        @Override
        public int hashCode() {
            int result = readerIndex;
            result = 31 * result + relativeOffset;
            return result;
        }
    }
}

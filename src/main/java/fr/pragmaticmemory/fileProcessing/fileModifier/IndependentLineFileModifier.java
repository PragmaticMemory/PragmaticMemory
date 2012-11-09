package fr.pragmaticmemory.fileProcessing.fileModifier;

import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
import java.util.ArrayList;
import java.util.List;
public abstract class IndependentLineFileModifier extends TextFileModifier {

    protected IndependentLineFileModifier(FileProvider fileProvider) {
        super(fileProvider);
    }


    @Override
    protected List<String> processFileContent(List<String> lines) throws Exception {
        List<String> resultList = new ArrayList<String>();
        for (int i = 0, linesSize = lines.size(); i < linesSize; i++) {
            resultList.add(processLine(lines.get(i)));
        }
        return resultList;
    }


    protected abstract String processLine(String line);
}

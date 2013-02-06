package fr.pragmaticmemory.fileProcessing.routeProvider;

import fr.pragmaticmemory.fileProcessing.core.Route;
import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
import java.io.FileNotFoundException;
public class MappedFileRouteProvider implements RouteProvider {

    private FileProvider inputFileProvider;
    private FileProvider outputFileProvider;


    public MappedFileRouteProvider(FileProvider inputFileProvider, FileProvider outputFileProvider) {
        this.inputFileProvider = inputFileProvider;
        this.outputFileProvider = outputFileProvider;

        if (inputFileProvider.getAllFile().size() != outputFileProvider.getAllFile().size()) {
            throw new IllegalArgumentException("Les deux FileProvider doivent fournir le même nombre de fichiers.");
        }
    }


    public int getRouteNumber() {
        return inputFileProvider.getAllFile().size();
    }


    public Route getRoute(int routeIndex) throws FileNotFoundException {
        return new FileRoute(inputFileProvider.getAllFile().get(routeIndex),
                             outputFileProvider.getAllFile().get(routeIndex));
    }
}

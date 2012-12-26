package fr.pragmaticmemory.fileProcessing.routeProvider;

import fr.pragmaticmemory.fileProcessing.core.Route;
import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
public class IdentityFileRouteProvider implements RouteProvider {
    private final List<File> fileList;


    public IdentityFileRouteProvider(FileProvider fileProvider) {
        fileList = fileProvider.getAllFile();
    }


    public int getRouteNumber() {
        return fileList.size();
    }


    public Route getRoute(int routeIndex) throws FileNotFoundException {
        return new IdentityFileRoute(fileList.get(routeIndex));
    }
}

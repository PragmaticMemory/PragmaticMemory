package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
public class IdentityFileRouteProvider implements RouteProvider {
    List<File> fileList;


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

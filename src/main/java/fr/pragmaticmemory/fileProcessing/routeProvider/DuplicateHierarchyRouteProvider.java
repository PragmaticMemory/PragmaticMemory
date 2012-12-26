package fr.pragmaticmemory.fileProcessing.routeProvider;

import fr.pragmaticmemory.fileProcessing.core.Route;
import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import fr.pragmaticmemory.fileProcessing.fileProvider.FileProvider;
import fr.pragmaticmemory.fileProcessing.utils.FileUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class DuplicateHierarchyRouteProvider implements RouteProvider {

    private final File destinationRootDirectory;
    private final List<Route> routeList = new ArrayList<Route>();


    public DuplicateHierarchyRouteProvider(FileProvider fileProvider, File destinationRootDirectory)
          throws IOException {
        this.destinationRootDirectory = destinationRootDirectory;
        for (File file : fileProvider.getAllFile()) {
            File destinationFile = getDestinationFile(file);
            FileUtils.createFile(destinationFile);
            routeList.add(new FileRoute(file, destinationFile));
        }
    }


    File getDestinationFile(File file) {
        final StringBuilder builder = new StringBuilder();
        builder.append(destinationRootDirectory.getAbsolutePath());
        builder.append("\\");
        builder.append(file.getAbsolutePath().replace(":", ""));
        return new File(builder.toString());
    }


    public int getRouteNumber() {
        return routeList.size();
    }


    public Route getRoute(int routeIndex) throws FileNotFoundException {
        return routeList.get(routeIndex);
    }
}

package fr.pragmaticmemory.fileProcessing.routeProvider;

import fr.pragmaticmemory.fileProcessing.core.Route;
import fr.pragmaticmemory.fileProcessing.core.RouteProvider;
import fr.pragmaticmemory.fileProcessing.utils.FileUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class DuplicateHierarchyRouteProvider implements RouteProvider {

    private File destinationRootDirectory;
    private List<File> sourceFileList = new ArrayList<File>();
    private List<File> sourceDirectoryList = new ArrayList<File>();
    private List<Route> routeList = new ArrayList<Route>();


    public DuplicateHierarchyRouteProvider(File sourceDirectory, File destinationRootDirectory) throws IOException {
        this.destinationRootDirectory = destinationRootDirectory;
        initSourceLists(sourceDirectory);
        buildTargetDirectories();
        buildTargetFiles();
        buildRoutes();
    }


    private void initSourceLists(File item) {
        if (item.isDirectory()) {
            sourceDirectoryList.add(item);
            final File[] childItems = item.listFiles();
            for (File childItem : childItems) {
                initSourceLists(childItem);
            }
        }
        else {
            sourceFileList.add(item);
        }
    }


    private String getDestinationFilePath(File file) {
        StringBuilder builder = new StringBuilder();
        builder.append(destinationRootDirectory.getAbsolutePath());
        builder.append("\\");
        builder.append(file.getAbsolutePath().replace(":", ""));
        return builder.toString();
    }


    private void buildTargetDirectories() throws IOException {
        for (File file : sourceDirectoryList) {
            FileUtils.createDirectory(new File(getDestinationFilePath(file)));
        }
    }


    private void buildTargetFiles() throws IOException {
        for (File sourceFile : sourceFileList) {
            FileUtils.createFile(new File(getDestinationFilePath(sourceFile)));
        }
    }


    private void buildRoutes() {
        for (File file : sourceFileList) {
            routeList.add(new FileRoute(file,
                                        new File(getDestinationFilePath(file))));
        }
    }


    public int getRouteNumber() {
        return routeList.size();
    }


    public Route getRoute(int routeIndex) throws FileNotFoundException {
        return routeList.get(routeIndex);
    }
}

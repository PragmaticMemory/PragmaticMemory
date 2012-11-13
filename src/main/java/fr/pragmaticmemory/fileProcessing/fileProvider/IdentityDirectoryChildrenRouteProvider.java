package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
public class IdentityDirectoryChildrenRouteProvider implements RouteProvider {

    private File directory;
    private boolean isRecursive = true;
    private List<File> fileList;


    public IdentityDirectoryChildrenRouteProvider(File directory) {
        this.directory = directory;
    }


    public IdentityDirectoryChildrenRouteProvider(String filePath) {
        this(new File(filePath));
    }


    public IdentityDirectoryChildrenRouteProvider(File directory, boolean isRecursive) {
        this(directory);
        this.isRecursive = isRecursive;
    }


    public IdentityDirectoryChildrenRouteProvider(String filePath, boolean isRecursive) {
        this(new File(filePath), isRecursive);
    }


    protected void getAllSubFilePath(File aDirectory, List<File> theFileList, boolean recursive) {
        File[] childItems = aDirectory.listFiles();
        for (File childItem : childItems) {
            if (!childItem.isDirectory()) {
                theFileList.add(childItem);
            }
        }

        if (isRecursive) {
            for (File childFile : childItems) {
                if (childFile.isDirectory()) {
                    getAllSubFilePath(childFile, theFileList, isRecursive);
                }
            }
        }
    }


    private void initFileList() {
        fileList = new ArrayList<File>();
        getAllSubFilePath(directory, fileList, isRecursive);
    }


    public int getRouteNumber() {
        return getFileList().size();
    }


    public Route getRoute(int routeIndex) throws FileNotFoundException {
        return new IdentityFileRoute(getFileList().get(routeIndex));
    }


    public List<File> getFileList() {
        if (fileList == null) {
            initFileList();
        }
        return fileList;
    }
}

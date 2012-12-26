package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class DirectoryFileProvider implements FileProvider {

    private final File rootDirectory;
    private boolean isRecursive = true;


    public DirectoryFileProvider(File directory) {

        this.rootDirectory = directory;
    }


    public DirectoryFileProvider(String filePath) {
        this(new File(filePath));
    }


    public DirectoryFileProvider(File directory, boolean isRecursive) {
        this(directory);
        this.isRecursive = isRecursive;
    }


    public DirectoryFileProvider(String filePath, boolean isRecursive) {
        this(new File(filePath), isRecursive);
    }


    protected void getAllSubFilePath(File directory, List<File> fileList, boolean recursive) {
        final File[] childItems = directory.listFiles();
        for (File childItem : childItems) {
            if (!childItem.isDirectory()) {
                fileList.add(childItem);
            }
        }

        if (isRecursive) {
            for (File childFile : childItems) {
                if (childFile.isDirectory()) {
                    getAllSubFilePath(childFile, fileList, isRecursive);
                }
            }
        }
    }


    public List<File> getAllFile() {
        final List<File> fileList = new ArrayList<File>();
        getAllSubFilePath(rootDirectory, fileList, isRecursive);
        return fileList;
    }
}
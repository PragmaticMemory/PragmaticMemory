package fr.pragmaticmemory.fileProcessing.fileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public abstract class FileProvider {

    protected void getAllSubFilePath(File file, List<String> fileList) {
        File[] files = file.listFiles();
        for (File childFile : files) {
            String absolutePath = childFile.getAbsolutePath();
            if (!childFile.isDirectory() && keepFile(childFile)) {
                fileList.add(absolutePath);
            }
        }

        for (File childFile : files) {
            if (childFile.isDirectory()) {
                getAllSubFilePath(childFile, fileList);
            }
        }
    }


    protected List<String> searchAllFile(String rootDirectory) {
        String userDir = getProjectDirectory();
        File file = new File(userDir + rootDirectory);
        List<String> fileList = new ArrayList<String>();
        getAllSubFilePath(file, fileList);
        return fileList;
    }


    protected String getProjectDirectory() {
        return System.getProperty("user.dir");
    }


    protected List<String> getConstantFilePathList(String... stringArray) {
        List<String> stringList = new ArrayList<String>();
        stringList.addAll(Arrays.asList(stringArray));
        return stringList;
    }


    abstract public List<File> getAllFile();


    abstract protected boolean keepFile(File childFile);
}

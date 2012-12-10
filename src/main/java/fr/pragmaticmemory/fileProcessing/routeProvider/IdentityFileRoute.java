package fr.pragmaticmemory.fileProcessing.routeProvider;

import java.io.File;
public class IdentityFileRoute extends FileRoute {

    public IdentityFileRoute(File file) {
        super(file, file);
    }
}

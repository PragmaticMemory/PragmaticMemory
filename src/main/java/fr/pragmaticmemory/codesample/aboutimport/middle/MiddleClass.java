package fr.pragmaticmemory.codesample.aboutimport.middle;

import fr.pragmaticmemory.codesample.aboutimport.server.ServeurClass;
public class MiddleClass {

    private final ServeurClass serverClass = new ServeurClass();


    public ServeurClass getServerClass() {
        return serverClass;
    }
}
package fr.pragmaticmemory.codesample.aboutimport.client;

import fr.pragmaticmemory.codesample.aboutimport.middle.MiddleClass;
public class ClientClass {

    public void clientMethod() {
        MiddleClass middleClass = new MiddleClass();
        middleClass.getServerClass().serverMethod();
    }

    /*
        La classe ClientClass appelle la méthode serverMethod() de la classe ServerClass.
        Or, comme l'instance de ServerClass est renvoyée par la méthode getServerClass() de la classe MiddleClass,
        il n'y a pas d'import du package "server" dans le fichier source de client.
        Conclusion : pour vérifier l'utilisation d'une classe par une autre, vérifier les imports de package
        n'est pas suffisant.
    */
    public static void main(String[] args) {
        ClientClass clientClass = new ClientClass();
        clientClass.clientMethod();
    }
}

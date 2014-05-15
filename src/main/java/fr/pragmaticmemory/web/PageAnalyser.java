package fr.pragmaticmemory.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
public class PageAnalyser {

    private static String proxyType = "HTTP";
    private static String proxyHost = "AAA";
    private static String proxyPort = "80";
    static final String URL = "http://www.votewatch.eu/en/jose-bove.html";
    static final String DOMAIN = "XXX";
    static final String LOGIN = "YYY";
    static final String PASSWORD = "ZZZ";
    //    static final String URL = "http://www.yahoo.com/";


    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", proxyPort);
        System.setProperty("http.proxyUser", DOMAIN + "\\" + LOGIN);
        System.setProperty("http.proxyPassword", PASSWORD);

        defineAuthenticator();

        URL url = new URL(URL);

//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        DocumentBuilder db = dbf.newDocumentBuilder();
//        Document doc = db.parse(url.openStream());

//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//DocumentBuilder db = dbf.newDocumentBuilder();
//Document doc = db.parse(url.openStream());
//NodeList nodes = doc.getElementsByTagName("row");
//System.out.println(nodes.getLength()) + " nodes found";

        BufferedReader in = new BufferedReader(
              new InputStreamReader(url.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }
        in.close();
    }


    private static void defineAuthenticator() {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                if (getRequestorType() == RequestorType.PROXY) {
                    String prot = getRequestingProtocol().toLowerCase();
                    String host = System.getProperty(prot + ".proxyHost", "");
                    String port = System.getProperty(prot + ".proxyPort", "");
                    String user = System.getProperty(prot + ".proxyUser", "");
                    String password = System.getProperty(prot + ".proxyPassword", "");
                    if (getRequestingHost().toLowerCase().equals(host.toLowerCase())) {
                        if (Integer.parseInt(port) == getRequestingPort()) {
                            // Seems to be OK.
                            return new PasswordAuthentication(user, password.toCharArray());
                        }
                    }
                }
                return null;
            }
        });
    }

    // Java ignores http.proxyUser. Here come's the workaround.
}

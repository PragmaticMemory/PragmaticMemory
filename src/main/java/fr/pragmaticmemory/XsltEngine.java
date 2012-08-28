package fr.pragmaticmemory;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
public class XsltEngine {

    static final String PATH = "C:\\dev\\projects\\PragmaticMemory\\knowledge\\data";


    public static void main(String[] args)
          throws TransformerException, IOException, SAXException, ParserConfigurationException {

        Document document = getDOM(PATH + "\\Data.xml");
        File file = new File(PATH + "\\TEST_OUTPUT.xml");

        StreamSource xslt = new StreamSource(new File(PATH + "\\ViewOnly.xsl"));
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setAttribute("indent-number", 6); // n'est pas pris en compte pour l'indentation de la sortie
        
        Transformer transformer = factory.newTransformer(xslt);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");

        // Vu dans un forum : If the Transformer implementation you're using is Xalan-J, then you should be able to use:
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setParameter(OutputKeys.INDENT, "yes");

        
        transformer.transform(new DOMSource(document), new StreamResult(file));
    }


    private static Document getDOM(String filePath)
          throws ParserConfigurationException, SAXException, IOException, TransformerException {
        DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();

        // création d'un constructeur de documents
        DocumentBuilder constructeur = fabrique.newDocumentBuilder();

        // lecture du contenu d'un fichier XML avec DOM
        File xml = new File(filePath);
        Document document = constructeur.parse(xml);
        displayDomInConsole(document);
        return document;
    }


    private static void displayDomInConsole(Document document) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(System.out));
    }
}

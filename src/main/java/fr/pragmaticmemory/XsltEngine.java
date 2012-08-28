package fr.pragmaticmemory;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
public class XsltEngine {

    static final String PATH = "C:\\Users\\cignett\\Desktop\\PM";


    public static void main(String[] args)
          throws TransformerException, IOException, SAXException, ParserConfigurationException {

        Document document = getDOM(PATH + "\\Data.xml");
        Source source = new DOMSource(document);

        File file = new File(PATH + "\\TEST_OUTPUT");
        Result resultat = new StreamResult(file);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.transform(source, resultat);
    }


    private static Document getDOM(String filePath) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();

        // création d'un constructeur de documents
        DocumentBuilder constructeur = fabrique.newDocumentBuilder();

        // lecture du contenu d'un fichier XML avec DOM
        File xml = new File(filePath);
        return constructeur.parse(xml);
    }
}

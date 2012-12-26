package fr.pragmaticmemory.xslt;

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

    static final String ROOT_PATH = "C:\\dev\\projects\\PragmaticMemory\\knowledge\\data";


    public static void main(String[] args) throws Exception {
        final String inputXmlPath;
        final String outputXmlPath;
        final String xsltPath;
        if (args.length == 0) {
            inputXmlPath = ROOT_PATH + "\\input\\View.xml";
            xsltPath = ROOT_PATH + "\\input\\Wikispaces.xsl";
            outputXmlPath = ROOT_PATH + "\\output\\Output2.txt";
        }
        else if (args.length != 3) {
            // se placer dans PragmaticMemory\target\classes et executer : java fr.pragmaticmemory.XsltEngine "input" "xslt" "output"
            String helpMessage = "usage : \n"
                                 + "premier argument : chemin du fichier xml d'entree\n"
                                 + "second argument : chemin du fichier xslt\n"
                                 + "troisieme argument : chemin du fichier xslt de sortie\n";
            System.out.println(helpMessage);
            return;
        }
        else {
            inputXmlPath = args[0];
            xsltPath = args[1];
            outputXmlPath = args[2];
        }

        System.out
              .println("java fr.pragmaticmemory.XsltEngine" + quote(inputXmlPath) + quote(xsltPath) + quote(
                    outputXmlPath));
        transform(new File(inputXmlPath), new File(xsltPath), new File(outputXmlPath));
    }


    private static void transform(File inputXmlPath, File xsltPath, File outputXmlPath) throws Exception {
        final Document document = getDOM(inputXmlPath.getAbsolutePath());
        final File file = new File(outputXmlPath.getAbsolutePath());

        final StreamSource xslt = new StreamSource(new File(xsltPath.getAbsolutePath()));
        final TransformerFactory factory = TransformerFactory.newInstance();
        factory.setAttribute("indent-number", 6); // non pris en compte pour l'indentation de la sortie

        final Transformer transformer = factory.newTransformer(xslt);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // Vu dans un forum : If the Transformer implementation you're using is Xalan-J, then you should be able to use:
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setParameter(OutputKeys.INDENT, "yes");

        transformer.setURIResolver(new XsltResolver());// permet de gérer les include de xslt

        transformer.transform(new DOMSource(document), new StreamResult(file));
    }


    private static Document getDOM(String filePath)
          throws ParserConfigurationException, SAXException, IOException, TransformerException {
        final DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();

        // création d'un constructeur de documents
        final DocumentBuilder constructeur = fabrique.newDocumentBuilder();

        // lecture du contenu d'un fichier XML avec DOM
        final File xml = new File(filePath);
        return constructeur.parse(xml);
    }


    private static void displayDomInConsole(Document document) throws TransformerException {
        final TransformerFactory factory = TransformerFactory.newInstance();
        final Transformer transformer = factory.newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(System.out));
    }


    static private String quote(String string) {
        return " \"" + string + "\"";
    }
}

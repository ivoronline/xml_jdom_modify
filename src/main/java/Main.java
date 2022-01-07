import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class Main {

  public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {

    //CREATE DOCUMENT BUILDER
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder        builder = factory.newDocumentBuilder();

    //SPECIFY XML SOURCE (FILE)
    File                   input = new File("Input.xml");

    //CREATE DOCUMENT
    Document               document  = builder.parse(input);
                           document.getDocumentElement().normalize();

    //INCREASE ATTRIBUTE ID BY 10
    NodeList nodeList = document.getElementsByTagName("person");
    for (int i = 0; i < nodeList.getLength(); i++) {

      //GET NEXT ELEMENT
      Node    personNode    = nodeList.item(i);
      Element personElement = (Element) personNode;

      //INCREASE ID BY 10
      String  idOld = personElement.getAttribute("id");
      Integer idNew = 10 + Integer.valueOf(idOld);
      personElement.setAttribute("id", String.valueOf(idNew));

    }

    //CHANGE NAME
    NodeList names = document.getElementsByTagName("name");
    for (int i = 0; i < names.getLength(); i++) {

      //GET NEXT ELEMENT
      Node    nameNode    = names.item(i);
      Element nameElement = (Element) nameNode;

      //CHANGE NAME
      String  nameOld = nameElement.getTextContent();
      String  nameNew = "First Name: " + nameOld;
      nameElement.setTextContent(nameNew);

    }

    //REMOVE AGE ELEMENTS
    NodeList persons = document.getElementsByTagName("person");
    for (int i = 0; i < persons.getLength(); i++) {

      //GET NEXT PERSON
      Node    personNode    = persons.item(i);
      Element personElement = (Element) personNode;

      //REMOVE AGE
      Node    age    = personElement.getElementsByTagName("age").item(0);
      personElement.removeChild(age);

    }

    //WRITE XML ---------------------------------------------------------------------------------------------

    //PREPARE TO WRITE
    DOMSource          source             = new DOMSource(document);
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer        transformer        = transformerFactory.newTransformer();

    //WRITE TO XML FILE
    StreamResult       result = new StreamResult(new File("Output.xml"));
                       transformer.transform(source, result);

    //WRITE TO CONSOLE
    StreamResult       consoleResult = new StreamResult(System.out);
                       transformer.transform(source, consoleResult);

  }

}

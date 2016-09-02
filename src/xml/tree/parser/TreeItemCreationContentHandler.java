package xml.tree.parser;

import javafx.scene.control.TreeItem;
import jdk.internal.org.xml.sax.Attributes;
import jdk.internal.org.xml.sax.SAXException;
import jdk.internal.org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * Created by svkreml on 23.08.2016.
 */
class TreeItemCreationContentHandler extends DefaultHandler {

    private TreeItem<String> item = new TreeItem<>();

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // finish this node by going back to the parent
        this.item = this.item.getParent();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // start a new node and use it as the current item
        TreeItem<String> item = new TreeItem<>(qName);
        this.item.getChildren().add(item);
        this.item = item;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s = String.valueOf(ch, start, length).trim();
        if (!s.isEmpty()) {
            // add text content as new child
            this.item.getChildren().add(new TreeItem<>(s));
        }
    }

    public static TreeItem<String> readData(File file) throws SAXException, ParserConfigurationException, IOException, org.xml.sax.SAXException {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        TreeItemCreationContentHandler contentHandler = new TreeItemCreationContentHandler();

        // parse file using the content handler to create a TreeItem representation
        reader.setContentHandler((ContentHandler) contentHandler);
        reader.parse(file.toURI().toString());

        // use first child as root (the TreeItem initially created does not contain data from the file)
        TreeItem<String> item = contentHandler.item.getChildren().get(0);
        contentHandler.item.getChildren().clear();
        return item;
    }
}
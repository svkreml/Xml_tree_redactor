package xml.tree.parser;

import javafx.scene.control.TreeItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by svkreml on 22.08.2016.
 */
public class Parser {
    private static Document doc;
    private String fileName;

    public Parser(String fileName) {
        this.fileName = fileName;
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setValidating(false);
            DocumentBuilder builder = null;
            builder = f.newDocumentBuilder();
            doc = builder.parse(new File(fileName));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TreeItem run(TreeItem tree) {
        Element root = doc.getDocumentElement();
        stepThrough (root, tree);
        tree.setValue(fileName);
        return tree;
    }

    private static void stepThrough (Node start, TreeItem tree) {
        TreeItem leaf = new TreeItem();
        leaf.setValue(start.getNodeName() + " | " + start.getNodeValue());
        int i=0;
        for (Node child = start.getFirstChild();
             child != null;
             child = child.getNextSibling())
        {
            stepThrough(child, leaf);
            i++;
        }
        if (start.getNodeName().startsWith("#Name") && start.getNodeValue()==null);
        else if(start.getNodeName().startsWith("#text") && start.getNodeValue().startsWith("\n"));
            else
        tree.getChildren().add(leaf);
    }
}

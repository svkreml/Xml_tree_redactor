package xml.tree.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import xml.tree.parser.Parser;

import java.util.ArrayList;

import static xml.tree.Main.MeSetTitle;

public class redactorView {
    @FXML
    private TreeView xmlTree;
    @FXML
    private TextField xPath;
    @FXML
    private TextField Tag;
    @FXML
    private TextField Text;

    @FXML
    private void load() {
        TreeItem rootItem = new TreeItem();
        Parser p;
        p = new Parser("data\\Result.xml");
        MeSetTitle("Result.xml");
        ArrayList<TreeItem> items = new ArrayList<>();
        TreeItem root = new TreeItem();
        xmlTree.setRoot(p.run(root));
    }
}

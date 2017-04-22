package inventory.viewAndControllers;

import inventory.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;


/**
 * Created by madrimas on 20.03.2017.
 */
public class RootLayoutController {

    private Main main;

    public void setMain(Main main){
        this.main = main;
    }

    @FXML
    private void handleNew(){
        main.getProducts().clear();
        main.setProductFilePath(null);
    }

    @FXML
    private void handleOpen(){
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml" );
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(main.getPrimaryStage());

        if (file != null){
            main.loadProductDataFromFile(file);
        }
    }

    @FXML
    private void handleSave(){
        File productFile = main.getProductFilePath();
        if (productFile != null){
            main.saveProductDataToFile(productFile);
        }
        else{
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs(){
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml" );
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(main.getPrimaryStage());

        if( file != null){
            if(!file.getPath().endsWith(".xml")){
                file = new File(file.getPath() + ".xml");
            }
            main.saveProductDataToFile(file);
        }
    }

    @FXML
    private void handleClose(){
        System.exit(0);
    }

    @FXML
    private void handleAbout(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Info");
        dialog.setHeaderText("Autor: Tomasz Zapiórkowski");
        dialog.setContentText("Jeśli masz pytania, napisz: zapiorkowski@gmail.com");

        dialog.setGraphic(new ImageView("file:resources/images/icon.png"));

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton);

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:resources/images/icon.png"));

        dialog.showAndWait();
    }
}

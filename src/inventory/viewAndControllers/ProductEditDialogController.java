package inventory.viewAndControllers;

import inventory.model.Product;
import inventory.model.ProductType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * Created by madrimas on 20.03.2017.
 */
public class ProductEditDialogController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<ProductType> typeComboBox;
    @FXML
    private CheckBox pairedCheckBox;

    private Stage dialogStage;
    private Product product;
    private boolean okClicked = false;

    @FXML
    private void initialize(){}

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public void setProduct(Product product){
        this.product = product;

        nameField.setText(product.getProductName());
        quantityField.setText(Integer.toString(product.getProductQuantity()));
        typeComboBox.getItems().setAll(ProductType.values());
        typeComboBox.setValue(product.getProductType());
        pairedCheckBox.setSelected(product.getProductIsPaired());
    }

    public boolean isOkClicked(){
        return okClicked;
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if(nameField.getText() == null || nameField.getText().length() == 0){
            errorMessage += "Nieprawidłowa nazwa produktu!\n";
        }
        if(quantityField.getText() == null || quantityField.getText().length() == 0){
            errorMessage += "Nieprawidłowa ilość produktów!\n";
        }
        else {
            try {
                Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Nieprawidłowa ilość produktów\n";
            }
        }
        if(typeComboBox.getValue() == null ){
            errorMessage += "Niezaznaczono typu produktu!\n";
        }
        if(errorMessage.length() == 0){
            return true;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Błędne dane!");
            alert.setHeaderText("Wprowadź poprawne dane!");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    @FXML
    private void handleOk(){
        if(isInputValid()){
            product.setProductName(nameField.getText());
            product.setProductQuantity(Integer.parseInt(quantityField.getText()));
            product.setProductType(typeComboBox.getValue());
            product.setProductIsPaired(pairedCheckBox.isSelected());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }
}

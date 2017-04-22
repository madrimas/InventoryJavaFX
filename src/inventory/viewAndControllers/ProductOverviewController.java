package inventory.viewAndControllers;

import inventory.Main;
import inventory.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class ProductOverviewController {
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label pairedLabel;

    private Main main;

    public ProductOverviewController(){}

    @FXML
    private void initialize(){
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getProductNameProperty());

        showProductDetails(null);

        productTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProductDetails(newValue));
    }

    public void setMain(Main main){
        this.main = main;

        productTableView.setItems(main.getProducts());
    }

    private void showProductDetails(Product product){
        if(product != null){
            nameLabel.setText(product.getProductName());
            quantityLabel.setText(Integer.toString(product.getProductQuantity())); //to String
            typeLabel.setText(product.getProductType().toString()); //to String
            pairedLabel.setText(product.productIsPairedString()); //to String
        }
        else{
            nameLabel.setText("");
            quantityLabel.setText("");
            typeLabel.setText("");
            pairedLabel.setText("");
        }
    }

    @FXML
    private void handleDeleteProduct(){
        int selectedIndex = productTableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex>=0) {
            productTableView.getItems().remove(selectedIndex);
        }
        else {
            Alert alert = new Alert((Alert.AlertType.WARNING));
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Brak wyboru!");
            alert.setHeaderText("Żaden produkt nie został wybrany!");
            alert.setContentText("Wybierz produkt z tabeli.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewProduct(){
        Product tempProduct = new Product();
        boolean okClicked = main.showProductEditDialog(tempProduct);
        if(okClicked){
            main.getProducts().add(tempProduct);
        }
    }

    @FXML
    private void handleEditProduct(){
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if(selectedProduct != null){
            boolean okClicked = main.showProductEditDialog(selectedProduct);
            if(okClicked){
                showProductDetails(selectedProduct);
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Nic nie wybrane");
            alert.setHeaderText("Żaden profukt nie został wybrany");
            alert.setContentText("Proszę wybrać produkt z tabeli");

            alert.showAndWait();
        }
    }
}

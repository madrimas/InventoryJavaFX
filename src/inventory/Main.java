package inventory;

import inventory.model.Product;
import inventory.model.ProductListWrapper;
import inventory.model.ProductType;
import inventory.viewAndControllers.ProductEditDialogController;
import inventory.viewAndControllers.ProductOverviewController;
import inventory.viewAndControllers.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Product> products = FXCollections.observableArrayList();

    public Main(){
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Inwentarz");
        this.primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));

        initRootLayout();

        showProductOverview();
    }

    private void initRootLayout() {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("viewAndControllers/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController rootLayoutController = loader.getController();
            rootLayoutController.setMain(this);

            primaryStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }

//        File file = getProductFilePath();
//        if (file != null){
//            loadProductDataFromFile(file);
//        }
    }

    private void showProductOverview() {
        try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("viewAndControllers/ProductOverview.fxml"));
        AnchorPane productOverview = (AnchorPane) loader.load();

        rootLayout.setCenter(productOverview);

        ProductOverviewController controller = loader.getController();
        controller.setMain(this);

    } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showProductEditDialog(Product product){
        try{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("viewAndControllers/ProductEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edycja Produktu");
            dialogStage.getIcons().add(new Image("file:resources/images/icon.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ProductEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setProduct(product);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public File getProductFilePath(){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null){
            return new File(filePath);
        }
        else{
            return null;
        }
    }

    public void setProductFilePath(File file){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null){
            prefs.put("filePath", file.getPath());

            primaryStage.setTitle("Inwentarz - " + file.getName());
        }
        else{
            prefs.remove("filePath");

            primaryStage.setTitle("Inwentarz");
        }
    }

    public void loadProductDataFromFile(File file){
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(ProductListWrapper.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            ProductListWrapper productListWrapper = (ProductListWrapper) unmarshaller.unmarshal(file);

            products.clear();
            products.addAll(productListWrapper.getProducts());

            setProductFilePath(file);
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Nie udało się załadować danych");
            alert.setContentText("Nie udało się załadować danych z pliku:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void saveProductDataToFile(File file){
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(ProductListWrapper.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            ProductListWrapper productListWrapper = new ProductListWrapper();
            productListWrapper.setProducts(products);

            marshaller.marshal(productListWrapper, file);

            setProductFilePath(file);
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Nie udało się zapisać danych");
            alert.setContentText("Nie udało się zapisać danych do pliku:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {

        launch(args);
    }
}

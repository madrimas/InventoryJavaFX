package inventory.model;

import javafx.beans.property.*;

/**
 * Created by madrimas on 20.03.2017.
 */
public class Product {
    private StringProperty productName;
    private IntegerProperty productQuantity;
    private ObjectProperty<ProductType> productType;
    private BooleanProperty productIsPaired;

    public Product(){
        productName = new SimpleStringProperty("Nieokreślony");
        productQuantity = new SimpleIntegerProperty(0);
        productType = new SimpleObjectProperty<>(ProductType.UNDEFINED);
        productIsPaired = new SimpleBooleanProperty(false);
    }

    public Product(String name, int quantity, ProductType type, boolean paired){
        productName = new SimpleStringProperty(name);
        productQuantity = new SimpleIntegerProperty(quantity);
        productType = new SimpleObjectProperty<>(type);
        productIsPaired = new SimpleBooleanProperty(paired);
    }

    public String getProductName(){
        return productName.get();
    }

    public StringProperty getProductNameProperty(){
        return productName;
    }

    public void setProductName(String name){
        productName.set(name);
    }

    public int getProductQuantity(){
        return productQuantity.get();
    }

    public IntegerProperty getProductQuantityProperty(){
        return productQuantity;
    }

    public void setProductQuantity(int quantity){
        productQuantity.set(quantity);
    }

    public ProductType getProductType(){
        return productType.get();
    }

    public ObjectProperty<ProductType> getProductTypeProperty(){
        return productType;
    }

    public void setProductType(ProductType type){
        productType.set(type);
    }

    public boolean getProductIsPaired(){
        return productIsPaired.get();
    }

    public BooleanProperty getProductIsPairedProperty(){
        return productIsPaired;
    }

    public void setProductIsPaired(boolean paired){
        productIsPaired.set(paired);
    }

    public String productIsPairedString(){
        if(getProductIsPaired()) {
            return "Tak";
        }
        else {
            return "Nie";
        }
    }
}

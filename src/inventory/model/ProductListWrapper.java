package inventory.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by madrimas on 27.03.2017.
 */

@XmlRootElement
public class ProductListWrapper {
    private List<Product> products;

    @XmlElement(name = "product")
    public List<Product> getProducts(){
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

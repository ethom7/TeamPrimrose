package companyA;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by evanpthompson on 10/31/2016.
 */

@XmlRootElement(name = "inventory")
public class Inventory implements Comparable<Inventory> {

    private int id;
    private Object _id;
    private String productId;
    private String productNumber;
    private String itemDescription;
    private double itemCost;
    private double itemPrice;
    private int itemCount;

    public Inventory() {     }

    @XmlElement
    public Object get_id() {
        return _id;
    }

    public void set_id(Object _id) {
        this._id = _id;
    }




    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @XmlElement
    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    @XmlElement
    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    @XmlElement
    public double getItemCost() {
        return itemCost;
    }

    public void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }

    @XmlElement
    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    @XmlElement
    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }


    public int compareTo(Inventory other) {
        return this.id - other.id;
    }

    @Override
    public String toString() {
        return "Inventory : {" +
                "id : " + id +
                ", productId : " + productId +
                ", productNumber : " + productNumber +
                ", itemDescription : " + itemDescription +
                ", itemCost : " + itemCost +
                ", itemPrice : " + itemPrice +
                ", itemCount : " + itemCount +
                " }";
    }
}

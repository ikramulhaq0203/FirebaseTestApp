package firebasetest.ikram.com.firebasetest;

/**
 * Created by lenovo on 25-03-2018.
 */

public class OrderDetails {

    String buyingOrderID;
    String sellingOrderID;

    public OrderDetails() {
    }

    public OrderDetails(String buyingOrderID, String sellingOrderID) {
        this.buyingOrderID = buyingOrderID;
        this.sellingOrderID = sellingOrderID;
    }

    public String getBuyingOrderID() {
        return buyingOrderID;
    }

    public String getSellingOrderID() {
        return sellingOrderID;
    }

    public void setBuyingOrderID(String buyingOrderID) {
        this.buyingOrderID = buyingOrderID;
    }

    public void setSellingOrderID(String sellingOrderID) {
        this.sellingOrderID = sellingOrderID;
    }

}

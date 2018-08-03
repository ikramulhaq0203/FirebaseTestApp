package firebasetest.ikram.com.firebasetest;

/**
 * Created by lenovo on 25-03-2018.
 */

public class OrderDetails {

    long buyingOrderID;
    long sellingOrderID;

    public OrderDetails(){}

    public OrderDetails(long buyingOrderID, long sellingOrderID) {
        this.buyingOrderID = buyingOrderID;
        this.sellingOrderID = sellingOrderID;
    }

    public long getBuyingOrderID() {
        return buyingOrderID;
    }

    public long getSellingOrderID() {
        return sellingOrderID;
    }

    public  void setBuyingOrderID(long buyingOrderID) {
        this.buyingOrderID = buyingOrderID;
    }

    public  void setSellingOrderID(long sellingOrderID) {
        this.sellingOrderID = sellingOrderID;
    }

}

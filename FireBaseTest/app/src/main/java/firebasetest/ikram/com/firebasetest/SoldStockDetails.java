package firebasetest.ikram.com.firebasetest;

import java.io.Serializable;

import utils.UtilsClass;

/**
 * Created by lenovo on 27-06-2017.
 *
 * utility class
 */
public class SoldStockDetails implements Serializable{

    //14 items


    String itemId;
    String brandName;
    String sellingPrice;

    String customerName;
    String customerPhone;
    String customerAddress;

    String sellingDate;
    long sellingOrderID;
    double dueAmount;

    String key;


    public SoldStockDetails() {

    }

    public SoldStockDetails(String itemId, String price, String avalibleStatus) {
        this.itemId = itemId;
        this.sellingPrice = price;
    }

    public SoldStockDetails(long mOrderId, String itemId, String brandName, String price, String customerName, String customerPhone, String customerAddress, String key, double dues_amout) {//key ???

        this.itemId = itemId;
        this.brandName = brandName;
        this.sellingPrice = price;

        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;

        this.sellingOrderID = mOrderId;
        this.sellingDate = UtilsClass.getCurrentTime();
        this.key = key;

        this.dueAmount = dues_amout;
    }


    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setSellingDate(String sellingDate) {
        this.sellingDate = sellingDate;
    }

    public void setSellingOrderID(long sellingOrderID) {
        this.sellingOrderID = sellingOrderID;
    }

    public String getitemId() {
        return itemId;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public String getSoldItemKey() {
        return key;
    }

    public void setDueAmout(double dues_amout) {
        this.dueAmount = dues_amout;
    }
    public double getDueAmount() {
        return dueAmount;
    }
}

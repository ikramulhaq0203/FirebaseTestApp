package firebasetest.ikram.com.firebasetest;

import utils.UtilsClass;

/**
 * Created by lenovo on 27-06-2017.
 *
 * utility class
 */
public class SoldUtils {

    //14 items


    String itemId;
    String brandName;
    String sellingPrice;

    String customerName;
    String customerPhone;
    String customerAddress;

    String sellingDate;
    String sellingOrderID;
    String dueAmount;

    String key;


    public SoldUtils() {

    }

    public SoldUtils(String itemId, String price, String avalibleStatus) {
        this.itemId = itemId;
        this.sellingPrice = price;
    }

    public SoldUtils(String mOrderId, String itemId, String brandName, String price, String customerName, String customerPhone, String customerAddress, String dues_amout) {//key ???

        this.itemId = itemId;
        this.brandName = brandName;
        this.sellingPrice = price;

        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;

        this.sellingOrderID = mOrderId;
        this.sellingDate = UtilsClass.getCurrentTime();

        this.dueAmount = dues_amout;
    }


    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public void setSellingOrderID(String sellingOrderID) {
        this.sellingOrderID = sellingOrderID;
    }

    public void setDueAmout(String dues_amout) {
        this.dueAmount = dues_amout;
    }


    public String getitemId() {
        return itemId;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }


    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public double getDueAmount() {
        return Double.parseDouble(dueAmount);
    }


    public String getSoldItemKey() {
        return key;
    }
}

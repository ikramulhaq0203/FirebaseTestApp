package firebasetest.ikram.com.firebasetest;

import java.io.Serializable;

/**
 * Created by lenovo on 27-06-2017.
 *
 * utility class
 */
public class DuesUtilsClass implements Serializable{

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

    String lastUpdate;
    String key;

    String paidAmount;
    String currentDues;
    String paymentDate;

    public DuesUtilsClass() {

    }

    public DuesUtilsClass(String sellingOrderID, String itemId, String brandName, String sellingPrice, String customerName,
                          String customerPhone, String customerAddress, String sellingDate, String lastUpdate, String paidAmount, String dueAmount, String key) {//key ???

        this.itemId = itemId;
        this.brandName = brandName;
        this.sellingPrice = sellingPrice;

        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;

        this.sellingOrderID = sellingOrderID;
        this.sellingDate = sellingDate;

        this.lastUpdate = lastUpdate;
        this.dueAmount = dueAmount;
        this.key = key;
        this.paidAmount = paidAmount;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getSellingDate() {
        return sellingDate;
    }

    public void setSellingDate(String sellingDate) {
        this.sellingDate = sellingDate;
    }

    public String getSellingOrderID() {
        return sellingOrderID;
    }

    public void setSellingOrderID(String sellingOrderID) {
        this.sellingOrderID = sellingOrderID;
    }

    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getCurrentDues() {
        return currentDues;
    }

    public void setCurrentDues(String currentDues) {
        this.currentDues = currentDues;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

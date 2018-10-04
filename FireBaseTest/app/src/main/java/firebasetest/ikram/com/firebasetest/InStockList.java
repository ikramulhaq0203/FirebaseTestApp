package firebasetest.ikram.com.firebasetest;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import utils.UtilsClass;

/**
 * Created by lenovo on 27-06-2017.
 *
 * utility class
 */
public class InStockList implements Serializable, Comparable<InStockList> {

    String itemId;
    String brandName;
    String buyingPrice;

    String buyingOrderID;
    String buyingQuantity;
    String buyingDate;

    String availableQuantity;
    String avalibleStatus;
    String key;

    String lastUpdateDate;
    //boolean isDelete;

    public InStockList() {}

    public InStockList(String itemId, String key, String availableQuantity) {
        this.itemId = itemId;
        this.key = key;
        this.availableQuantity = availableQuantity;
    }

    public InStockList(String mOrderId, String itemId, String brandName, String price,
                     String buyingQuantity, String buyingDate, String availableQuantity, String lastUpdateDate, String key) {//key ???

        this.itemId = itemId;
        this.brandName = brandName;
        this.buyingPrice = price;

        this.buyingOrderID = mOrderId;

        this.buyingQuantity = buyingQuantity;
        this.buyingDate = buyingDate;

        this.availableQuantity = availableQuantity;
        this.avalibleStatus = "available";

        this.lastUpdateDate = lastUpdateDate;
        //this.isDelete = false;
        this.key = key;
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

    public String getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(String buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public String getBuyingOrderID() {
        return buyingOrderID;
    }

    public void setBuyingOrderID(String buyingOrderID) {
        this.buyingOrderID = buyingOrderID;
    }

    public String getBuyingQuantity() {
        return buyingQuantity;
    }

    public void setBuyingQuantity(String buyingQuantity) {
        this.buyingQuantity = buyingQuantity;
    }

    public String getBuyingDate() {
        return buyingDate;
    }

    public void setBuyingDate(String buyingDate) {
        this.buyingDate = buyingDate;
    }

    public String getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(String availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getAvalibleStatus() {
        return avalibleStatus;
    }

    public void setAvalibleStatus(String avalibleStatus) {
        this.avalibleStatus = avalibleStatus;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    //listing asscending order by date of creation
    @Override
    public int compareTo(InStockList another) {
        return this.getBuyingDate().compareTo(another.getBuyingDate());
    }
}

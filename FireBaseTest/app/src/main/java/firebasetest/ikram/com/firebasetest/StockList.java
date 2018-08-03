package firebasetest.ikram.com.firebasetest;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import utils.UtilsClass;

/**
 * Created by lenovo on 27-06-2017.
 *
 * utility class
 */
public class StockList implements Serializable{

    String itemId;
    String brandName;
    String buyingPrice;

    String buyingDate;
    long buyingQuantity;
    long buyingOrderID;

    long availableQuantity;
    String avalibleStatus;
    String stockItemKey;

    String lastUpdateDate;
    boolean isDelete;

    public StockList() {}

    public StockList(long mOrderId, String itemId, String brandName, String price, int buyingQuantity, String key) {//key ???

        this.itemId = itemId;
        this.brandName = brandName;
        this.buyingPrice = price;

        this.buyingDate = UtilsClass.getCurrentTime();
        this.buyingOrderID = mOrderId;

        this.buyingQuantity = buyingQuantity;
        this.availableQuantity = buyingQuantity;
        this.avalibleStatus = "available";
        this.stockItemKey = key;

        this.lastUpdateDate = "";
        this.isDelete = false;
    }


    public void setItemId(String itemId) {
        this.itemId = itemId;
    } //not used

    public String getitemId() {
        return itemId;
    } //not used

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBuyingPrice(String price) {
        this.buyingPrice = price;
    } //not used

    public String getBuyingPrice() {
        return buyingPrice;
    } //not used


    public void setBuyingQuantity(long buyingQuantity) {
        this.buyingQuantity = buyingQuantity;
    }

    public long getBuyingQuantity() {
        return buyingQuantity;
    }


    public void setAvailableQuantity(long availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public long getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvalibleStatus(String avalibleStatus) {
        this.avalibleStatus = avalibleStatus;
    }

    public String getAvalibleStatus() {
        return avalibleStatus;
    }


    public String getBuyingDate() {
        return buyingDate;
    }

    public void setLastUpdateDate() {
        this.lastUpdateDate = UtilsClass.getCurrentTime();
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public String getStockItemKey() {
        return stockItemKey;
    }

}

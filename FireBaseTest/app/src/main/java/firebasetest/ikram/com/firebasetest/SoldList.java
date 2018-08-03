package firebasetest.ikram.com.firebasetest;

import java.io.Serializable;

/**
 * Created by lenovo on 27-06-2017.
 */
public class SoldList implements Serializable{

    String orderId;
    String sold_price;
    String avalibleStatus;
    String key;
    String user_name;
    String user_phone;


    public SoldList() {

    }

    public SoldList(String orderId, String sold_price, String avalibleStatus) {
        this.orderId = orderId;
        this.sold_price = sold_price;
        this.avalibleStatus = avalibleStatus;
    }

    public SoldList(String orderId, String sold_price, String avalibleStatus, String key) {
        this.orderId = orderId;
        this.sold_price = sold_price;
        this.avalibleStatus = avalibleStatus;
        this.key = key;
    }

    public String getStockItemKey() {
        return this.key;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setPrice(String sold_price) {
        this.sold_price = sold_price;
    }

    public void setAvalibleStatus(String avalibleStatus) {
        this.avalibleStatus = avalibleStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPrice() {
        return sold_price;
    }

    public String getAvalibleStatus() {
        return avalibleStatus;
    }
}

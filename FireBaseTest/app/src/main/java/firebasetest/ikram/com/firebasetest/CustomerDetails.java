package firebasetest.ikram.com.firebasetest;

import java.io.Serializable;

/**
 * Created by lenovo on 28-06-2017.
 *
 * utility class
 */
class CustomerDetails implements Serializable {

    String name;
    String phone;
    String location;

    String item_id;
    String item_name;
    String price;

    public CustomerDetails() {}

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getPrice() {
        return price;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public CustomerDetails(String name, String phone, String location,
                           String item_name, String item_id, String price) {
        this.name = name;
        this.phone = phone;
        this.location = location;

        this.item_id = item_id;
        this.item_name = item_name;
        this.price = price;
    }
}

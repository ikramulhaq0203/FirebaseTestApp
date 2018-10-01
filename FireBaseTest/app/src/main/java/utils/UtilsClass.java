package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lenovo on 23-03-2018.
 */

public class UtilsClass {

    public static final int ADD_ITEM_REQUEST_CODE = 1;
    public static final int SELL_ITEM_REQUEST_CODE = 2;

    public static long BUYING_ORDER_ID = 1000;
    public static long SELLIGN_ORDER_ID = 10000;

    public static final String USERS_TABLE_NAME = "user_list";
    public static final String USERS_DETAILS = "user_details";
    public static final String USERS_STOCKS = "user_stock";
    public static final String USERS_SOLD_STOCKS = "user_sold_stock";
    public static final String BUYING_SELLING_ORDER_ID = "user_orders";
    public static final String DUES_TABLE = "dues_table";


    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }
}

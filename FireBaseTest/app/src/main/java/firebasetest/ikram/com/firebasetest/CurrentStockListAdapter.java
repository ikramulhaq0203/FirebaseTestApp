package firebasetest.ikram.com.firebasetest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 27-06-2017.
 *
 * utility class
 */
public class CurrentStockListAdapter extends ArrayAdapter<StockList>{

    LayoutInflater inflator;
    ArrayList<StockList> availablStockList;


    public CurrentStockListAdapter(Context mcontext, int row, ArrayList<StockList> arrayList) {
        super(mcontext, 0, arrayList);
        inflator = (LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        availablStockList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        View view = convertView;
        if (view == null) {
            view = inflator.inflate(R.layout.available_stock, parent, false);
        }
        TextView list_item_id = (TextView)view.findViewById(R.id.list_item_id);
        TextView list_item_brand = (TextView)view.findViewById(R.id.list_item_brand);
        TextView list_item_price = (TextView)view.findViewById(R.id.list_item_price);
        TextView list_item_quantity = (TextView)view.findViewById(R.id.list_item_quantity);

        long current_price = 0;
        if (availablStockList.get(position).getBuyingQuantity() > 0) {
            current_price = (Integer.valueOf(availablStockList.get(position).getBuyingPrice()) / availablStockList.get(position).getBuyingQuantity()) * availablStockList.get(position).getAvailableQuantity();
        }

        list_item_id.setText(availablStockList.get(position).getitemId());
        list_item_brand.setText(availablStockList.get(position).getBrandName());
        list_item_price.setText(String.valueOf(current_price));
        list_item_quantity.setText(String.valueOf(availablStockList.get(position).getBuyingPrice() +" X " +availablStockList.get(position).getAvailableQuantity()));
        return view;
    }
}

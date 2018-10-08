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
 * Created by lenovo on 26-03-2018.
 */
public class SoldStockListAdapter extends ArrayAdapter<OutStockList> {
    LayoutInflater inflator;
    ArrayList<OutStockList> soldStockList;


    public SoldStockListAdapter(Context mcontext, int row, ArrayList<OutStockList> arrayList) {
        super(mcontext, 0, arrayList);
        inflator = (LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        soldStockList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        View view = convertView;
        if (view == null) {
            view = inflator.inflate(R.layout.sold_stock_list, parent, false);
        }
        TextView list_item_id = (TextView)view.findViewById(R.id.list_item_id);
        TextView list_item_brand = (TextView)view.findViewById(R.id.list_item_brand);
        TextView list_item_price = (TextView)view.findViewById(R.id.list_item_price);
        TextView list_item_selling_date = (TextView)view.findViewById(R.id.list_item_selling_date);

        list_item_id.setText(soldStockList.get(position).getItemId());
        list_item_brand.setText(soldStockList.get(position).getBrandName());
        list_item_price.setText(soldStockList.get(position).getSellingPrice());
        list_item_selling_date.setText(String.valueOf(soldStockList.get(position).getSellingDate().substring(0, 10)));
        return view;
    }
}


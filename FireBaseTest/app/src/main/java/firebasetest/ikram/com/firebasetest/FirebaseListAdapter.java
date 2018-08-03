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
class FirebaseListAdapterCustom extends ArrayAdapter<StockList>{

    LayoutInflater inflator;
    ArrayList<StockList> stockList;


    public FirebaseListAdapterCustom(Context mcontext, int row, ArrayList<StockList> arrayList) {
        super(mcontext, 0, arrayList);
        inflator = (LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        stockList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = inflator.inflate(R.layout.row, parent, false);
        }
        TextView list_item_id = (TextView)view.findViewById(R.id.list_item_id);
        TextView list_item_brancd = (TextView)view.findViewById(R.id.list_item_brand);
        TextView list_item_price = (TextView)view.findViewById(R.id.list_item_price);
        TextView list_item_purchase_date = (TextView)view.findViewById(R.id.list_item_prchase_date);

        list_item_id.setText(stockList.get(position).getitemId());
        list_item_brancd.setText(stockList.get(position).getBrandName());
        list_item_price.setText(""+stockList.get(position).getBuyingPrice()+" X "+stockList.get(position).getBuyingQuantity());
        list_item_purchase_date.setText(stockList.get(position).getBuyingDate().substring(0, 10));
        return view;
    }
}

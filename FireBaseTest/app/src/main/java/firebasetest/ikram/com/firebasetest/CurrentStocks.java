package firebasetest.ikram.com.firebasetest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import utils.UtilsClass;

/**
 * Created by lenovo on 10-07-2017.
 *
 * UserDetails -> SoldStockList , SoldStockList
 */
public class CurrentStocks extends AppCompatActivity{


    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference muserStockRef;
    ArrayList<StockList> arrayList;
    CurrentStockListAdapter availableStockListAdapter;
    ListView mlistview;

    TextView mcolumn1, mcolumn2, mcolumn3;

    TextView total_price_current_stocks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.current_stock);

        getSupportActionBar().setElevation(0);

        initializeUiElement();

        mAuth = FirebaseAuth.getInstance();
        arrayList = new ArrayList<StockList>();


        mDatabase = FirebaseDatabase.getInstance();
        muserStockRef = mDatabase.getReference(UtilsClass.USERS_TABLE_NAME).child(mAuth.getCurrentUser().getUid()).child(UtilsClass.USERS_STOCKS);

        muserStockRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    if (datasnapshot.getValue(StockList.class).getAvalibleStatus().equals("available")) {
                        arrayList.add(datasnapshot.getValue(StockList.class));
                    }
                }
                availableStockListAdapter = new CurrentStockListAdapter(CurrentStocks.this, R.layout.available_stock, arrayList);
                mlistview.setAdapter(availableStockListAdapter);
                setTotalPrice();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initializeUiElement() {
        mlistview = (ListView)findViewById(R.id.list_item);
        total_price_current_stocks = (TextView)findViewById(R.id.total_price_current_stocks);
    }



    private void setTotalPrice() {
        double total_price = 0;
        for (int i = 0; i< arrayList.size(); i++) {
            total_price =  total_price + (double)(Double.valueOf(arrayList.get(i).getBuyingPrice()) * arrayList.get(i).getAvailableQuantity());
        }
        total_price_current_stocks.setText("  Toatal Stock Value = "+total_price);
    }
}

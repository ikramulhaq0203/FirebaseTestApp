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
 * Created by lenovo on 26-03-2018.
 */
public class SoldStock extends AppCompatActivity{

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference muserStockRef;
    ArrayList<SoldStockDetails> arrayList;
    SoldStockListAdapter soldStockListAdapter;
    ListView mlistview;

    TextView total_price_outransaction;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sold_stock);

        getSupportActionBar().setElevation(0);

        initializeUiElement();

        mAuth = FirebaseAuth.getInstance();
        arrayList = new ArrayList<SoldStockDetails>();


        mDatabase = FirebaseDatabase.getInstance();
        muserStockRef = mDatabase.getReference(UtilsClass.USERS_TABLE_NAME).child(mAuth.getCurrentUser().getUid()).child(UtilsClass.USERS_SOLD_STOCKS);

        muserStockRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    arrayList.add(datasnapshot.getValue(SoldStockDetails.class));
                }
                soldStockListAdapter = new SoldStockListAdapter(SoldStock.this, R.layout.sold_stock_list, arrayList);
                mlistview.setAdapter(soldStockListAdapter);
                setTotalPrice();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setTotalPrice() {
        double total_price = 0;
        for (int i = 0; i< arrayList.size(); i++) {
            total_price =  total_price + (double)(Double.valueOf(arrayList.get(i).getSellingPrice()));
        }
        total_price_outransaction.setText("  Out Transaction Value = "+total_price);
    }

    private void initializeUiElement() {
        mlistview = (ListView)findViewById(R.id.list_item);
        total_price_outransaction = (TextView)findViewById(R.id.total_price_outransaction);
    }
}

package firebasetest.ikram.com.firebasetest;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

import static android.widget.AdapterView.*;

/**
 * Created by lenovo on 27-06-2017.
 * UserDetails -> ShowStockList , ShowStockList
 * AddItems -> ShowStockList , after update stocks
 * CustomerCopy -> ShowStockList , after sell
 *
 * ShowStockList -> CustomerCopy , sell stock
 */

public class InTransaction extends AppCompatActivity {

    ListView mlistview;
    ArrayList<StockList> arrayList;
    FirebaseListAdapterCustom customAdapter;

    FirebaseDatabase mDatabase;
    DatabaseReference muserStockRef;
    FirebaseAuth mAuth;
    TextView total_price_intransaction;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stock_details);

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
                    arrayList.add(datasnapshot.getValue(StockList.class));
                }

                customAdapter = new FirebaseListAdapterCustom(InTransaction.this, R.layout.row, arrayList);
                mlistview.setAdapter(customAdapter);
                setTotalPrice();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       // registerForContextMenu(mlistview);

    }

    private void initializeUiElement() {
        mlistview = (ListView)findViewById(R.id.list_item);
        total_price_intransaction = (TextView)findViewById(R.id.total_price_intransaction);
    }

    private void setTotalPrice() {
        double total_price = 0;
        for (int i = 0; i< arrayList.size(); i++) {
            total_price =  total_price + (double)(Double.valueOf(arrayList.get(i).getBuyingPrice()) * arrayList.get(i).getBuyingQuantity());
        }
        total_price_intransaction.setText("  In Transaction Value = "+total_price);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        /*super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
       menuInflater.inflate(R.menu.context_menu, menu);*/
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
/*
        //return super.onContextItemSelected(item);
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_update:


                Intent intent = new Intent(this, StockUpdate.class);
                intent.putExtra("Myclass", customAdapter.getItem(info.position));
                startActivity(intent);
                Log.d("show_onCont", "" +info.position);

                break;
            case R.id.action_delete:
                Log.d("show_onCont", "" +info.position);
                DatabaseReference mRef = muserStockRef.child(customAdapter.getItem(info.position).getStockItemKey());
                mRef.removeValue();

                break;

            case R.id.action_sell:
                Log.d("show_onCont", "" +info.position);

                Intent intent2 = new Intent(this, CustomerCopy.class);
                intent2.putExtra("Myclass", customAdapter.getItem(info.position));
                startActivity(intent2);
                break;

            default:
                break;

        }

*/
        return true;
    }
}

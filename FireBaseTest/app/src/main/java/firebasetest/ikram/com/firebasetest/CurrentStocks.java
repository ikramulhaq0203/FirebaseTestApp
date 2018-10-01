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

/**
 * Created by lenovo on 10-07-2017.
 *
 * UserDetails -> SoldStockList , SoldStockList
 */
public class CurrentStocks extends AppCompatActivity{


    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference muserStockRef;
    ArrayList<InStockList> arrayList;
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
        arrayList = new ArrayList<InStockList>();


        mDatabase = FirebaseDatabase.getInstance();
        muserStockRef = mDatabase.getReference(UtilsClass.USERS_TABLE_NAME).child(mAuth.getCurrentUser().getUid()).child(UtilsClass.USERS_STOCKS);

        muserStockRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    if (datasnapshot.getValue(InStockList.class).getAvalibleStatus().equals("available")) {
                        arrayList.add(datasnapshot.getValue(InStockList.class));
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

        registerForContextMenu(mlistview);
    }

    private void initializeUiElement() {
        mlistview = (ListView)findViewById(R.id.list_item);
        total_price_current_stocks = (TextView)findViewById(R.id.total_price_current_stocks);
    }



    private void setTotalPrice() {
        double total_price = 0;
        for (int i = 0; i< arrayList.size(); i++) {
            total_price =  total_price + (double)(Double.valueOf(arrayList.get(i).getBuyingPrice()) * Long.parseLong(arrayList.get(i).getAvailableQuantity()));
        }
        total_price_current_stocks.setText("  Toatal Stock Value = "+total_price);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu_current_stock, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //return super.onContextItemSelected(item);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_update:

                Log.d("InTransaction.java", "" +"action_update");
                Intent intent = new Intent(this, InTransactionUpdate.class);
                intent.putExtra("Myclass", availableStockListAdapter.getItem(info.position));
                startActivity(intent);
                break;

            case R.id.action_sell:

                Log.d("InTransaction.java", "" +"action_sell");
                Intent intent2 = new Intent(this, SellActivity.class);
                intent2.putExtra("item_id", availableStockListAdapter.getItem(info.position).getItemId());
                startActivity(intent2);
                finish();
                break;

            default:
                break;

        }

        return true;
    }
}

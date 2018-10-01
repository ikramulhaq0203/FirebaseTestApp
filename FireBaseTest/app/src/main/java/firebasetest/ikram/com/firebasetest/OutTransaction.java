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
 * Created by lenovo on 26-03-2018.
 */
public class OutTransaction extends AppCompatActivity{

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference muserStockRef;
    ArrayList<OutStockList> arrayList;
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
        arrayList = new ArrayList<OutStockList>();


        mDatabase = FirebaseDatabase.getInstance();
        muserStockRef = mDatabase.getReference(UtilsClass.USERS_TABLE_NAME).child(mAuth.getCurrentUser().getUid()).child(UtilsClass.USERS_SOLD_STOCKS);

        muserStockRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    updateData(datasnapshot);
                }
                soldStockListAdapter = new SoldStockListAdapter(OutTransaction.this, R.layout.sold_stock_list, arrayList);
                mlistview.setAdapter(soldStockListAdapter);
                setTotalPrice();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        registerForContextMenu(mlistview);
    }

    String m_itemId;
    String m_brandName;
    String m_sellingPrice;

    String m_customerName;
    String m_customerPhone;
    String m_customerAddress;

    String m_sellingDate;
    String m_sellingOrderID;

    String m_dueAmount;
    String m_key;

    private void updateData(DataSnapshot datasnapshot) {

        m_itemId = (String) datasnapshot.child("itemId").getValue();
        m_brandName = (String) datasnapshot.child("brandName").getValue();
        m_sellingPrice = (String) datasnapshot.child("sellingPrice").getValue();

        m_customerName = (String) datasnapshot.child("customerName").getValue();
        m_customerPhone = (String) datasnapshot.child("customerPhone").getValue();
        m_customerAddress = (String) datasnapshot.child("customerAddress").getValue();

        m_sellingDate = (String) datasnapshot.child("sellingDate").getValue();
        m_sellingOrderID = (String) datasnapshot.child("sellingOrderID").getValue();

        m_dueAmount = (String) datasnapshot.child("dueAmount").getValue();
        m_key = datasnapshot.getKey();

        OutStockList outStockList = new OutStockList();

        outStockList.setItemId(m_itemId);
        outStockList.setBrandName(m_brandName);
        outStockList.setSellingPrice(m_sellingPrice);

        outStockList.setCustomerName(m_customerName);
        outStockList.setCustomerPhone(m_customerPhone);
        outStockList.setCustomerAddress(m_customerAddress);

        outStockList.setSellingDate(m_sellingDate);
        outStockList.setSellingOrderID(m_sellingOrderID);

        outStockList.setDueAmount(m_dueAmount);
        outStockList.setKey(datasnapshot.getKey());

        arrayList.add(outStockList);
        //muserStockRef.child(b_key).child("sellingPrice").setValue(3000);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
       menuInflater.inflate(R.menu.context_menu_intransaction, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //return super.onContextItemSelected(item);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_update:

                OutStockList outStockList2 = soldStockListAdapter.getItem(info.position);
                Intent intent = new Intent(this, OutTransactionUpdate.class);
                intent.putExtra("Myclass", outStockList2);
                startActivity(intent);
                Log.d("show_onCont", "" + info.position);
                break;

            default:
                break;

        }

        return true;
    }

}

package firebasetest.ikram.com.firebasetest;


import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

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

public class InTransaction extends AppCompatActivity implements PeriodsEditDialogFragment.PeriodsEditDialogFragmentInterfaceListener{

    ListView mlistview;
    ArrayList<InStockList> arrayList;
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
        arrayList = new ArrayList<InStockList>();


        mDatabase = FirebaseDatabase.getInstance();
        muserStockRef = mDatabase.getReference(UtilsClass.USERS_TABLE_NAME).child(mAuth.getCurrentUser().getUid()).child(UtilsClass.USERS_STOCKS);

        muserStockRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    //arrayList.add(datasnapshot.getValue(InStockList.class));
                    updateData(datasnapshot);
                }

                customAdapter = new FirebaseListAdapterCustom(InTransaction.this, R.layout.row, arrayList);
                mlistview.setAdapter(customAdapter);
                setTotalPrice();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       registerForContextMenu(mlistview);

    }

    InStockList inStockList;
    private void updateData(DataSnapshot datasnapshot) {

        String itemId = (String)datasnapshot.child("itemId").getValue();
        String brandName = (String)datasnapshot.child("brandName").getValue();
        String buyingPrice = (String)datasnapshot.child("buyingPrice").getValue();

        String buyingDate = (String)datasnapshot.child("buyingDate").getValue();
        String buyingQuantity = (String)datasnapshot.child("buyingQuantity").getValue();
        String buyingOrderID = (String)datasnapshot.child("buyingOrderID").getValue();

        String availableQuantity = (String)datasnapshot.child("availableQuantity").getValue();
        String avalibleStatus = (String)datasnapshot.child("avalibleStatus").getValue();
        String key = (String)datasnapshot.getKey();

        //String lastUpdateDate = (String)datasnapshot.child("lastUpdateDate").getValue();
        //boolean isDelete = (String)datasnapshot.child("itemId").getValue();

        inStockList = new InStockList(buyingOrderID, itemId, brandName, buyingPrice, buyingQuantity, buyingDate, availableQuantity, UtilsClass.getCurrentTime(), key);
        arrayList.add(inStockList);

    }

    private void initializeUiElement() {
        mlistview = (ListView)findViewById(R.id.list_item);
        total_price_intransaction = (TextView)findViewById(R.id.total_price_intransaction);
    }

    private void setTotalPrice() {
        double total_price = 0;
        for (int i = 0; i< arrayList.size(); i++) {
            total_price =  total_price + (double)(Double.valueOf(arrayList.get(i).getBuyingPrice()) * Long.parseLong(arrayList.get(i).getBuyingQuantity()));
        }
        total_price_intransaction.setText("  In Transaction Value = "+total_price);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.intransaction_option_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_select_date:

                break;
            case R.id.action_select_periods:
                listItemsforduration();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void listItemsforduration() {
        DialogFragment dialogFrament = new PeriodsEditDialogFragment();
        dialogFrament.show(getFragmentManager(), "PeriodsEditDialogFragment");
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
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_update:

                Log.d("InTransaction.java", "" +"action_update");
                Intent intent = new Intent(this, InTransactionUpdate.class);
                intent.putExtra("Myclass", customAdapter.getItem(info.position));
                startActivity(intent);
                break;

            case R.id.action_details:
                Log.d("show_onCont", "" +info.position);
                //DatabaseReference mRef = muserStockRef.child(customAdapter.getItem(info.position).getStockItemKey());
                //mRef.removeValue();
                //sample code
/*                Collections.sort(arrayList);

                String time = "2018/09/06 23:59:59";
                for (int i = 0; i < arrayList.size(); i++) {
                    if (time.compareTo(arrayList.get(i).getBuyingDate()) < 0) {
                        arrayList.remove(i);
                        i--;
                    }
                }
                customAdapter.notifyDataSetChanged();*/
                break;

            default:
                break;

        }

        return true;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String start_date, String end_date) {
        Toast.makeText(this, "start = "+start_date +" end = "+end_date ,Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}

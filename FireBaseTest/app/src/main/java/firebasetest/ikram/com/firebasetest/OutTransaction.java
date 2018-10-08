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
import android.widget.LinearLayout;
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

import utils.UtilsClass;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by lenovo on 26-03-2018.
 */
public class OutTransaction extends AppCompatActivity implements PeriodsEditDialogFragment.PeriodsEditDialogFragmentInterfaceListener{

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference muserStockRef;
    ArrayList<OutStockList> arrayList;
    SoldStockListAdapter soldStockListAdapter;
    ListView mlistview;
    LinearLayout bottom_layout;
    LinearLayout no_item_layout;
    TextView empty_text;

    TextView total_transaction;
    double total_price = 0;


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
                getTimeFromCalander();
                break;
            case R.id.action_select_periods:
                listItemsforduration();
                break;
        }

        return super.onOptionsItemSelected(item);
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

    private void listItemsforduration() {
        DialogFragment dialogFrament = new PeriodsEditDialogFragment();
        dialogFrament.show(getFragmentManager(), "PeriodsEditDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String start_date, String end_date) {
        Toast.makeText(this, "start = "+start_date +" end = "+end_date ,Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        String mStartDate = start_date +" 00:00:00";
        String mEndDate = end_date + " 23:59:59";
        dateSortingFunction(mStartDate, mEndDate);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    private void dateSortingFunction(String mStartDate, String mEndDate) {
        total_price = 0;
        int item = 0;

        for (int i = 0; i < arrayList.size(); i++) {
            if ((mStartDate.compareTo(arrayList.get(i).getSellingDate()) <= 0 && mEndDate.compareTo(arrayList.get(i).getSellingDate()) >= 0)) {
                total_price =  total_price + (double)(Double.valueOf(arrayList.get(i).getSellingPrice()));
                item++;
            } else {
                arrayList.remove(i);
                i--;
            }
        }
        soldStockListAdapter.notifyDataSetChanged();
        if (item != 0) {
            mlistview.setVisibility(VISIBLE);
            bottom_layout.setVisibility(VISIBLE);
            no_item_layout.setVisibility(GONE);
            empty_text.setVisibility(GONE);
            total_transaction.setText("  Total Value = "+total_price);
        } else {
            mlistview.setVisibility(GONE);
            bottom_layout.setVisibility(GONE);
            no_item_layout.setVisibility(VISIBLE);
            empty_text.setVisibility(VISIBLE);
        }
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
        for (int i = 0; i< arrayList.size(); i++) {
            total_price =  total_price + (double)(Double.valueOf(arrayList.get(i).getSellingPrice()));
        }
        if (arrayList.size() != 0) {
            mlistview.setVisibility(VISIBLE);
            bottom_layout.setVisibility(VISIBLE);
            no_item_layout.setVisibility(GONE);
            empty_text.setVisibility(GONE);
            total_transaction.setText(" Total Value = "+total_price);
        } else {
            mlistview.setVisibility(GONE);
            bottom_layout.setVisibility(GONE);
            no_item_layout.setVisibility(VISIBLE);
            empty_text.setVisibility(VISIBLE);
        }
    }

    private void initializeUiElement() {
        mlistview = (ListView)findViewById(R.id.list_item);
        total_transaction = (TextView)findViewById(R.id.total_transaction);
        bottom_layout = (LinearLayout)findViewById(R.id.bottom_layout);
        no_item_layout = (LinearLayout) findViewById(R.id.no_item_layout);
        empty_text = (TextView) findViewById(R.id.empty_text);
    }

    private void getTimeFromCalander() {

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String day_format;
                String month_format;
                if (dayOfMonth < 10) {
                    day_format = "0" + String.valueOf(dayOfMonth);
                } else {
                    day_format = String.valueOf(dayOfMonth);
                }

                if (monthOfYear < 9) {
                    month_format = "0" + String.valueOf(monthOfYear+1);
                } else {
                    month_format = String.valueOf(monthOfYear+1);
                }

                final String date = String.valueOf(year) + "/" + month_format
                        + "/" + day_format;

                String mStartDate = date +" 00:00:00";
                String mEndDate = date + " 23:59:59";
                dateSortingFunction(mStartDate, mEndDate);
            }
        }, yy, mm, dd);
        datePicker.show();
    }
}

package firebasetest.ikram.com.firebasetest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
 * Created by lenovo on 23-03-2018.
 */
public class SellActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mItemId;
    EditText mSelleingPrice;
    EditText mCustomerName;
    EditText mCustomerPhone;
    EditText mCustomerAddress;
    ImageButton mScanButton;
    Button mSellButton;
    TextView mDescription;

    TextView mDuesAmount;

    FirebaseDatabase mDatabase;
    DatabaseReference mUsersRef;
    FirebaseAuth mAuth;

    StockList stock;

    ArrayList<StockList> arrayList;
    OrderDetails od;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_activity);

        initializeUi();

        arrayList = new ArrayList<StockList>();
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUsersRef = mDatabase.getReference(UtilsClass.USERS_TABLE_NAME).child(mAuth.getCurrentUser().getUid()); //get current user reference

        dataChangeListener();

    }

    private void dataChangeListener() {

        mUsersRef.child(UtilsClass.USERS_STOCKS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    arrayList.add(datasnapshot.getValue(StockList.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("UserDEtails.java", "onCancelled", databaseError.toException());
            }
        });

        mUsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                od = dataSnapshot.child(UtilsClass.BUYING_SELLING_ORDER_ID).getValue(OrderDetails.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("UserDEtails.java", "onCancelled", databaseError.toException());
            }
        });
    }

    private void initializeUi() {

        mItemId = (EditText)findViewById(R.id.editText_sell_item_id);
        mSelleingPrice = (EditText)findViewById(R.id.editText_sell_item_price);
        mCustomerName = (EditText)findViewById(R.id.editText_sell_customer_name);
        mCustomerPhone = (EditText)findViewById(R.id.editText_sell_customer_phone);
        mCustomerAddress = (EditText)findViewById(R.id.editText_sell_customer_address);
        mDuesAmount = (EditText)findViewById(R.id.editText_sell_dues_amount);

        mDescription = (TextView)findViewById(R.id.sell_description);
        mSellButton = (Button)findViewById(R.id.button_sell);
        mScanButton = (ImageButton)findViewById(R.id.sell_scan_button);
        mScanButton.setOnClickListener(this);
        mSellButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.sell_scan_button:
                Intent scanIntent = new Intent(this, ScannerActivity.class);
                startActivityForResult(scanIntent, UtilsClass.SELL_ITEM_REQUEST_CODE);
                break;

            case R.id.button_sell:

                if (mItemId.getText().toString().equals("") || mSelleingPrice.getText().toString().equals("") ||
                        mCustomerName.getText().toString().equals("") || mCustomerPhone.getText().toString().equals("")) {
                    mDescription.setText("Please fill all required field *");
                    mDescription.setTextColor(Color.RED);
                    return;
                }

                boolean isItemExist = false;
                for (int i = 0; i<arrayList.size(); i++) {
                    if(arrayList.get(i).itemId.equals(mItemId.getText().toString())) {
                        isItemExist = true;
                        stock = arrayList.get(i);
                    }
                }

                if (!isItemExist) {
                    mDescription.setText("Please enter correct item id");
                    mDescription.setTextColor(Color.RED);
                    return;
                }

                if(!stock.getAvalibleStatus().equals("available")) {
                    mDescription.setText("Item is not availble in stock");
                    mDescription.setTextColor(Color.RED);
                    return;
                }

                if (mCustomerPhone.getText().toString().length() < 10) {
                    mDescription.setText("Please enter correct phone number !");
                    mDescription.setTextColor(Color.RED);
                    return;
                }

                if (stock.getAvailableQuantity() > 0) {
                    if (stock.getAvailableQuantity() == 1) {
                        stock.setAvalibleStatus("sold");
                    }

                    //long total_price = (Long.valueOf(stock.getPrice()) /stock.getquantity()) * (stock.getquantity() - 1) ;

                    stock.setAvailableQuantity(stock.getAvailableQuantity() - 1);
                    //stock.setPrice(String.valueOf(total_price));
                    mDescription.setText("Congratulation!! successfully sold");
                    mDescription.setTextColor(Color.BLUE);
                }

                od.setSellingOrderID(od.getSellingOrderID() +1);  // crash observed
                mUsersRef.child(UtilsClass.BUYING_SELLING_ORDER_ID).setValue(od);

                String customerAddress = "";
                if (!mCustomerAddress.getText().toString().isEmpty()) {
                    customerAddress = mCustomerAddress.getText().toString();
                }

                double dues_amount = 0;

                if (!mDuesAmount.getText().toString().isEmpty()) {
                    dues_amount = Double.valueOf(mDuesAmount.getText().toString());
                }


                mUsersRef.child(UtilsClass.USERS_STOCKS).child(stock.getStockItemKey()).setValue(stock);

                DatabaseReference mSoldStockRef = mUsersRef.child(UtilsClass.USERS_SOLD_STOCKS).push();

                SoldStockDetails soldList = new SoldStockDetails(od.getSellingOrderID(), mItemId.getText().toString(),stock.getBrandName(),
                        mSelleingPrice.getText().toString(), mCustomerName.getText().toString(),mCustomerPhone.getText().toString(),
                        customerAddress, mSoldStockRef.getKey(), dues_amount);

                mSoldStockRef.setValue(soldList);

                DatabaseReference mDueRef = mSoldStockRef.push();
                mDueRef.child("lastUpdateDate").setValue(UtilsClass.getCurrentTime());
                mDueRef.child("lastUpdateDueAmount").setValue(soldList.getDueAmount());



                mItemId.setText(null);
                mSelleingPrice.setText(null);
                mCustomerName.setText(null);
                mCustomerPhone.setText(null);
                mDuesAmount.setText(null);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UtilsClass.SELL_ITEM_REQUEST_CODE && resultCode == RESULT_OK) {
            mItemId.setText(data.getStringExtra("result"));
        }
    }

}

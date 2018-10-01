package firebasetest.ikram.com.firebasetest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
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
 * Created by lenovo on 28-06-2017.
 */
public class OutTransactionUpdate extends AppCompatActivity implements View.OnClickListener {

    EditText mItemId;
    EditText mSelleingPrice;
    EditText mCustomerName;
    EditText mCustomerPhone;
    EditText mCustomerAddress;
    ImageButton mScanButton;
    Button mSellButton;
    TextView mDescription;

    TextView mDuesAmount;

    TextInputLayout mItemId_layout;
    TextInputLayout mSelleingPrice_layout;
    TextInputLayout mCustomerName_layout;
    TextInputLayout mCustomerPhone_layout;
    TextInputLayout mCustomerAddress_layout;
    TextInputLayout mDuesAmount_layout;

    FirebaseDatabase mDatabase;
    DatabaseReference mUsersRef;
    FirebaseAuth mAuth;

    DatabaseReference mUsersSoldStockRef;

    OutStockList outStockList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_activity);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUsersRef = mDatabase.getReference(UtilsClass.USERS_TABLE_NAME).child(mAuth.getCurrentUser().getUid()); //get current user reference

        outStockList = (OutStockList) getIntent().getSerializableExtra("Myclass");
        mUsersSoldStockRef = mUsersRef.child(UtilsClass.USERS_SOLD_STOCKS);

        initializeUi();
        setDefautVale();
    }

    ArrayList<InStockList> arrayList;

    private void dataChangeListener() {

        mUsersRef.child(UtilsClass.USERS_STOCKS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    arrayList.add(datasnapshot.getValue(InStockList.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("UserDEtails.java", "onCancelled", databaseError.toException());
            }
        });
    }

    private void setDefautVale() {
        if (outStockList != null) {
            mItemId.setText(outStockList.getItemId());
            mSelleingPrice.setText(outStockList.getSellingPrice());
            mCustomerName.setText(outStockList.getCustomerName());
            mCustomerPhone.setText(outStockList.getCustomerPhone());
            mDuesAmount.setText(outStockList.getDueAmount());
        }
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

        mItemId_layout = (TextInputLayout) findViewById(R.id.editText_sell_item_id_layout);
        mSelleingPrice_layout = (TextInputLayout) findViewById(R.id.editText_sell_item_price_layout);
        mCustomerName_layout = (TextInputLayout) findViewById(R.id.editText_sell_customer_name_layout);
        mCustomerPhone_layout = (TextInputLayout) findViewById(R.id.editText_sell_customer_phone_layout);
        mCustomerAddress_layout = (TextInputLayout) findViewById(R.id.editText_sell_customer_address_layout);
        mDuesAmount_layout = (TextInputLayout) findViewById(R.id.editText_sell_dues_amount_layout);

        mItemId_layout.setHint("Item id *");
        mSelleingPrice_layout.setHint("Selling Price *");
        mCustomerName_layout.setHint("Customer Name *");
        mCustomerPhone_layout.setHint("Customer phone *");
        mCustomerAddress_layout.setHint("Customer address - Optional");
        mDuesAmount_layout.setHint("Dues Amount - Optional");


        mSellButton.setText("Update");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_sell:

                if (mItemId.getText().toString().equals("") || mSelleingPrice.getText().toString().equals("") ||
                        mCustomerName.getText().toString().equals("") || mCustomerPhone.getText().toString().equals("")) {
                    mDescription.setText("Please fill all required field *");
                    mDescription.setTextColor(Color.RED);
                    return;
                }


                if (mCustomerPhone.getText().toString().length() < 10) {
                    mDescription.setText("Please enter correct phone number !");
                    mDescription.setTextColor(Color.RED);
                    return;
                }

                outStockList.setItemId(mItemId.getText().toString());
                outStockList.setSellingPrice(mSelleingPrice.getText().toString());
                outStockList.setCustomerName(mCustomerName.getText().toString());
                outStockList.setCustomerPhone(mCustomerPhone.getText().toString());
                //customerAddress, mDuesAmount.getText().toString());



          /*      outStockList.setBrandName(outStockList.getBrandName());
                outStockList.setCustomerAddress(outStockList.getCustomerAddress());
                outStockList.setSellingDate(outStockList.getSellingDate());
                outStockList.setSellingOrderID(outStockList.getSellingOrderID());
                outStockList.setKey(outStockList.getKey());*/

                outStockList.setItemId(mItemId.getText().toString());
                outStockList.setSellingPrice(mSelleingPrice.getText().toString());
                outStockList.setCustomerName(mCustomerName.getText().toString());
                outStockList.setCustomerPhone(mCustomerPhone.getText().toString());
                outStockList.setDueAmount( mDuesAmount.getText().toString());
                outStockList.setLastUpdate(UtilsClass.getCurrentTime());

                mUsersSoldStockRef.child(outStockList.getKey()).setValue(outStockList);

                Intent intent = new Intent(this, OutTransaction.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}

package firebasetest.ikram.com.firebasetest;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
    EditText mDuesAmount;

    ImageButton mScanButton;
    Button mSellButton;
    TextView mDescription;

    TextInputLayout mItemId_layout;
    TextInputLayout mSelleingPrice_layout;
    TextInputLayout mCustomerName_layout;
    TextInputLayout mCustomerPhone_layout;
    TextInputLayout mCustomerAddress_layout;
    TextInputLayout mDuesAmount_layout;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;


    FirebaseDatabase mDatabase;
    DatabaseReference mUsersRef;
    FirebaseAuth mAuth;

    InStockList inStockList;

    ArrayList<InStockList> arrayList;
    OrderDetails order_details;

    Context mContext;

    boolean isSellingPriceLessThanBuyingPrice = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_activity);

        initializeUi();

        arrayList = new ArrayList<InStockList>();
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUsersRef = mDatabase.getReference(UtilsClass.USERS_TABLE_NAME).child(mAuth.getCurrentUser().getUid()); //get current user reference

        dataChangeListener();

        mContext = this;

    }

    private void dataChangeListener() {

        mUsersRef.child(UtilsClass.USERS_STOCKS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    updateData(datasnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("UserDEtails.java", "onCancelled", databaseError.toException());
            }
        });

        mUsersRef.child(UtilsClass.BUYING_SELLING_ORDER_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                order_details = dataSnapshot.getValue(OrderDetails.class); //need to be changed
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("UserDEtails.java", "onCancelled", databaseError.toException());
            }
        });
    }

    private void updateData(DataSnapshot datasnapshot) {

/*        String mItemId = (String) datasnapshot.child("itemId").getValue();
        String mkey = datasnapshot.getKey();
        String availableQuantity = (String)datasnapshot.child("availableQuantity").getValue();*/

    /*    StockList stockList = new StockList(mItemId, mkey, availableQuantity);
        arrayList.add(stockList);*/

        String mItemId = (String)datasnapshot.child("itemId").getValue();
        String brandName = (String)datasnapshot.child("brandName").getValue();
        String buyingPrice = (String)datasnapshot.child("buyingPrice").getValue();

        String buyingDate = (String)datasnapshot.child("buyingDate").getValue();
        String buyingQuantity = (String)datasnapshot.child("buyingQuantity").getValue();
        String buyingOrderID = (String)datasnapshot.child("buyingOrderID").getValue();

        String availableQuantity = (String)datasnapshot.child("availableQuantity").getValue();
        String avalibleStatus = (String)datasnapshot.child("avalibleStatus").getValue();
        String mkey = (String)datasnapshot.getKey();

        //String lastUpdateDate = (String)datasnapshot.child("lastUpdateDate").getValue();
        //boolean isDelete = (String)datasnapshot.child("itemId").getValue();

        inStockList = new InStockList(buyingOrderID, mItemId, brandName, buyingPrice, buyingQuantity, buyingDate, availableQuantity, UtilsClass.getCurrentTime(), mkey);
        arrayList.add(inStockList);
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

        String m_itemid = getIntent().getStringExtra("item_id");
        if (m_itemid != null) {
            mItemId.setText(m_itemid);
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.sell_scan_button:
                checkCameraPermissions();
                break;

            case R.id.button_sell:

                if (mItemId.getText().toString().equals("") || mSelleingPrice.getText().toString().equals("") ||
                        mCustomerName.getText().toString().equals("") || mCustomerPhone.getText().toString().equals("")) {
                    mDescription.setText("Please fill all required field *");
                    mDescription.setTextColor(Color.RED);
                    return;
                }

                boolean isItemExist = false;
                String sKey;

                for (int i = 0; i<arrayList.size(); i++) {
                    if(arrayList.get(i).getItemId().equals(mItemId.getText().toString())) {
                        isItemExist = true;
                        sKey = arrayList.get(i).getKey();
                        inStockList = arrayList.get(i);
                    }
                }

                if (!isItemExist) {
                    mDescription.setText("Please enter correct item id");
                    mDescription.setTextColor(Color.RED);
                    return;
                }

                if(Long.parseLong(inStockList.getAvailableQuantity()) <= 0) {
                    mDescription.setText("Item is not availble in stock");
                    mDescription.setTextColor(Color.RED);
                    return;
                }

                if (mCustomerPhone.getText().toString().length() < 10) {
                    mDescription.setText("Please enter correct phone number !");
                    mDescription.setTextColor(Color.RED);
                    return;
                }

                if (Double.parseDouble(mSelleingPrice.getText().toString()) < Double.parseDouble(inStockList.getBuyingPrice())) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                    builder.setTitle("Warning!")
                            .setMessage("Do you really want to sell this item less than buying price ?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(SellActivity.this, "Sold item successfully", Toast.LENGTH_SHORT).show();
                                    isSellingPriceLessThanBuyingPrice = true;
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    isSellingPriceLessThanBuyingPrice = false;
                                    Toast.makeText(SellActivity.this, "cancelled", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                if(!isSellingPriceLessThanBuyingPrice) {
                    return;
                }

                if (Long.parseLong(inStockList.getAvailableQuantity()) > 0) {
                    inStockList.setAvailableQuantity(String.valueOf(Long.parseLong(inStockList.getAvailableQuantity()) - 1));
                    mDescription.setText("Congratulation!! successfully sold");
                    mDescription.setTextColor(Color.BLUE);
                }

                order_details.setSellingOrderID(String.valueOf(Long.parseLong(order_details.getSellingOrderID()) +1));  // crash observed
                mUsersRef.child(UtilsClass.BUYING_SELLING_ORDER_ID).setValue(order_details);

                String customerAddress = "";
                if (!mCustomerAddress.getText().toString().isEmpty()) {
                    customerAddress = mCustomerAddress.getText().toString();
                }

                mUsersRef.child(UtilsClass.USERS_STOCKS).child(inStockList.getKey()).setValue(inStockList);//try to get complete stock tuples

                String mDues = "0";
                if (!mDuesAmount.getText().toString().isEmpty()) {
                    mDues = mDuesAmount.getText().toString();
                }

                OutStockList outStockList = new OutStockList(order_details.getSellingOrderID(), mItemId.getText().toString(), inStockList.getBrandName(),
                        mSelleingPrice.getText().toString(), mCustomerName.getText().toString(),mCustomerPhone.getText().toString(),
                        customerAddress, UtilsClass.getCurrentTime(), UtilsClass.getCurrentTime(), mDues, "");

                mUsersRef.child(UtilsClass.USERS_SOLD_STOCKS).push().setValue(outStockList);//.getKey();setValue(outStockList);
                //mref.setValue(outStockList);

                if (Double.parseDouble(mDues) > 0) {
                    DuesUtilsClass duesUtilsClass = new DuesUtilsClass(order_details.getSellingOrderID(), mItemId.getText().toString(), inStockList.getBrandName(),
                            mSelleingPrice.getText().toString(), mCustomerName.getText().toString(),mCustomerPhone.getText().toString(),
                            customerAddress, UtilsClass.getCurrentTime(), UtilsClass.getCurrentTime(),
                            String.valueOf(Double.parseDouble(mSelleingPrice.getText().toString()) - Double.parseDouble(mDues)),mDues, outStockList.getItemId());

                    mUsersRef.child(UtilsClass.DUES_TABLE).child(outStockList.getItemId()).push().setValue(duesUtilsClass);
                }

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


    private void checkCameraPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) !=  PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            } else {
                Intent scanIntent = new Intent(this, ScannerActivity.class);
                startActivityForResult(scanIntent, UtilsClass.SELL_ITEM_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent scanIntent = new Intent(this, ScannerActivity.class);
                startActivityForResult(scanIntent, UtilsClass.SELL_ITEM_REQUEST_CODE);
            } else {
                return;
            }
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

package firebasetest.ikram.com.firebasetest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
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
 * Created by lenovo on 28-06-2017.
 *
 * UserDetails -> AddItems ,  item add
 *
 * AddItems -> ShowStockList , after update stocks
 */
public class AddItems extends AppCompatActivity implements View.OnClickListener {

    int count;
    EditText mItemId;
    EditText mBrandName;
    EditText mItemPrice;
    EditText mItemQuantity;
    ImageButton scanButton;
    Button mAddButton;
    TextView mDescription;

    TextInputLayout mItemId_layout;
    TextInputLayout mBrandName_layout;
    TextInputLayout mItemPrice_layout;
    TextInputLayout mItemQuantity_layout;


    FirebaseDatabase mDatabase;
    DatabaseReference mUsersRef;
    FirebaseAuth mAuth;

    ArrayList<InStockList> arrayList;
    OrderDetails order_details;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_items);

        count = 0;

        initializeUi();

        arrayList = new ArrayList<InStockList>();

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUsersRef = mDatabase.getReference(UtilsClass.USERS_TABLE_NAME).child(mAuth.getCurrentUser().getUid()); //get current user reference
        dataChangeListener();
    }

    private void initializeUi() {

        mItemId = (EditText)findViewById(R.id.editText_add_item_id);
        mBrandName = (EditText)findViewById(R.id.editText_add_brand_name);
        mItemPrice = (EditText)findViewById(R.id.editText_add_item_price);
        mItemQuantity = (EditText)findViewById(R.id.editText_add_item_quantity);

        mAddButton = (Button)findViewById(R.id.button_add);
        scanButton = (ImageButton) findViewById(R.id.scan_button);
        mDescription = (TextView)findViewById(R.id.description);

        scanButton.setOnClickListener(this);
        mAddButton.setOnClickListener(this);

        mItemId_layout = (TextInputLayout) findViewById(R.id.editText_add_item_id_layout);
        mBrandName_layout = (TextInputLayout) findViewById(R.id.editText_add_brand_name_layout);
        mItemPrice_layout = (TextInputLayout) findViewById(R.id.editText_add_item_price_layout);
        mItemQuantity_layout = (TextInputLayout) findViewById(R.id.editText_add_item_quantity_layout);

        mItemId_layout.setHint("Item id *");
        mBrandName_layout.setHint("Brand Name *");
        mItemPrice_layout.setHint("Item Price *");
        mItemQuantity_layout.setHint("Quantity - Optional");
        mItemQuantity_layout.setVisibility(View.GONE);

    }


    private void dataChangeListener() {

        mUsersRef.child(UtilsClass.USERS_STOCKS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    arrayList.add(datasnapshot.getValue(InStockList.class)); //not proper...need to change
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
                order_details = dataSnapshot.getValue(OrderDetails.class); //not proper...need to change
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("UserDEtails.java", "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_add:
                if (mBrandName.getText().toString().equals("") ||
                        mItemPrice.getText().toString().equals("") || mItemId.getText().toString().equals("")) {
                    mDescription.setText("Please fill all field *");
                    mDescription.setTextColor(Color.RED);
                    return;
                }

                boolean isItemExist = false;
                for (int i = 0; i<arrayList.size(); i++) {
                    if(arrayList.get(i).itemId.equals(mItemId.getText().toString())) {
                        isItemExist = true;
                    }
                }
                if (isItemExist) {
                    mDescription.setText("Item already added in stock");
                    mDescription.setTextColor(Color.RED);
                    return;
                }

                if (mItemQuantity.getText().toString().isEmpty() || mItemQuantity.getVisibility() == View.GONE) {
                    count = 1;
                } else {
                    count =  Integer.valueOf(mItemQuantity.getText().toString());
                }

                order_details.setBuyingOrderID(String.valueOf(Long.parseLong(order_details.getBuyingOrderID())+1));
                mUsersRef.child(UtilsClass.BUYING_SELLING_ORDER_ID).setValue(order_details);


                InStockList inStockList = new InStockList(order_details.getBuyingOrderID(), mItemId.getText().toString(), mBrandName.getText().toString(),
                        mItemPrice.getText().toString() , String.valueOf(count), UtilsClass.getCurrentTime(), String.valueOf(count), UtilsClass.getCurrentTime(),"");

                mUsersRef.child(UtilsClass.USERS_STOCKS).push().setValue(inStockList);

                mDescription.setText(count + " items added in stock");
                mDescription.setTextColor(Color.BLUE);

                mBrandName.setText(null);
                mItemPrice.setText(null);
                mItemId.setText(null);
                mItemQuantity.setText(null);

                break;

            case R.id.scan_button:
                checkCameraPermissions();
                break;

            default:
                break;
        }
    }

    private void checkCameraPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) !=  PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            } else {
                Intent scan_intent = new Intent(this, ScannerActivity.class);
                startActivityForResult(scan_intent, UtilsClass.ADD_ITEM_REQUEST_CODE);
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
                Intent scan_intent = new Intent(this, ScannerActivity.class);
                startActivityForResult(scan_intent, UtilsClass.ADD_ITEM_REQUEST_CODE);
            } else {
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UtilsClass.ADD_ITEM_REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
            mItemId.setText(data.getStringExtra("result"));
        }
    }
}

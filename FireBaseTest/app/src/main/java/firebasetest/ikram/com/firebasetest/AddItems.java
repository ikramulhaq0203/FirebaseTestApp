package firebasetest.ikram.com.firebasetest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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


    FirebaseDatabase mDatabase;
    DatabaseReference mUsersRef;
    FirebaseAuth mAuth;

    ArrayList<StockList> arrayList;
    OrderDetails order_details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_items);

        count = 0;

        initializeUi();

        arrayList = new ArrayList<StockList>();

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
                order_details = dataSnapshot.child(UtilsClass.BUYING_SELLING_ORDER_ID).getValue(OrderDetails.class);
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

                DatabaseReference mUserStockRef = mUsersRef.child(UtilsClass.USERS_STOCKS).push();

                if (mItemQuantity.getText().toString().isEmpty()) {
                    count = 1;
                } else {
                    count =  Integer.valueOf(mItemQuantity.getText().toString());
                }

                order_details.setBuyingOrderID(order_details.getBuyingOrderID()+1);
                mUsersRef.child(UtilsClass.BUYING_SELLING_ORDER_ID).setValue(order_details);

                //int total_price = Integer.valueOf(mItemPrice.getText().toString());
                StockList st = new StockList(order_details.getBuyingOrderID(), mItemId.getText().toString(), mBrandName.getText().toString(), mItemPrice.getText().toString() , count, mUserStockRef.getKey());
                mUserStockRef.setValue(st);

                mDescription.setText(count + " items added in stock");
                mDescription.setTextColor(Color.BLUE);

                mBrandName.setText(null);
                mItemPrice.setText(null);
                mItemId.setText(null);
                mItemQuantity.setText(null);

                break;

            case R.id.scan_button:
                Intent scan_intent = new Intent(this, ScannerActivity.class);
                startActivityForResult(scan_intent, UtilsClass.ADD_ITEM_REQUEST_CODE);
                break;

            default:
                break;
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

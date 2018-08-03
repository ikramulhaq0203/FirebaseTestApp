package firebasetest.ikram.com.firebasetest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import utils.UtilsClass;

/**
 * Created by lenovo on 28-06-2017.
 */
public class StockUpdate extends AppCompatActivity implements View.OnClickListener {

    EditText mItemId;
    EditText mBrandName;
    EditText mItemPrice;
    EditText mItemQuantity;
    ImageButton scanButton;
    Button mUpdateButton;
    TextView mDescription;

    FirebaseDatabase mDatabase;
    DatabaseReference muser_stock_ref;
    FirebaseAuth mAuth;

    StockList st;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_items);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        initializeUiElement();

        muser_stock_ref = mDatabase.getReference(UtilsClass.USERS_TABLE_NAME).child(mAuth.getCurrentUser().getUid()).child(UtilsClass.USERS_STOCKS);
        st = (StockList)getIntent().getSerializableExtra("Myclass");

        setDefautVale();
    }

    private void setDefautVale() {
        if (st != null) {
            mItemId.setText(st.getitemId());
            mBrandName.setText(st.getBrandName());
            mItemPrice.setText(st.getBuyingPrice());
            mItemQuantity.setText(String.valueOf(st.getBuyingQuantity()));
        }
    }

    private void initializeUiElement() {

        mItemId = (EditText)findViewById(R.id.editText_add_item_id);
        mBrandName = (EditText)findViewById(R.id.editText_add_brand_name);
        mItemPrice = (EditText)findViewById(R.id.editText_add_item_price);
        mItemQuantity = (EditText)findViewById(R.id.editText_add_item_quantity);
        mUpdateButton = (Button)findViewById(R.id.button_add);
        scanButton = (ImageButton) findViewById(R.id.scan_button);
        mDescription = (TextView)findViewById(R.id.description);

        scanButton.setOnClickListener(this);
        mUpdateButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_update:

                if (mBrandName.getText().toString().equals("") ||
                        mItemPrice.getText().toString().equals("") || mItemId.getText().toString().equals("")) {
                    mDescription.setText("Please fill all field *");
                    mDescription.setTextColor(Color.RED);
                    return;
                }
                //DatabaseReference mStockRef = muser_stock_ref.child(stock_item_key);
                //StockList st = new StockList(item_number.getText().toString(), item_price.getText().toString(), item_available_status.getText().toString());

                st.setItemId(mItemId.getText().toString());
                st.setBrandName(mBrandName.getText().toString());
                st.setBuyingPrice(mItemPrice.getText().toString());
                st.setBuyingQuantity(Long.valueOf(mItemQuantity.getText().toString()));
                st.setLastUpdateDate();

                muser_stock_ref.setValue(st);

                Intent intent = new Intent(this, InTransaction.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}

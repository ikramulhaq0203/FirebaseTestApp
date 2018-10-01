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
public class InTransactionUpdate extends AppCompatActivity implements View.OnClickListener {

    EditText mItemId;
    EditText mBrandName;
    EditText mItemPrice;
    EditText mItemQuantity;
    ImageButton scanButton;
    Button mUpdateButton;
    TextView mDescription;

    TextInputLayout mItemId_layout;
    TextInputLayout mBrandName_layout;
    TextInputLayout mItemPrice_layout;
    TextInputLayout mItemQuantity_layout;

    FirebaseDatabase mDatabase;
    DatabaseReference muser_stock_ref;
    FirebaseAuth mAuth;

    InStockList inStockList;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_items);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        initializeUiElement();

        inStockList = (InStockList) getIntent().getSerializableExtra("Myclass");
        muser_stock_ref = mDatabase.getReference(UtilsClass.USERS_TABLE_NAME).child(mAuth.getCurrentUser().getUid()).child(UtilsClass.USERS_STOCKS).child(inStockList.getKey());//error


        setDefautVale();
    }

    private void setDefautVale() {
        if (inStockList != null) {
            mItemId.setText(inStockList.getItemId());
            mBrandName.setText(inStockList.getBrandName());
            mItemPrice.setText(inStockList.getBuyingPrice());
            mItemQuantity.setText(inStockList.getBuyingQuantity());
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

        mItemId_layout = (TextInputLayout) findViewById(R.id.editText_add_item_id_layout);
        mBrandName_layout = (TextInputLayout) findViewById(R.id.editText_add_brand_name_layout);
        mItemPrice_layout = (TextInputLayout) findViewById(R.id.editText_add_item_price_layout);
        mItemQuantity_layout = (TextInputLayout) findViewById(R.id.editText_add_item_quantity_layout);

        mItemId_layout.setHint("Item id *");
        mBrandName_layout.setHint("Brand Name *");
        mItemPrice_layout.setHint("Item Price *");
        mItemQuantity_layout.setHint("Quantity - Optional");

        mUpdateButton.setText("Update");

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

                inStockList.setItemId(mItemId.getText().toString());
                inStockList.setBrandName(mBrandName.getText().toString());
                inStockList.setBuyingPrice(mItemPrice.getText().toString());
                inStockList.setBuyingQuantity(mItemQuantity.getText().toString());
                inStockList.setLastUpdateDate(UtilsClass.getCurrentTime());
                muser_stock_ref.setValue(inStockList);

                Intent intent = new Intent(this, InTransaction.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.scan_button:
                checkCameraPermissions();
            default:
                break;
        }
    }

    private void checkCameraPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) !=  PackageManager.PERMISSION_GRANTED) {
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

}

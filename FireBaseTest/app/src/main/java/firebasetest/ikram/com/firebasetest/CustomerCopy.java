package firebasetest.ikram.com.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by lenovo on 28-06-2017.
 *
 * ShowStockList -> CustomerCopy , sell stock
 *
 * CustomerCopy -> ShowStockList , after sell
 */
public class CustomerCopy extends AppCompatActivity implements View.OnClickListener {

    EditText customerName;
    EditText customerPhone;
    EditText sellingPrice;

    Button cancel_button, sell_button;

    StockList stock;
    FirebaseDatabase mDatabase;
    DatabaseReference muser_stock_ref;
    DatabaseReference muser_sold;
    FirebaseAuth mAuth;

    DatabaseReference muser_sold_ref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customercopy);

        initializeUiElement();

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        if (getIntent().getSerializableExtra("Myclass") != null) {
            stock = (StockList)getIntent().getSerializableExtra("Myclass");
        }
        muser_stock_ref = mDatabase.getReference("user_list").child(mAuth.getCurrentUser().getUid()).child("STOCKS").child(stock.getStockItemKey());
        muser_sold = mDatabase.getReference("user_list").child(mAuth.getCurrentUser().getUid());

        muser_sold_ref = mDatabase.getReference("user_list").child(mAuth.getCurrentUser().getUid());
    }

    private void initializeUiElement() {

        customerName = (EditText)findViewById(R.id.editText_customer_name);
        customerPhone = (EditText)findViewById(R.id.editText_customer_phone);
        sellingPrice = (EditText)findViewById(R.id.editText_selling_price);

        cancel_button = (Button)findViewById(R.id.button_cancel);
        sell_button = (Button)findViewById(R.id.button_sell);
        cancel_button.setOnClickListener(this);
        sell_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_cancel:
                finish();
                break;
            case R.id.button_sell:
                Toast.makeText(this, "succesfully ", Toast.LENGTH_SHORT).show();

                stock.setAvalibleStatus("SOLD");
                muser_stock_ref.setValue(stock);

                CustomerDetails customers_details = new CustomerDetails(customerName.getText().toString(), customerPhone.getText().toString(),"na",
                        stock.getitemId(), "na", sellingPrice.getText().toString());

                DatabaseReference sold_db  = muser_sold.child("SOLD_STOCKS").push();
                sold_db.setValue(customers_details);

                Intent intent = new Intent(this, InTransaction.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
        }

    }
}

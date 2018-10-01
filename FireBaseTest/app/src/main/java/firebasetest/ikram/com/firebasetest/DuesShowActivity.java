package firebasetest.ikram.com.firebasetest;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

import utils.UtilsClass;

public class DuesShowActivity extends AppCompatActivity implements View.OnClickListener, EditDialogFrament.EditDialogFramentInterfaceListener {


    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mDuesUserRef;
    ArrayList<DuesUtilsClass> arrayList;
    ListView mlistview;

    IndivitualDuesListAdapter indivitualDuesListAdapter;

    Button cancelButton;
    Button payButton;

    String res;

    TextView mCustomerName;
    TextView mCustomerPhone;
    TextView mItem;
    TextView mPrice;
    TextView mPayment;
    TextView mDues;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indivitaul_dues_details);

        res = getIntent().getStringExtra("prm_key");


        getSupportActionBar().setElevation(0);

        initializeUiElement();

        mAuth = FirebaseAuth.getInstance();
        arrayList = new ArrayList<DuesUtilsClass>();

        mDatabase = FirebaseDatabase.getInstance();
        mDuesUserRef = mDatabase.getReference(UtilsClass.USERS_TABLE_NAME).child(mAuth.getCurrentUser().getUid()).child(UtilsClass.DUES_TABLE);

        mDuesUserRef.child(res).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot parent : dataSnapshot.getChildren()) {
                    arrayList.add(parent.getValue(DuesUtilsClass.class));
                }

                uiUpdate();
                indivitualDuesListAdapter = new IndivitualDuesListAdapter(DuesShowActivity.this, R.layout.dues_row, arrayList);
                mlistview.setAdapter(indivitualDuesListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initializeUiElement() {

        cancelButton = (Button)findViewById(R.id.cancel_button);
        payButton = (Button) findViewById(R.id.payment_button);
        mlistview = (ListView) findViewById(R.id.indivitual_list_item);

        cancelButton.setOnClickListener(this);
        payButton.setOnClickListener(this);

        mCustomerName = (TextView) findViewById(R.id.customer_name);
        mCustomerPhone = (TextView) findViewById(R.id.customer_phone);
        mItem = (TextView) findViewById(R.id.item_name);
        mPrice = (TextView) findViewById(R.id.item_price);
        mPayment = (TextView) findViewById(R.id.payment);
        mDues = (TextView) findViewById(R.id.dues);
    }

    private void uiUpdate() {
        DuesUtilsClass dummy = arrayList.get(0);
        mCustomerName.setText(dummy.getCustomerName());
        mCustomerPhone.setText(dummy.getCustomerPhone());
        mItem.setText(dummy.getBrandName() + " / " +dummy.getItemId());
        mPrice.setText(dummy.getSellingPrice());
        mPayment.setText(dummy.getPaidAmount());
        mDues.setText(dummy.getDueAmount());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payment_button:
                EditDialog();
                break;
            case R.id.cancel_button:
                finish();
                break;

        }
    }

    private void EditDialog() {
        DialogFragment dialogFrament = new EditDialogFrament();
        dialogFrament.show(getFragmentManager(), "EditDialogFrament");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String payment) {
        Toast.makeText(getApplicationContext(), "Payment done" +" "+payment, Toast.LENGTH_SHORT).show();

        if (Double.parseDouble(payment) > 0) {
            DuesUtilsClass dummy = arrayList.get(arrayList.size()-1);
            DuesUtilsClass duesUtilsClass = new DuesUtilsClass(dummy.getSellingOrderID(), dummy.getItemId(), dummy.getBrandName(),
                    dummy.getSellingPrice(), dummy.getBrandName(), dummy.getCustomerPhone(),
                    dummy.getCustomerAddress(), UtilsClass.getCurrentTime(), UtilsClass.getCurrentTime(),
                    payment, String.valueOf(Double.parseDouble(dummy.getDueAmount()) - Double.parseDouble(payment)), dummy.getCustomerPhone());

            mDuesUserRef.child(res).push().setValue(duesUtilsClass);
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}

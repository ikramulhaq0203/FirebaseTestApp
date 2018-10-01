package firebasetest.ikram.com.firebasetest;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import utils.UtilsClass;

/**
 * Created by lenovo on 31-03-2018.
 */

public class DuesDetails extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference muserStockRef;
    ArrayList<DuesUtilsClass> arrayList;
    DuesListAdapter duesListAdapter;
    ListView mlistview;

    TextView mTotalDueAmoutnTextView;

    HashMap<String, Object> due_amount_map;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dues_details);

        getSupportActionBar().setElevation(0);

        initializeUiElement();

        mAuth = FirebaseAuth.getInstance();
        arrayList = new ArrayList<DuesUtilsClass>();
        due_amount_map = new HashMap<>();


        mDatabase = FirebaseDatabase.getInstance();
        muserStockRef = mDatabase.getReference(UtilsClass.USERS_TABLE_NAME).child(mAuth.getCurrentUser().getUid()).child(UtilsClass.DUES_TABLE);

        muserStockRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();

                for (DataSnapshot parent : dataSnapshot.getChildren()) {
                    int count = 1;
                    for (DataSnapshot child : parent.getChildren()) {
                        //child.getValue()
                        String price = (String)child.child("sellingPrice").getValue();
                        if (count == parent.getChildrenCount()) {
                            arrayList.add(child.getValue(DuesUtilsClass.class));
                        }
                        count++;
                    }
                }

                duesListAdapter = new DuesListAdapter(DuesDetails.this, R.layout.dues_row, arrayList);
                mlistview.setAdapter(duesListAdapter);

                mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(DuesDetails.this, DuesShowActivity.class);
                        intent.putExtra("prm_key", duesListAdapter.getItem(position).getItemId());
                        startActivity(intent);
                    }
                });


                setTotalDue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setTotalDue() {
        double total_dues_amount = 0;
        for (int i = 0; i< arrayList.size(); i++) {
            if (Double.parseDouble(arrayList.get(i).getDueAmount()) > 0) {
                total_dues_amount =  total_dues_amount + (double) (Double.parseDouble(arrayList.get(i).getDueAmount()));
            }

        }
        mTotalDueAmoutnTextView.setText("  Total Due Amount = "+total_dues_amount);
    }

    private void initializeUiElement() {
        mlistview = (ListView)findViewById(R.id.list_item);
        mTotalDueAmoutnTextView = (TextView)findViewById(R.id.total_dues_amount);
    }
}

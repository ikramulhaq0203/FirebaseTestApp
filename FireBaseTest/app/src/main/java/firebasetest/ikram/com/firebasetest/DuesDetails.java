package firebasetest.ikram.com.firebasetest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
    ArrayList<SoldStockDetails> arrayList;
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
        arrayList = new ArrayList<SoldStockDetails>();
        due_amount_map = new HashMap<>();


        mDatabase = FirebaseDatabase.getInstance();
        muserStockRef = mDatabase.getReference(UtilsClass.USERS_TABLE_NAME).child(mAuth.getCurrentUser().getUid()).child(UtilsClass.USERS_SOLD_STOCKS);

        muserStockRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    if (datasnapshot.getValue(SoldStockDetails.class).getDueAmount() > 0) {
                        arrayList.add(datasnapshot.getValue(SoldStockDetails.class));
                    }



                    /*if (datasnapshot.hasChildren()) {
                        for (DataSnapshot datasnapshot2 : datasnapshot.getChildren()) {
                            String s = datasnapshot2.getValue(String.class);
                            due_amount_map.put(datasnapshot2.getKey(), s);
                        }
                    }*/
                }

                //HashMap<String, Object> map = null;

                due_amount_map.clear();
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {
                    DataSnapshot item = items.next();
                    due_amount_map = (HashMap<String, Object>) item.getValue();
                    //due_amount_map.put("items", "items");
                }
                duesListAdapter = new DuesListAdapter(DuesDetails.this, R.layout.dues_row, arrayList);
                mlistview.setAdapter(duesListAdapter);
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
            if (arrayList.get(i).getDueAmount() > 0) {
                total_dues_amount =  total_dues_amount + (double) (Double.valueOf(arrayList.get(i).getDueAmount()));
            }

        }
        mTotalDueAmoutnTextView.setText("  Total Due Amount = "+total_dues_amount);
    }

    private void initializeUiElement() {
        mlistview = (ListView)findViewById(R.id.list_item);
        mTotalDueAmoutnTextView = (TextView)findViewById(R.id.total_dues_amount);
    }
}

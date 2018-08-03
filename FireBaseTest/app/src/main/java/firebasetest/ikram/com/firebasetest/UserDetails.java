package firebasetest.ikram.com.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import utils.UtilsClass;


/**
 * Created by lenovo on 25-06-2017.
 * 2.
 * HomeActivity -> UserDetails , for already signed user
 * HomeActivity -> UserDetails , after successfully signed
 * RegistrationUser -> UserDetails , after successfully create new users
 *
 * UserDetails -> HomeActivity , after successfully signed out
 * UserDetails -> AddItems ,  item add
 * UserDetails -> ShowStockList , ShowStockList
 * UserDetails -> SoldStockList , SoldStockList
 *
 */
public class UserDetails extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView welcomeMessage;

    FirebaseAuth mAuth;

    FirebaseDatabase mDatabase;
    DatabaseReference mCurrentUserRef;
    DatabaseReference mCurrentUserOrderRef;

    ArrayList<String> mGridAraylist;
    ArrayAdapter<String> mlistAdapter;
    String []grid_data = {"Add", "Sell", "In Transactions", "Out Transactions", "Current Stocks", "Dues Details"};
    GridView mGridView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.userdetails);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        initializeUiElement();

        mGridAraylist = new ArrayList<>();
        for (int i = 0; i < grid_data.length; i++) {
            mGridAraylist.add(grid_data[i]);
        }
        mlistAdapter = new CustomListAdapter(this, R.layout.mlist_row, mGridAraylist);
        mGridView = (GridView) findViewById(R.id.list_item);
        mGridView.setAdapter(mlistAdapter);
        mGridView.setOnItemClickListener(this);


        mCurrentUserRef = mDatabase.getReference(UtilsClass.USERS_TABLE_NAME).child(mAuth.getCurrentUser().getUid());

        //mCurrentUserOrderRef.setValue(new OrderDetails(UtilsClass.BUYING_ORDER_ID, UtilsClass.SELLIGN_ORDER_ID));
        dataChangeListener();
    }

    private void dataChangeListener() {

        mCurrentUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get StockList object and use the values to update the UI
                StockList st_list = dataSnapshot.getValue(StockList.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("UserDEtails.java", "onCancelled", databaseError.toException());
            }
        });
    }

    private void initializeUiElement() {

        welcomeMessage = (TextView)findViewById(R.id.welcome_message);
        welcomeMessage.setVisibility(View.VISIBLE);
        welcomeMessage.setText("Welcome "+mAuth.getCurrentUser().getEmail());
    }

    @Override
    protected void onStart() {
        super.onStart();
        welcomeMessage.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_signout:
                mAuth.signOut();
                Intent homeIntent = new Intent(UserDetails.this, HomeActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch (position) {
                case 0:
                    Intent intent_add = new Intent(UserDetails.this, AddItems.class);
                    intent_add.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent_add);
                    break;
                case 1:
                    //welcomeMessage.setVisibility(View.GONE);
                    Intent intent2 = new Intent(UserDetails.this, SellActivity.class);
                    startActivity(intent2);
                    break;
                case 2:
                    //welcomeMessage.setVisibility(View.GONE);
                    Intent intent1 = new Intent(UserDetails.this, InTransaction.class);
                    startActivity(intent1);
                    break;

                case 3:
                    Intent intent3 = new Intent(UserDetails.this, SoldStock.class);
                    startActivity(intent3);
                    break;

                case 4:
                    Intent intent4 = new Intent(UserDetails.this, CurrentStocks.class);
                    startActivity(intent4);
                    break;

                case 5:
                    Intent intent5 = new Intent(UserDetails.this, DuesDetails.class);
                    startActivity(intent5);
                    break;

                default:
                    break;
            }
    }
}

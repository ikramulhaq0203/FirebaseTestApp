package firebasetest.ikram.com.firebasetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import utils.UtilsClass;

/**
 * Created by lenovo on 25-06-2017.
 * 2
 * HomeActivity -> RegistrationUser , for new user
 * RegistrationUser -> UserDetails , after successfully create new users
 * RegistrationUser -> UtilityClass , for user table name
 */

public class RegistrationUser extends AppCompatActivity implements View.OnClickListener {

    EditText mEditTextEmail, mEditTextPassword;
    Button mJoinNowButton;

    FirebaseAuth mAuth;
    FirebaseDatabase mDataBase;
    DatabaseReference mUsersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.registration_page);

        initializeUIElement();

        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance();
        mUsersList = mDataBase.getReference(UtilsClass.USERS_TABLE_NAME);
    }

    private void initializeUIElement() {

        mEditTextEmail = (EditText)findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText)findViewById(R.id.editTextPassword);
        mJoinNowButton = (Button) findViewById(R.id.signupButton);
        mJoinNowButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signupButton:
                createNewUsers(mEditTextEmail.getText().toString(), mEditTextPassword.getText().toString());
                break;

            default:
                break;
        }
    }

    private void createNewUsers(final String email, final String password) {


        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "invalid emal or password", Toast.LENGTH_SHORT).show();

        } else {

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrationUser.this, new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        String uid = mAuth.getCurrentUser().getUid();//unique id for every users
                        NewUser userDetails = new NewUser(email, password);
                        DatabaseReference newUserDetails = mUsersList.child(uid).child(UtilsClass.USERS_DETAILS);
                        DatabaseReference mCurrentUserOrderRef = mUsersList.child(uid).child(UtilsClass.BUYING_SELLING_ORDER_ID);
                        mCurrentUserOrderRef.setValue(new OrderDetails(UtilsClass.BUYING_ORDER_ID, UtilsClass.SELLIGN_ORDER_ID));
                        newUserDetails.setValue(userDetails);

                        Intent intent = new Intent(RegistrationUser.this, UserDetails.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        Toast.makeText(RegistrationUser.this, "Account created.", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {

                        Toast.makeText(RegistrationUser.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}

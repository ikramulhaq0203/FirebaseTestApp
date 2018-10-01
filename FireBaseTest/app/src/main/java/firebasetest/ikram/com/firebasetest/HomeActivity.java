package firebasetest.ikram.com.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by lenovo on 25-06-2017.
 * 1. First Activity
 *  HomeActivity -> RegistrationUser , for new user registrantion
 *  HomeActivity -> UserDetails , for already signed user
 *  HomeActivity -> UserDetails , after successfully signed
 *  UserDetails -> HomeActivity , after successfully signed out
 */


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    EditText signInEmail, signInPassword;
    Button signInButton, signUpButton;

    FirebaseAuth mAuth;

    TextInputLayout mSigninEmail_layout;
    TextInputLayout mSigninPassword_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_layout);

        initializeUiElement();

        mAuth = FirebaseAuth.getInstance();
    }

    private void initializeUiElement() {

        signInEmail = (EditText) findViewById(R.id.editTextSignEmail);
        signInPassword = (EditText) findViewById(R.id.editTextSignPassword);

        signInButton = (Button) findViewById(R.id.signinButton);
        signUpButton = (Button) findViewById(R.id.signupButton);

        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

        mSigninEmail_layout = (TextInputLayout) findViewById(R.id.editTextSignEmail_layout);
        mSigninPassword_layout = (TextInputLayout) findViewById(R.id.editTextSignPassword_layout);

        mSigninEmail_layout.setHint("Email");
        mSigninPassword_layout.setHint("Password");
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            Toast.makeText(HomeActivity.this, "Already signed in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, UserDetails.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.signinButton:
                userSignIn(signInEmail.getText().toString(), signInPassword.getText().toString());
                break;
            case R.id.signupButton:
                userSignUp();
                break;
            default:
                break;
        }

    }

    private void userSignUp() {

        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, RegistrationUser.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

    private void userSignIn(String email, String password) {

        if (email.isEmpty() || password.isEmpty()) {

            Toast.makeText(this, "Please enter valid email and password", Toast.LENGTH_SHORT).show();

        } else {

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(HomeActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this, UserDetails.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(HomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
}

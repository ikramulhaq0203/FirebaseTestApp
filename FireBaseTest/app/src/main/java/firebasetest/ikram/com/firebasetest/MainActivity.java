package firebasetest.ikram.com.firebasetest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

/*
    Button upload_btn;
    Button download_btn;
    Button database_button;

    ImageView uploadImage;
    TextView database_txt;

    StorageReference mStorage;
    UploadTask uploadTask;

    FirebaseDatabase database;
    DatabaseReference mUser;
    private FirebaseAuth mAuth;

    FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_stock);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

      *//*  upload_btn = (Button)findViewById(R.id.upload_btn);
        upload_btn.setOnClickListener(this);

        download_btn = (Button)findViewById(R.id.download_btn);
        download_btn.setOnClickListener(this);

        database_button = (Button)findViewById(R.id.data_base);
        database_button.setOnClickListener(this);

        uploadImage = (ImageView)findViewById(R.id.upload_image);

        database_txt = (TextView)findViewById(R.id.data_base);



        database = FirebaseDatabase.getInstance();//data base

        mUser = database.getReference("users123");

        mStorage = FirebaseStorage.getInstance().getReference();*//*

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      *//*  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*//*


       // mAuth = FirebaseAuth.getInstance();



    }

    @Override
    protected void onStart() {
       *//* super.onStart();

        //FirebaseUser current_user1 = mAuth.getCurrentUser();
        //updateUi(current_user1);
        mAuth.addAuthStateListener(MainActivity.this);*//*



    }

    private void updateUi(FirebaseUser current_user1) {

        if (current_user1 != null) {
            int i = 1;
            database_txt.setText(""+i);


            Log.d("ikram_test", "createUserWithEmail:success = " +mAuth.getCurrentUser().getUid());

            NewUser it = new NewUser("mark", "lsa");
            //database.getReference("users").push().setValue(it);

            DatabaseReference newuser = mUser.child(mAuth.getCurrentUser().getUid());
            newuser.child("name").setValue(mAuth.getCurrentUser().getEmail());
            newuser.child("pass").setValue(mAuth.getCurrentUser().getUid());

        }
        else {
            int j = 5;
            database_txt.setText(""+j);
            NewUser ite = new NewUser("ikram7868mr@gmail.com", "123456");
            createuser(ite.userName, ite.userPassword);
        }
    }

    private void createuser(String email, String password) {

      *//*  mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });*//*
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.upload_btn:
                Toast.makeText(MainActivity.this, "upload start", Toast.LENGTH_SHORT).show();
                startupload();
                break;
            case R.id.download_btn:
                Toast.makeText(MainActivity.this, "download start", Toast.LENGTH_SHORT).show();
                downloadStart();
                break;

            case R.id.data_base:
                Toast.makeText(MainActivity.this, "data base", Toast.LENGTH_SHORT).show();
                database_func();
                break;

            default:
                break;
        }


    }

    private void database_func() {


        NewUser items = new NewUser("Mark123", "USA123");


        //myRef = database.getReference("message");
        //myRef.setValue("Hello, World change!");

        //myRef = database.getReference("user");
        //myRef.setValue("Hello, World change!");

        //myRef.push().setValue("wee done");

        database.getReference("users").push().setValue(items);





        database.getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                NewUser ite = dataSnapshot.getValue(NewUser.class);
                database_txt.setText(ite.userName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void downloadStart() {

*//*        gs://fir-test-f016f.appspot.com



        StorageReference pathReference = mStorage.child("images/stars.jpg");

// Create a reference to a file from a Google Cloud Storage URI
        StorageReference gsReference = mStorage.getReferenceFromUrl("gs://bucket/images/stars.jpg");

// Create a reference from an HTTPS URL
// Note that in the URL, characters are URL escaped!
        StorageReference httpsReference = mStorage.getReferenceFromUrl("https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg");

        StorageReference islandRef = mStorage.child("images/island.jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });*//*
    }

    private void startupload() {


        // Create a storage reference from our app
        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = mStorage.child("Photo").child("image1.jpeg");

       // Create a reference to 'images/mountains.jpg'

        String path = Environment.getExternalStorageDirectory().getPath().toString() + "/fireapp/image1.jpeg";
        StorageReference mountainImagesRef = mStorage.child( path +"/fireapp/image1.jpeg");


      // While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

        InputStream stream =null;
        try {
            stream = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


*//*        // Get the data from an ImageView as bytes
        uploadImage.setDrawingCacheEnabled(true);
        uploadImage.buildDrawingCache();
        Bitmap bitmap = uploadImage.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();*//*

        uploadTask = mountainsRef.putStream(stream);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads

                Toast.makeText(MainActivity.this, "uploaded failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                Toast.makeText(MainActivity.this, "Sucessfully uploaded", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

        if (firebaseAuth.getCurrentUser() == null) {

            Intent intent = new Intent(this, RegistrationUser.class);
            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        else {

            firebaseAuth.signOut();

            Intent intent = new Intent(this, RegistrationUser.class);
            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }


  *//*  @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null) {

            Intent intent = new Intent(this, RegistrationUser.class);
            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        else {

            firebaseAuth.getInstance().signOut();

            Intent intent = new Intent(this, RegistrationUser.class);
            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }*/
}

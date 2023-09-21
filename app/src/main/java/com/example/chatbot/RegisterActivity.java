package com.example.chatbot;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText username1,email1,password1,confirmPassword1,phone1;
    Button next;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore firestore;
    String userID;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username1=(EditText) findViewById(R.id.signup_name);
        email1=(EditText) findViewById(R.id.signup_email);
        password1=(EditText) findViewById(R.id.signup_passcode);
        confirmPassword1=(EditText) findViewById(R.id.signup_confirm);
        next=(Button) findViewById(R.id.proceedbtn);
        login=(TextView) findViewById(R.id.loginpage);
        final ProgressBar progressBar=findViewById(R.id.progressBar);
        FirebaseApp.initializeApp(this);
        mFirebaseAuth=FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference("users");
//                reference.setValue()
                String username_user =username1.getText().toString();
                String email_user =email1.getText().toString();
                String password_user =password1.getText().toString();
                String confirmpass_user =confirmPassword1.getText().toString();

                if(TextUtils.isEmpty(username_user) || TextUtils.isEmpty(email_user) || TextUtils.isEmpty(password_user)||TextUtils.isEmpty(confirmpass_user)  ){
                    Toast.makeText(RegisterActivity.this, "Please fill all the require fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password_user.length() < 8){
                    password1.setError("Password must contain atleast 8 characters");
                    return;
                }
                if(!(TextUtils.equals(password_user, confirmpass_user))){
                    password1.setError("Passwords Do not Match");
                    return;
                }
                //registering the user in firebase
                mFirebaseAuth.createUserWithEmailAndPassword(email_user, password_user).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            userID = mFirebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firestore.collection("users").document(userID);


                            Map<String,Object> user = new HashMap<>();
                            user.put("username", username_user);
                            user.put("email", email_user);
                            user.put("password",password_user);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG ,"onSuccess: User Profile is created for"+userID);


                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG ,"Error: problem for"+userID+" ",e);
                                            Toast.makeText(RegisterActivity.this, "Error "+e, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            Toast.makeText(RegisterActivity.this, "User Created Successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            finish();


                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Some Error Occurred" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }
}
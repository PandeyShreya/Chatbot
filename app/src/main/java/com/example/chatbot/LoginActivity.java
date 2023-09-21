package com.example.chatbot;

import static com.example.chatbot.R.id.*;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatbot.R.id;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp; // Import FirebaseApp
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

public class LoginActivity extends AppCompatActivity {
    TextView register, forgot_password;
    EditText email_id, password;
    Button login_button;
    FirebaseAuth mFirebaseAuth;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    ImageView twitterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register=findViewById(registerpage);
        email_id = findViewById(login_email);
        password = findViewById(login_passcode);
        forgot_password = findViewById(forgotpass);
        twitterBtn=findViewById(R.id.twitter_btn);
        login_button = findViewById(loginbtn);
        googleBtn=findViewById(google_btn);
        FirebaseApp.initializeApp(this);
        mFirebaseAuth = FirebaseAuth.getInstance();

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SignIn();

                GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

                gsc =GoogleSignIn.getClient(LoginActivity.this,gso);
                Intent intent =gsc.getSignInIntent();
                startActivityForResult(intent,100);



            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = email_id.getText().toString().trim();
                String Password= password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    email_id.setError("Email is Required!");
                    return;
                }
                if(TextUtils.isEmpty(Password)) {
                    password.setError("Password is Required!");
                    return;
                }

                //Authenticate the user
                mFirebaseAuth.signInWithEmailAndPassword(email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged in Successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Some Error Occurred" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setMessage("Enter your Email to receive reset link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //extract the email and send reset link

                        String mail = resetMail.getText().toString();
                        mFirebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(LoginActivity.this, "Reset Link sent to your Email", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "ERROR!  Reset Link is Not sent" +e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });
                passwordResetDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //close the dialog
                    }
                });

                passwordResetDialog.create().show();
            }
        });

        twitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OAuthProvider.Builder provider= OAuthProvider.newBuilder("twitter.com");
                provider.addCustomParameter("lang","en");

                Task<AuthResult> pendingResultTask =mFirebaseAuth.getPendingAuthResult();
                if(pendingResultTask!=null){
                    pendingResultTask.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this,"Login Failed due to "+e,Toast.LENGTH_SHORT).show();
                                }
                            });
                }else{
                    mFirebaseAuth.startActivityForSignInWithProvider(/**/LoginActivity.this, provider.build())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(LoginActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this,"Login Failed due to "+e,Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }

    private void SignIn() {
        Intent intent =gsc.getSignInIntent();
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                task.getResult(ApiException.class);
                GoogleActivity();
            }catch (ApiException e){
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();


            }

        }
    }

    private void GoogleActivity() {
        finish();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        
    }

    public void register(View view) {
            finish();
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }
}
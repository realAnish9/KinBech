package com.example.kinbech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button mloginButton,mregisterButton,mforgotBtn;
    private EditText mfullName,mEmail,mpassword,mphone;
    private FirebaseAuth mAuth;
    ProgressBar mprogressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mloginButton=findViewById(R.id.loginButtonMain);
        mfullName=findViewById(R.id.nameInput);
        mEmail=findViewById(R.id.emailInput);
        mpassword=findViewById(R.id.passwordInput);
        mphone=findViewById(R.id.passwordInput);
        mregisterButton=findViewById(R.id.registerButtonMain);
        mforgotBtn=findViewById(R.id.resetPasswordBtn);

        mAuth=FirebaseAuth.getInstance();
        mprogressBar=findViewById(R.id.progressBar);

        //check if account exists already
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }

        mregisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Password is Required");
                    return;
                }
                if(password.length()<8){
                    mpassword.setError("Password must be more than 8 characters");
                }

                mprogressBar.setVisibility(View.VISIBLE); // show the progressbar

                //register user in firebase
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        }else{
                            Toast.makeText(MainActivity.this, "Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        mloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
    }

    public void openLoginActivity() {
        Intent intent=new Intent(this, loginActivity.class);
        startActivity(intent);
    }
}
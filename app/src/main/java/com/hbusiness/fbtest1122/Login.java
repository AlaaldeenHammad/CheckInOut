package com.hbusiness.fbtest1122;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button LogB;
    Toolbar toolB;
    EditText UserN,Pass;
    String email,password;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        linearLayout=findViewById(R.id.Linear);
        mAuth = FirebaseAuth.getInstance();
        LogB=findViewById(R.id.Login);
        UserN=findViewById(R.id.UserName);
        Pass=findViewById(R.id.Password);

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (mAuth.getCurrentUser()!=null){
            Intent intent=new Intent(Login.this,MainActivity.class);
            startActivity(intent);
        }
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void login(View view) {
        if (String.valueOf(UserN.getText()).isEmpty()){
            UserN.setError("Email is required !");
        return;
        }
        if (String.valueOf(Pass.getText()).isEmpty()){
            Pass.setError("Enter your password");
        return;
        }
        email=UserN.getText().toString();
        password=Pass.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Login.this, "Login completed", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent=new Intent(Login.this,MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
package com.example.ThirdYearProject;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity{
    private EditText email, password;
    private Button loginButtons;
    private FirebaseAuth fAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // checks to see if the current user is already logged in
        if (user != null) {
            // if hes already logged in redirect to the homepage
            Intent i = new Intent(LoginScreen.this, HomeScreen.class);
            finish(); // prevents incorrect back button redirection
            startActivity(i);
        }
        // collecting all of the IDs
        password = findViewById(R.id.loginpassword);
        email = findViewById(R.id.loginemail);
        loginButtons = findViewById(R.id.loginButton);
        fAuth = FirebaseAuth.getInstance();

        loginButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordCon = password.getText().toString().trim();
                String emailCon = email.getText().toString().trim();
                if(TextUtils.isEmpty(emailCon)){ // if an email hasn't been entered
                    email.setError("An email is required");
                    return;
                }
                if (TextUtils.isEmpty(passwordCon)){ // if a password hasn't been entered
                    password.setError("Password field can not be empty");
                    return;
                }
                fAuth.signInWithEmailAndPassword(emailCon,passwordCon).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(LoginScreen.this, "Login Successfull", Toast.LENGTH_SHORT ).show();
                            Intent sendUserCreds = new Intent(LoginScreen.this, HomeScreen.class);

                            String clubNameET = "ClubHub";
                            String clubName = String.valueOf(clubNameET);
                            sendUserCreds.putExtra("clubName",clubName);

                            startActivity(sendUserCreds);
                            finish();
                        }
                        else{
                            Toast.makeText(LoginScreen.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



    }




    public void OnSendUsersName() {
        }

    public void RegisterNewUser(View view) {
        Intent createNewUser = new Intent(this, RegistrationScreen.class);
        startActivity(createNewUser);

    }

    public void ForgotPassword(View view) {
        Intent forgotPassword =  new Intent(this, ForgotPassword.class);
        startActivity(forgotPassword);
    }
}

package com.example.ThirdYearProject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegistrationScreen extends AppCompatActivity {
    private EditText email, password, club, password2, pitches, number, division;
    private Button registrationButton;
    private FirebaseAuth fAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;
    private Button pitchButton;
    private long lastClickTime = 0;
    private String pitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrationscreen);
        //id collection
        email = findViewById(R.id.emailText);
        club = findViewById(R.id.clubname);
        password = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        pitches = findViewById(R.id.pitches);
        number = findViewById(R.id.ClubNumber);
        final Spinner divisionSpinner = findViewById(R.id.CLubDivision);
        fAuth = FirebaseAuth.getInstance();
        registrationButton = findViewById(R.id.regButton);
        pitchButton = findViewById(R.id.AddPitches);



        final ArrayList<String> pitchList = new ArrayList<String>();
        pitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adding pitches to the db
                pitch = pitches.getText().toString().trim();
                if( !pitch.equals("")){
                    pitchList.add(pitch);
                    pitches.setText("");
                    Toast.makeText(RegistrationScreen.this, "Pitch added", Toast.LENGTH_SHORT).show();
                }
                else{
                    pitches.setError("Please enter a pitch");
                }


            }
        });

        databaseReference = firebaseDatabase.getInstance().getReference("Club");
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }
                //collecting my values after the onclick
                lastClickTime = SystemClock.elapsedRealtime();
                final String clubInside = club.getText().toString().trim();
                final String emailInside = email.getText().toString().trim();
                String passwordInside = password.getText().toString().trim();
                String passwordInside2 = password2.getText().toString().trim();
                final String divisionInside = divisionSpinner.getSelectedItem().toString();
                final String numberInside = number.getText().toString().trim();
                final String pitchInside =  pitches.getText().toString().trim();
                // check check if all fields have been entered
                if(TextUtils.isEmpty(clubInside)){
                    club.setError("An email is required");
                    return;
                }
                if(TextUtils.isEmpty(emailInside)){
                    email.setError("An email is required");
                    return;
                }
                else if (TextUtils.isEmpty(passwordInside)){
                    password.setError("Password field can not be empty");
                    return;
                }
                else if (!passwordInside2.equals(passwordInside)){
                    password.setError("Passwords must match");
                    return;
                }
                else if (divisionInside.equals("Select a Division")){
                    Toast.makeText(RegistrationScreen.this,"A division must be chosen", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(numberInside)){
                    number.setError("A number is required");
                }
                else {
                    fAuth.createUserWithEmailAndPassword(emailInside, passwordInside).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // if registration is successful upload the club details to the db and move to the login screen
                                Toast.makeText(RegistrationScreen.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                                Club information = new Club(clubInside, emailInside, pitchList, divisionInside, numberInside);
                                firebaseDatabase.getInstance().getReference("Club").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(information);
                                finish();
                            } else {
                                Toast.makeText(RegistrationScreen.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }



            }
        });

    }

}

package com.example.ThirdYearProject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class ChallengeRequestScreen extends Activity implements AdapterView.OnItemSelectedListener {
    private DatePickerDialog picker;
    private TextView eText;
    private Button btnGet;
    private TextView tvw;
    private Button challengeButton;
    private DatabaseReference reff;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private Challenge challenge;
    private String name, yourClubName,iD;
    private ArrayList timeListInner;
    private ArrayList<String> opTimeList;
    private SimpleDateFormat dateFormat;
    private Calendar calendar;
    private Date dateConv;
    private Spinner pitchSpinner;
    private long lastClickTime = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_request_screen);
        opTimeList = new ArrayList<String>();
        opTimeList.add("Avoid empty error");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width, (int)( height *.8));


        pitchSpinner = findViewById(R.id.pitchSpinner);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            timeListInner.add("Avoid null error");
            iD = "empty";
            name = "empty";
            yourClubName = "empty";
        } else {
            timeListInner = bundle.getStringArrayList("timeList");
            iD = bundle.getString("Opposing teams id");
            name = bundle.getString("OpposingTeamName");
            yourClubName = bundle.getString("clubName");
        }
        TextView top = findViewById(R.id.topbarChal);
        String topCon = "Challenge: " + name;
        top.setText(topCon);


        eText=(TextView) findViewById(R.id.date);
        eText.setInputType( InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog( ChallengeRequestScreen.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


        challengeButton = findViewById(R.id.ChallengeButton);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final String userid = user.getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("Challenges");

        challengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // preventing double, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }
                calendar = Calendar.getInstance();
                dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                final Date todaysDate = calendar.getTime();

                lastClickTime = SystemClock.elapsedRealtime();
                final String pitchInside = pitchSpinner.getSelectedItem().toString();
                final String date = eText.getText().toString().trim();
                try {
                    dateConv = dateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                firebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DataSnapshot chall = dataSnapshot.child("Challenges");
                        for (DataSnapshot messageSnapshot : chall.getChildren()) {

                            for (DataSnapshot insideUsers : messageSnapshot.getChildren()) {

                                String iD = insideUsers.child("challengedTeam").getValue(String.class);
                                String date2 = insideUsers.child("date").getValue(String.class);
                                String challengingTeam = insideUsers.child("challengingTeam").getValue(String.class);
                                if (userid.equals(challengingTeam)) {
                                    Log.d(TAG, date2 +  "IMPORTANT" );
                                    if (!timeListInner.contains(date2)){
                                        timeListInner.add(date2);
                                    }

                                }
                            }
                        }
                        DataSnapshot act = dataSnapshot.child("Active");
                        for (DataSnapshot messageSnapshot : act.getChildren()) {
                            String iD1 = messageSnapshot.child("teamOneID").getValue(String.class);
                            String iD2 = messageSnapshot.child("teamTwoID").getValue(String.class);
                            String date = messageSnapshot.child("date").getValue(String.class);


                            if( iD1.equals(iD) || iD2.equals(iD)){
                                opTimeList.add(date);
                            }



                        }
                        if (dateConv == null) {
                            eText.setError("Please set a date");
                            Toast.makeText(ChallengeRequestScreen.this, "Please set a date", Toast.LENGTH_SHORT).show();
                        }
                        else if (timeListInner.contains(date)){
                            eText.setError("You currently have a game scheduled for this date");
                            Toast.makeText(ChallengeRequestScreen.this, "You currently have a game scheduled for this date", Toast.LENGTH_SHORT).show();

                        }
                        else if(dateConv.compareTo(todaysDate) < 0){
                            eText.setError("Please select a date in the future");
                            Toast.makeText(ChallengeRequestScreen.this, "Please select a date in the future", Toast.LENGTH_SHORT).show();

                        }
                        else if (opTimeList.contains(date)){
                            eText.setError("Opposing team currently has a game scheduled for this date");
                            Toast.makeText(ChallengeRequestScreen.this, "Opposing team currently has a game scheduled for this date", Toast.LENGTH_SHORT).show();
                            CreateChallengeCloseScreen();
                        }


                        else {
                            timeListInner.add(date);
                            Log.d(TAG, pitchInside + "   " + iD + " " + userid + " " + yourClubName + " " + name);
                            challenge = new Challenge(pitchInside, date, iD, userid, yourClubName, name);
                            reff.push().child(userid).setValue(challenge);

                            Toast.makeText(ChallengeRequestScreen.this, "Challenge successful", Toast.LENGTH_SHORT).show();
                            CreateChallengeCloseScreen();

                        }
                                }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


            }
        });

    }

    public void CreateChallengeCloseScreen() {
        finish();

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // when item selection is changed this method called
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
// when no item is selected this method call for ex: remove selected item from the list
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}



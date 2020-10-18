package com.example.ThirdYearProject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static android.content.ContentValues.TAG;

public class HomeScreen extends AppCompatActivity {
    private String clubname, clubID, Division, userID,division;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reff;
    private FirebaseAuth firebaseAuth;
    private Button createBroadcast;
    private List<String> a;
    private Button SwitchToGameScreen;
    private Button SwitchToClubListScreen;
    private long lastClickTime = 0;
    private Button logout;
    private TextView clubanmeBanner;


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        a = new ArrayList<String>();

        reff = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final ArrayList<String> timeList = new ArrayList<String>();
        userID =  user.getUid();
        // collecting all the needed view IDs
        clubanmeBanner = findViewById(R.id.TeamName);
        final LinearLayout layout = findViewById(R.id.progress_Bar);
        final LinearLayout layout1 = findViewById(R.id.ClubSearchButton);
        final LinearLayout layout3 = findViewById(R.id.broadcast_creation);
        final LinearLayout layout2 = findViewById(R.id.CalendarButton);
        final LinearLayout layout4 = findViewById(R.id.GameButton);
        logout = findViewById(R.id.LogoutUser);

        // Remove buttons until the api call has complete
        layout1.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout4.setVisibility(View.GONE);
        layout.bringToFront(); // put the progress bar at the forefront

        firebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot broad = dataSnapshot.child("Broadcasts");
                for (DataSnapshot messageSnapshot : broad.getChildren()) {

                    for (DataSnapshot insideUsers : messageSnapshot.getChildren()) {
                        String date = insideUsers.child("date").getValue(String.class); //  collecting the date value
                        String broadcasterID = insideUsers.child("creator").getValue(String.class); // collecting the creator ID
                        // only collecting dates for this team
                        if (broadcasterID.equals(userID)) {
                            timeList.add(date);
                        }
                    }
                }
                DataSnapshot chall = dataSnapshot.child("Challenges");
                for (DataSnapshot messageSnapshot : chall.getChildren()) {

                    for (DataSnapshot insideUsers : messageSnapshot.getChildren()) {
                        String date = insideUsers.child("date").getValue(String.class); // date value
                        String yourId = insideUsers.child("challengingTeam").getValue(String.class); // creator id
                        // only collecting dates for this team
                        if (yourId.equals(userID)) {
                            timeList.add(date);
                        }
                    }
                }
                //collecting my clubs information
                DataSnapshot club = dataSnapshot.child("Club");
                clubname = club.child(userID).child("club").getValue(String.class);
                division = club.child(userID).child("leagues").getValue(String.class);
                clubanmeBanner.setText(clubname); // setting the homescreen banner
                a.add(clubname);
                a.add(division);
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String date = messageSnapshot.child("date").getValue(String.class);

                    String gameId = messageSnapshot.getKey();
                    // only collecting dates for this team
                    if (userID.equals(userID) || userID.equals(userID)) {
                        //activeGameList.add(gameId);
                        timeList.add(date);

                    }
                    layout.setVisibility(View.GONE); // API calls are complete so we can remove the progress bar and bring back the buttons
                    layout1.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                    layout4.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        // none of the buttons on this screen can be double clicked
        createBroadcast = findViewById(R.id.createBroadcastButton);
        createBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();
                final String club = a.get(0);
                create_broadcast(club, timeList);

            }
        });
        SwitchToGameScreen = findViewById(R.id.gamesButton);
        SwitchToGameScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();
                final String club = a.get(0);
                openGameScreen(club);

            }
        });
        SwitchToClubListScreen  = findViewById(R.id.ClubListScreen);
        SwitchToClubListScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();
                final String club = a.get(0);
                final String div = a.get(1);
                MoveToClubListScreen(club, div, timeList);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();
                Logout_user();

            }
        });




    }



    public void Logout_user() {
        FirebaseAuth.getInstance().signOut();
        Intent Logout  =  new Intent(this, LoginScreen.class);
        startActivity(Logout);
        finish();
    }


    public void create_broadcast(String clubName, ArrayList<String> timeList) {
        Intent CreateBroadcast = new Intent(this, BroadcastScreen.class);
        CreateBroadcast.putExtra("clubName", clubName);
        CreateBroadcast.putExtra("timeList", timeList);

        startActivity(CreateBroadcast);

    }
    public void OpenCalendar(View view) {
        Intent opencalendar = new Intent(this, CalendarScreen.class);
        startActivity(opencalendar);

    }

    public void MoveToClubListScreen(String clubname, String division, ArrayList timeList) {
        Intent clubSearch = new Intent(this, ClubSearchScreen.class);
        clubSearch.putExtra("clubName", clubname);
        clubSearch.putExtra("clubDiv", division);
        clubSearch.putExtra("timeList", timeList);
        startActivity(clubSearch);
    }

    public void openGameScreen(String clubName) {
        Intent GameScreen = new Intent(this, MatchScreen.class);
        GameScreen.putExtra("clubName", clubName);
        startActivity(GameScreen);
    }
}

package com.example.ThirdYearProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OpposingTeamsHP extends Activity {
    private String iD, name, yourClubName, number, div;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseDatabase reff;
    private ArrayList timeList;
    private Button challTeam;
    private long lastClickTime = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opposing_team_hp);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            timeList.add("empty");
            iD = null;
            name = null;
            yourClubName = null;
            number = null;
            div = null;
        } else {
            // bring forward all the extra data
            div = bundle.getString("div");
            number = bundle.getString("number");
            timeList = bundle.getStringArrayList("timeList");
            iD = bundle.getString("OpposingTeamID");
            name = bundle.getString("OpposingTeamName");
            yourClubName = bundle.getString("clubName");
        }
        // collecting my IDs
        final TextView nameText = findViewById(R.id.OpposingTeamName);
        final TextView numberText = findViewById(R.id.teamNumber);
        final TextView divText = findViewById(R.id.Division);
        challTeam = findViewById(R.id.challengeTeamButton);
        String numberCon = "Number: " + number;
        nameText.setText(name);
        numberText.setText(numberCon);
        divText.setText(div);
        //challenge creation button
        challTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();
                create_Challenge();
            }
        });


}

    public void create_Challenge() {
        Intent ChallengeUser = new Intent(this, ChallengeRequestScreen.class);
        ChallengeUser.putExtra("Opposing teams id", iD);
        ChallengeUser.putExtra("OpposingTeamName", name);
        ChallengeUser.putExtra("clubName", yourClubName);
        ChallengeUser.putExtra("timeList", timeList);
        startActivity(ChallengeUser);
    }
    }

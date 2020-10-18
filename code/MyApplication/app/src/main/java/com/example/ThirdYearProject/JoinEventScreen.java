package com.example.ThirdYearProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.Nullable;

import static android.content.ContentValues.TAG;

public class JoinEventScreen extends Activity {
    private String name, date, venue, division, forRemoval, id, gameID, gameType,clubName;
    private Button JoinButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reff;
    private String information;
    private TextView type;

    private long lastClickTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_event_screen); // selecting the correct screen
        // Creating the pop up screen size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        JoinButton = findViewById(R.id.Cancel_Event);
        getWindow().setLayout(width, (int)( height *.8));
        // bringing the information from the match screen
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            information = "empty";
            gameID = "empty";
            gameType = "empty";
            clubName = "empty";
            forRemoval = "empty";

        } else {
            clubName = bundle.getString("clubName");
            information =  bundle.getString("information"); // all of the information needed for adding to the db
            gameID = bundle.getString("gameID");
            gameType = bundle.getString("type");
            forRemoval = bundle.getString("forRemoval"); // bringing the list item to pass it back and remove it
        }
        type = findViewById(R.id.four);
        String join = "Join " + gameType;
        type.setText(join);
        Log.d(TAG, gameType + "   " + gameID + " " + information);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // collecting all the necessary ID's and mapping them to variables.
        final String userid = user.getUid();
        reff = FirebaseDatabase.getInstance().getReference();
        JoinButton = findViewById(R.id.joinButton);
        TextView divisionText = findViewById(R.id.divisionText);
        TextView dateText = findViewById(R.id.DateText);
        TextView venueText = findViewById(R.id.venueText);

        //Checking for challenge or broadcast
        if (gameType.equals("Challenge")){
            final String[] sArray = information.split("]");
            date = sArray[0];
            venue = sArray[1];
            name = sArray[2];
            id = sArray[3];
        }
        else if(gameType.equals("Broadcast")){
            String[] sArray = information.split("]");
            division = sArray[0];
            date = sArray[1];
            venue = sArray[2];
            name = sArray[3];
            id = sArray[4];
            Log.d(TAG, division + " Check" );
            divisionText.setText("Division: " + division);

        }

        dateText.setText("Date: " + date);
        venueText.setText("Pitch "+ venue);



        JoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ensuring we cant click join multiple times
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();

                Intent intentWithResult = new Intent();
                //add and removing challenges and broadcasts accordingly
                if(gameType.equals("Challenge")){
                    // string order is event, division, date, venue, broadcastclubname, broadcasterID

                    reff.child("Challenges").child(gameID).removeValue();

                    ActiveGame activeGame = new ActiveGame(name,clubName, userid,id,venue, date);
                    reff.child("Active").push().setValue(activeGame);
                    intentWithResult.putExtra("message returned", "Challenge]" + forRemoval);
                    setResult(2, intentWithResult);
                    finish();


                }
                else if(gameType.equals(("Broadcast"))){
                    // string order is Event, date, challengeclubname, challcasterID


                    Log.d(TAG, gameID +  " VERY BIG WRITING ");
                    reff.child("Broadcasts").child(gameID).removeValue();
                    ActiveGame broadcast = new ActiveGame(name,clubName, userid,id,venue, date);
                    reff.child("Active").push().setValue(broadcast);
                    intentWithResult.putExtra("message returned", "Broadcast]" + forRemoval);
                    setResult(2, intentWithResult);
                    finish();


                }






            }
        });
    }
}

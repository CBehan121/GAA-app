package com.example.ThirdYearProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;
// Cancel event screen
public class CancelEventScreen extends Activity {
    private Button cancelButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reff;
    private String info, forRemoval, gameType, gameID, creatorID;
    private long lastClickTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_event_screen);
        // Setting the screen size to pop up window size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width, (int)( height *.8));

        //Collecting all the ID's
        TextView pitches = findViewById(R.id.pitchTextBox);
        TextView datebox = findViewById(R.id.dateTextBox);
        TextView divbox = findViewById(R.id.DivisionTextcan);
        TextView textView = findViewById(R.id.TopBar);

        Bundle bundle = getIntent().getExtras(); // collecting the data sent from matchscreen
        if(bundle == null) {
            gameID = "empty";
            gameType = "empty";
            info = "empty";
            forRemoval = "empty";

        } else {
            forRemoval = bundle.getString("forRemoval"); //Collecting the list name of the value for removal after cancellation
            info = bundle.getString("info");
            gameID = bundle.getString("gameID");
            gameType = bundle.getString("type");
        }
        // check if its a challenge or broadcast
        if (gameType.equals("Challenge")){
            final String[] sArray = info.split("]");
            String date = sArray[0];
            String division = sArray[1];
            pitches.setText("Pitch: " + division);
            datebox.setText("Date: " + date);

        }
        else if(gameType.equals("Broadcast")) {
            String[] sArray = info.split("]");
            String division = sArray[0];
            String date = sArray[1];
            String divisiontext = sArray[2];
            divbox.setText("Pitch: " + divisiontext);
            pitches.setText("Division: " + division);
            datebox.setText("Date: " + date);
        }
        String topText = "Cancel " + gameType;

        textView.setText(topText); // setting the topbar text
        Log.d(TAG, gameType + "   " + gameID); // adhoc
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        cancelButton = findViewById(R.id.Cancel_Event);
        final String userid = user.getUid();
        reff = FirebaseDatabase.getInstance().getReference();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // stopping double click
                Intent intentWithResult = new Intent();
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();
                // check if its a challenge or broadcast to remove it correctly
                if(gameType.equals("Challenge")){
                    reff.child("Challenges").child(gameID).removeValue(); // removing the ID from the db to ensure all of the data is removed
                    Log.d(TAG, gameType + "   " + gameID);
                    Toast.makeText(CancelEventScreen.this, "Your challenge request has been cancelled", Toast.LENGTH_SHORT).show();
                    intentWithResult.putExtra("message returned", "Challenge]" + forRemoval); // send back the text for removal
                    setResult(1, intentWithResult); // resultCode is for cancellations
                    finish();

                }
                else if(gameType.equals(("Broadcast"))){
                    Log.d(TAG, gameType + " check here  " + gameID);
                    reff.child("Broadcasts").child(gameID).removeValue();
                    Toast.makeText(CancelEventScreen.this, "Your broadcast has been cancelled", Toast.LENGTH_SHORT).show();
                    intentWithResult.putExtra("message returned", "Broadcast]" + forRemoval);
                    setResult(1, intentWithResult); // resultCode is for cancellations
                    finish();
                }





            }
        });

    }
}


package com.example.ThirdYearProject;

import android.app.Activity;
import android.app.NotificationChannel;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.content.ContentValues.TAG;


public class MatchScreen extends Activity {
    private Map map;
    private Button switchToActive;
    private ListView listView, yourGames;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private Button switchToYourGamesButton, switchToAvail;
    private String neededType, event, clubnameTop, viewableString;
    private ListView active;
    private ArrayList<String> yourGamesList, arrayList, activeGameList;
    private ArrayAdapter yourGamesArrayAdapter, arrayAdapter, activeGameListAdapter;
    private TextView activeIcon, availIcon, pendingIcon;
    private long lastClickTime = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Collecting ID's
        setContentView(R.layout.match_screen);
        activeIcon = findViewById(R.id.ActiveIcon);
        availIcon = findViewById(R.id.AvailIcon);
        pendingIcon = findViewById(R.id.YourGamIcon);
        activeIcon.setVisibility(View.GONE);
        availIcon.setVisibility(View.GONE);
        pendingIcon.setVisibility(View.GONE);

        //Getting clubName from home screen.
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            clubnameTop = null;
        } else {
            clubnameTop = bundle.getString("clubName");
        }
        //Map to hold values about the given events
        final Map<String, String> holdEvent= new HashMap<>();

        //Creating a hashmap for the viewable objects
        final Map<String, String> viewHolder = new HashMap<String, String>();



        //Available games
        listView = findViewById(R.id.AvailList);
        final Map<String, String > Availmap = new HashMap<>();
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);


        //Your games (not accepted)
        final Map<String, String> map = new HashMap<String, String>();
        yourGames = findViewById(R.id.YourList);
        yourGames.setVisibility(View.GONE);
        yourGamesList = new ArrayList<>();
        yourGamesArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, yourGamesList);


        // your accepted games
        final Map<String, String> complMap = new HashMap<>();
        active = findViewById(R.id.AcceptedList);
        active.setVisibility(View.GONE);
        activeGameList = new ArrayList<>();
        activeGameListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, activeGameList);

        final ProgressBar progressBar = findViewById(R.id.loadingCircleMatchScreen);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = user.getUid();
        // adding broadcasts lists
        firebaseDatabase.getInstance().getReference("Broadcasts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {

                    for(DataSnapshot insideUsers : messageSnapshot.getChildren()) {
                        event = "";
                        String data = insideUsers.child("division").getValue(String.class);
                        String date = insideUsers.child("date").getValue(String.class);
                        String venue = insideUsers.child("venue").getValue(String.class);
                        String broadcastClubName = insideUsers.child("name").getValue(String.class);
                        String broadcasterID = insideUsers.child("creator").getValue(String.class);
                        String broadcastID = messageSnapshot.getKey();


                        Availmap.put(broadcastID, "Broadcast" );
                        map.put( broadcastID, "Broadcast");
                        // string order is event, division, date, venue, broadcastclubname, broadcasterID
                        event = data + "]" + date + "]" + venue + "]" + broadcastClubName + "]" + broadcasterID;
                        if(broadcasterID.equals(userid)){
                            viewableString = "Broadcast: "+ clubnameTop + "  VS  Pending   Date: " + date + "   Pitch: " + venue;
                            Log.d(TAG, viewableString);
                            viewHolder.put(viewableString, broadcastID);
                            yourGamesList.add(viewableString);
                            //yourGamesList.add(broadcastID);


                        } else {
                            viewableString = "Broadcast: " + broadcastClubName + "  VS  Pending   Date: " + date + "   Pitch: " + venue;
                            viewHolder.put(viewableString, broadcastID);
                            arrayList.add(viewableString);
                            //arrayList.add(broadcastID);
                        }
                        holdEvent.put(broadcastID, event);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Getting challenges
        firebaseDatabase.getInstance().getReference("Challenges").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        // have to go 2 layers deep because of the layout of the db
                    for (DataSnapshot insideUsers : messageSnapshot.getChildren()) {
                        event = "";
                        // Collecting all of the challenge information
                        String iD = insideUsers.child("challengedTeam").getValue(String.class);
                        String name = insideUsers.child("challengingClubName").getValue(String.class);
                        String date2 = insideUsers.child("date").getValue(String.class);
                        String pitch = insideUsers.child("pitch").getValue(String.class);
                        String team = insideUsers.child("challengedTeamName").getValue(String.class);
                        String challengingTeam = insideUsers.child("challengingTeam").getValue(String.class);
                        String challengeID = messageSnapshot.getKey();
                        map.put(challengeID ,"Challenge");
                        Availmap.put( challengeID ,"Challenge");

                        event = date2 + "]" + pitch+ "]" + name + "]" + challengingTeam;
                        // string order is Event, date, challengeclubname, challcasterID

                        if (iD.equals(userid)) {
                            viewableString = "Challenge: " + name +  "   Date: " + date2 + "   Pitch: " + pitch;

                            arrayList.add(viewableString);
                            viewHolder.put(viewableString, challengeID);




                        } else if (userid.equals(challengingTeam)) {

                            viewableString = "Challenging:  " + team + "   Date: " + date2 + "   Pitch: " + pitch;

                            viewHolder.put(viewableString, challengeID);
                            yourGamesList.add(viewableString);


                        }
                        holdEvent.put(challengeID,event);
                    }
                }
                progressBar.setVisibility(View.GONE);
                if(arrayList.isEmpty()){
                    availIcon.setVisibility(View.VISIBLE);

                }
                listView.setAdapter(arrayAdapter);
                yourGames.setAdapter(yourGamesArrayAdapter);

                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        firebaseDatabase.getInstance().getReference("Active").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String iD1 = messageSnapshot.child("teamOneID").getValue(String.class);
                    String iD2 = messageSnapshot.child("teamTwoID").getValue(String.class);
                    String pitch = messageSnapshot.child("venue").getValue(String.class);
                    String name2 = messageSnapshot.child("teamOneName").getValue(String.class);
                    String name1 = messageSnapshot.child("teamTwoName").getValue(String.class);
                    String date = messageSnapshot.child("date").getValue(String.class);

                    String gameId = messageSnapshot.getKey();
                    viewableString = name1 + "  VS  " + name2 + "  Date: " + date + "  Pitch: " + pitch;
                    if( iD1.equals(userid) || iD2.equals(userid)){
                        //activeGameList.add(gameId);
                        activeGameList.add(viewableString);
                    }
                    active.setAdapter(activeGameListAdapter);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Three top button redirection
        switchToYourGamesButton = findViewById(R.id.Users);
        switchToYourGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(yourGamesList.isEmpty()){

                    pendingIcon.setVisibility(View.VISIBLE);

                }
                //Set all of the visibilties correctly
                activeIcon.setVisibility(View.GONE);
                availIcon.setVisibility(View.GONE);
                yourGames.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                active.setVisibility(View.GONE);
            }
        });
        switchToAvail = findViewById(R.id.Available);
        switchToAvail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayList.isEmpty()){
                    availIcon.setVisibility(View.VISIBLE);



                }
                activeIcon.setVisibility(View.GONE);
                pendingIcon.setVisibility(View.GONE);
                yourGames.setVisibility(View.GONE);
                active.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }

            });
        switchToActive = findViewById(R.id.Accepted);
        switchToActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activeGameList.isEmpty()){
                    activeIcon.setVisibility(View.VISIBLE);


                }
                availIcon.setVisibility(View.GONE);
                pendingIcon.setVisibility(View.GONE);
                active.setVisibility(View.VISIBLE);
                yourGames.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String item = ((TextView)view).getText().toString();
                //Log.d(TAG, item + "        THIS first  ");
                String neededID = viewHolder.get(item);
                //Log.d(TAG, neededID + "IMPORTANT!");
                String teamId = map.get(neededID);
                String information = holdEvent.get(neededID);
                // Log.d(TAG, teamId + "        THIS second  " + teamId +"   " + information);
                // There was a lot of values so a lot of adhoc testing was required here
                OpenEventIntentToJoin(teamId, neededID, information, clubnameTop, item );


            }
        });
        yourGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final String item = ((TextView)view).getText().toString();
               Log.d(TAG, item + "        THIS first  ");
                String neededID = viewHolder.get(item);
                String teamId = Availmap.get(neededID);
                String information = holdEvent.get(neededID);

              //  Log.d(TAG, item + "        THIS third  " + teamId );



                OpenEventIntentToCancel(teamId, neededID, information, item);



            }
        });



    }
    public void OpenEventIntentToCancel( String gameType ,String matchID, String info, String item) {
        Intent OpenEvent = new Intent(this, CancelEventScreen.class);
        OpenEvent.putExtra("type", gameType);
        OpenEvent.putExtra("gameID", matchID);
        OpenEvent.putExtra("info", info);
        OpenEvent.putExtra("forRemoval", item);
        Log.d(TAG, gameType + "   " + matchID);

        startActivityForResult(OpenEvent, 1);
    }
    public void OpenEventIntentToJoin( String gameType ,String matchID, String holdEvent, String clubname, String item ) {
        Intent OpenEvent = new Intent(this, JoinEventScreen.class);
        OpenEvent.putExtra("information", holdEvent);
        OpenEvent.putExtra("type", gameType);
        OpenEvent.putExtra("gameID", matchID);
        OpenEvent.putExtra("clubName", clubname);
        OpenEvent.putExtra("forRemoval", item);
        Log.d(TAG, gameType + "   " + matchID+ "     " + holdEvent);

        startActivityForResult(OpenEvent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1) {
                String messagereturned = data.getStringExtra("message returned");
                Log.d(TAG, "WORKING WOOP");
                String[] typeName = messagereturned.split("]");
                String item = typeName[1];
                String type = typeName[0];

                if (type.equals("Challenge")) {
                    yourGamesList.remove(item);

                } else if (type.equals("Broadcast")) {
                    yourGamesList.remove(item);

                    Log.d(TAG, type);

                }
                yourGames.setAdapter(yourGamesArrayAdapter);

            }
            else if(requestCode == 2){
                String messagereturned = data.getStringExtra("message returned");
                String[] typeName = messagereturned.split("]");
                String item = typeName[1];
                String type = typeName[0];

                if (type.equals("Challenge")) {
                    arrayList.remove(item);
                    activeGameList.add(item);//Just adding a place holder until the team refreshes.
                } else if (type.equals("Broadcast")) {
                    arrayList.remove(item);
                    activeGameList.add(item);//Just adding a place holder until the team refreshes.

                }
                listView.setAdapter(arrayAdapter);
                active.setAdapter(activeGameListAdapter);
            }
        }
        catch(Exception FATAL ){

        }



        };
}

package com.example.ThirdYearProject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ClubSearchScreen extends Activity {
    private ListView listView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reff;
    private Map map;

    private String div,userID, name;
    private ArrayList<String> timeList;
    private  static final String TAG = "ClubSearchScreen";
    private ArrayAdapter arrayAdapter;
    private long lastClickTime = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_search_screen);
        //Bringing in the clubName from homeScreen
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            timeList = null;
            div = "empty";
            name = "empty";
        } else {
            timeList = bundle.getStringArrayList("timeList");
            div = bundle.getString("clubDiv");
            name = bundle.getString("clubName");
        }

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID =  user.getUid();
        final Map<String, String> map = new HashMap<String, String>();
        final Map<String, String> map2 = new HashMap<String, String>();

        listView = findViewById(R.id.clubList);
        final ArrayList<String> arrayList =  new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        final ProgressBar progressBar = findViewById(R.id.loadingcircleInner);

        reff = FirebaseDatabase.getInstance().getReference().child("Club");
        firebaseDatabase.getInstance().getReference().child("Club").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot messageSnapshot : dataSnapshot.getChildren()){
                    String teamid = messageSnapshot.getKey();
                    String retrievedUser  = messageSnapshot.child("club").getValue(String.class);
                    String getDivision = messageSnapshot.child("leagues").getValue(String.class);
                    String getNumber = messageSnapshot.child("phoneNumber").getValue(String.class);
                    if (!userID.equals(teamid) ){
                        String ClubID = messageSnapshot.getKey();
                        map.put(retrievedUser, ClubID + " ]" + getNumber +  "]" + getDivision);
                        arrayList.add(retrievedUser);

                    }
                    progressBar.setVisibility(View.GONE);




                }

                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        EditText searcher = findViewById(R.id.ClubSearch);
        searcher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (ClubSearchScreen.this).arrayAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // preventing double, using threshold of 1000 ms
                if (SystemClock .elapsedRealtime() - lastClickTime < 1000){
                    return;
                }

                lastClickTime = SystemClock.elapsedRealtime();
                String item = ((TextView)view).getText().toString();
                String teamId = map.get(item);
                final String[] sArray = teamId.split("]");
                teamId = sArray[0];
                String number = sArray[1];
                String div = sArray[2];

                OpenOppentsScreen(teamId, item, name, timeList, number, div);
            }
        });


    }

    public void OpenOppentsScreen(String id, String name, String clubName, ArrayList timeList, String number, String div) {
        Intent openPersons = new Intent(this, OpposingTeamsHP.class);
        openPersons.putExtra("OpposingTeamID", id);
        openPersons.putExtra("OpposingTeamName", name);
        openPersons.putExtra("timeList", timeList);
        openPersons.putExtra("number", number);
        openPersons.putExtra("div", div);
        Log.d(TAG, clubName);
        openPersons.putExtra("clubName", clubName);
        startActivity(openPersons);


    }
}

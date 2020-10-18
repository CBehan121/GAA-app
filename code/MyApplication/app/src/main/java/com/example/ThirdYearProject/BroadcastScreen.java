package com.example.ThirdYearProject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import static android.content.ContentValues.TAG;
// this is the broadcast screen file, From here broadcasts are created and sent to the database
public class BroadcastScreen extends Activity implements AdapterView.OnItemSelectedListener {
    private DatePickerDialog picker;
    private TextView eText, tvw;
    private EditText date;
    private DatabaseReference reff;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private Broadcast broadcast;
    private Button createBroadcast;
    private Club info;
    private String venue, division, name;
    private ArrayList timeList;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private Date dates, dateConv;
    private int day, year, month;
    private long lastClickTime = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_broadcast_screen); // set the view to the broadcast screen
        Bundle bundle = getIntent().getExtras();
        // get the data sent from the home screen
        if(bundle == null) {
            timeList.add("empty"); // if the timelist is empty I add and "empty"  to it to avoid null object issues
            name = null; // name is safe to be set as null
        } else {
            timeList = bundle.getStringArrayList("timeList"); // get the timelist from the homescreen
            name = bundle.getString("clubName"); // bring the clubname so we dont need to source it on this screen
        }


        // Collecting all my variables
        createBroadcast = findViewById(R.id.createBroadcastB);
        final Spinner divisionspinner = findViewById(R.id.divisionspinner);
        final Spinner venuespinner = findViewById(R.id.venuespinner);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = user.getUid();

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final Date todaysDate = calendar.getTime(); // getting todays date in the same format as the databse


        eText=(TextView) findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        // Setting the onclick for the date textView
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                day = cldr.get(Calendar.DAY_OF_MONTH);
                month = cldr.get(Calendar.MONTH);
                year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog( BroadcastScreen.this, new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText( dayOfMonth + "/" + (monthOfYear + 1) + "/" + year); //Ensuring i follow the same date format
                            }
                        }, year, month, day);
                picker.show();

            }
        });

        reff = FirebaseDatabase.getInstance().getReference().child("Broadcasts"); // get the database reference, Here i only need the
        // the broadcast section.

        createBroadcast.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick(View view) {
                // preventing double click
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();

                final String venue = venuespinner.getSelectedItem().toString(); // Get the spinner strings
                final String division = divisionspinner.getSelectedItem().toString();
                final String date = eText.getText().toString().trim(); // collecting the date string
                try {
                    dateConv = dateFormat.parse(date); // convert the dateString to a date
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // check  if the user inputted a date
                if(date.equals("")){
                    eText.setError("Please choose a date");
                    Toast.makeText(BroadcastScreen.this,"Please choose a date", Toast.LENGTH_SHORT).show();
                }
                //Ensure a division was chosen
                else if (division.equals("Select a Division")){
                    Toast.makeText(BroadcastScreen.this,"Please choose which divisions can accept this broadcast", Toast.LENGTH_SHORT).show();
                }
                // check to make sure the chosen date is in the future
                else if (dateConv.compareTo(todaysDate) < 0){
                    Log.d(TAG, "working" +  dateConv.compareTo(todaysDate));
                    eText.setError("You cannot book a date in the past");
                    Toast.makeText(BroadcastScreen.this,"You cannot book a date in the past", Toast.LENGTH_SHORT).show();
                }
                //  check if the user already has a game set for the chosen date
                else if(timeList.contains(date)){
                    eText.setError("You already have a game on this date");
                    Toast.makeText(BroadcastScreen.this,"You already have a game on this date", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d(TAG, "working" +  dateConv.compareTo(todaysDate)); // adhoc
                    broadcast = new Broadcast(name, venue, division, date, userid, "Pending"); // create the broacast
                    reff.push().child(userid).setValue(broadcast); // upload the broadcast using push to give it a unique ID
                    timeList.add(date); // add the current date to the timelist to stop overbooking
                    Toast.makeText(BroadcastScreen.this,"Broadcast created", Toast.LENGTH_SHORT).show();
                    final_create_broadcast(); // Once everything has succeeded

                }






            }
        });

    }

    public void final_create_broadcast() {
        Intent FinalBroadcast = new Intent(this, HomeScreen.class);
        finish(); // Close the current screen
        startActivity(FinalBroadcast);

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

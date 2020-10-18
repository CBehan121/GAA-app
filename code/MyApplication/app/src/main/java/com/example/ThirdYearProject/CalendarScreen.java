package com.example.ThirdYearProject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CalendarScreen extends Activity {
    private CalendarView calendarView;
    private TextView currentDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_screen);

        calendarView = (CalendarView) findViewById(R.id.CalendarView);
        currentDate = (TextView) findViewById(R.id.MyDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date  = (dayOfMonth) + "/" + (month) + "/" + year;
                currentDate.setText(date);
                setCurrentDate(currentDate);
            }
        });
    }



    public CalendarView getCalendarView() {

        return calendarView;
    }

    public TextView getCurrentDate() {
        return currentDate;
    }

    public void setCalendarView(CalendarView calendarView) {

        this.calendarView = calendarView;
    }

    public void setCurrentDate(TextView currentDate) {

        this.currentDate = currentDate;
    }
}

package com.example.ThirdYearProject;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
//  Unit testing the cancel event screen
public class CancelEventScreenUnit {
    @Test
    public void checkAllExists() throws Exception {
        // Check to ensure all the features are on the screen
        ActivityScenario activityScenario = ActivityScenario.launch(CancelEventScreen.class);
        // Sleep upon opening incase of loading
        Thread.sleep(2000);

        onView(withId(R.id.TopBar)).check(matches(isDisplayed()));

        onView(withId(R.id.cancelScreenButton)).check(matches(isDisplayed()));

        onView(withId(R.id.Cancel_Event)).check(matches(isDisplayed()));

        onView(withId(R.id.dateTextBox)).check(matches(isDisplayed()));

        onView(withId(R.id.pitchTextBox)).check(matches(isDisplayed()));




    }
}
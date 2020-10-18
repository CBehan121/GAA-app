package com.example.ThirdYearProject;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
// UNIT TEST
public class MatchScreenUnit {

        @Test
        public void checkAllExists() throws Exception{
            // Start the match screen
            ActivityScenario activityScenario = ActivityScenario.launch(MatchScreen.class);
            Thread.sleep(2000); // allow for loading time
            //Cycle through each list and ensure they all show
            onView(withId(R.id.Available)).check(matches(isDisplayed()));
            onView(withId(R.id.AvailList)).check(matches(isDisplayed()));


            onView(withId(R.id.Users)).check(matches(isDisplayed()));
            onView(withId(R.id.Users)).perform(click());
            onView(withId(R.id.YourList)).check(matches(isDisplayed()));

            onView(withId(R.id.Accepted)).check(matches(isDisplayed()));
            onView(withId(R.id.Accepted)).perform(click());;
            onView(withId(R.id.AcceptedList)).check(matches(isDisplayed()));

        }

}
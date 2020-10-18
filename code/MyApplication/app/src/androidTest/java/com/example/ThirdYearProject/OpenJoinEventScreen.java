package com.example.ThirdYearProject;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.*;
// Integration test - join event
public class OpenJoinEventScreen {
    @Rule
    // start on home screen
    public ActivityTestRule<HomeScreen> mLoginScreenTestResult =
            new ActivityTestRule<HomeScreen>(HomeScreen.class);

    @Test
    public void openJoinEventScreen() throws Exception{
        // Allow for loading
        Thread.sleep(2000);
        onView(withId(R.id.gamesButton)).perform(click());
        onView(withId(R.id.AvailList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.AvailList)).atPosition(0).perform(click());
        Thread.sleep(2000);
        // Join the game
        onView(withId(R.id.joinButton)).perform(click());
        // Check to ensure we are sent back to the list screen
        onView(withId(R.id.Available)).perform(click());
    }
}
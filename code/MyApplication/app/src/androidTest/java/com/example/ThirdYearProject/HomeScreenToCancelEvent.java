package com.example.ThirdYearProject;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.*;

public class HomeScreenToCancelEvent {
    // Integration test for cancelling an event
    @Rule
    public ActivityTestRule<HomeScreen> mLoginScreenTestResult =
            new ActivityTestRule<HomeScreen>(HomeScreen.class);

    @Test
    public void openCancelEventScreen() throws Exception{
        // pause  for home screen load
        Thread.sleep(2000);
        // Open the game screen
        onView(withId(R.id.gamesButton)).perform(click());

        onView(withId(R.id.Users)).perform(click());

        onView(withId(R.id.YourList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.YourList)).atPosition(0).perform(click());

        Thread.sleep(2000);
        // Cancel the game
        onView(withId(R.id.cancelScreenButton)).perform(click());
        //Check to see the integration succeeded
        onView(withId(R.id.Users)).perform(click());

    }
}
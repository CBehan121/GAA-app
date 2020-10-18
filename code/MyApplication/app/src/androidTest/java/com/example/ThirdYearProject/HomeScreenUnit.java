package com.example.ThirdYearProject;

import androidx.test.core.app.ActivityScenario;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
///// UNIT TEST
public class HomeScreenUnit {
    @Test
    public void checkAllExists() throws Exception{
        ActivityScenario activityScenario = ActivityScenario.launch(HomeScreen.class);
        // allow the screen to load
        Thread.sleep(2000);
        //check that all the necessary features exist
        onView(withId(R.id.gamesButton)).check(matches(isDisplayed()));
        onView(withId(R.id.ClubListScreen)).check(matches(isDisplayed()));
        onView(withId(R.id.CalendarButtons)).check(matches(isDisplayed()));
        onView(withId(R.id.createBroadcastButton)).check(matches(isDisplayed()));
        onView(withId(R.id.LogoutUser)).check(matches(isDisplayed()));
        onView(withId(R.id.TeamName)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView2)).check(matches(isDisplayed()));

    }
    @Test
    public void checkButtonsWork() throws Exception{
        // Restart the home screen
        // ensure every button works
        // ensure back button redirection is correct
        ActivityScenario activityScenario = ActivityScenario.launch(HomeScreen.class);
        Thread.sleep(2000);

        Thread.sleep(500);
        onView(withId(R.id.createBroadcastButton)).perform(click());
        onView(withId(R.id.createBroadcastB)).check(matches(isDisplayed()));
        pressBack();

        onView(withId(R.id.gamesButton)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.Users)).check(matches(isDisplayed()));
        pressBack();

        Thread.sleep(500);
        onView(withId(R.id.CalendarButtons)).perform(click());
        onView(withId(R.id.MyDate)).check(matches(isDisplayed()));
        pressBack();

        Thread.sleep(500);
        onView(withId(R.id.ClubListScreen)).perform(click());
        onView(withId(R.id.ClubSearch)).check(matches(isDisplayed()));
        pressBack();







    }

}
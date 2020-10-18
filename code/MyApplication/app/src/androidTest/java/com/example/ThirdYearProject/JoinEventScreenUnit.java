package com.example.ThirdYearProject;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
// UNIT test
public class JoinEventScreenUnit {
    @Test
    public void checkAllExists() throws Exception {
        // open the join event screen
        ActivityScenario activityScenario = ActivityScenario.launch(JoinEventScreen.class);
        Thread.sleep(2000);
        // check all of its attributes exist
        onView(withId(R.id.joinButton)).check(matches(isDisplayed()));

        onView(withId(R.id.four)).check(matches(isDisplayed()));

        onView(withId(R.id.DateText)).check(matches(isDisplayed()));

        onView(withId(R.id.venueText)).check(matches(isDisplayed()));





    }
}


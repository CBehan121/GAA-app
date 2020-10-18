package com.example.ThirdYearProject;
/// This test files checks to ensure you stay on the same screen if you fail to create a broadcast.
import androidx.test.core.app.ActivityScenario;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.*;

public class HomeScreenTest {
    ///UNIT TEST

    @Test
    public void onCreate() throws Exception {
        // Start the homeScreen
        ActivityScenario activityScenario = ActivityScenario.launch(HomeScreen.class);
        // Delay any checks until it loads
        Thread.sleep(2000);
        // Switch to the broadcast screen and check all of its attributes
        onView(withId(R.id.broadcast_creation)).perform(click());
        onView(withId(R.id.divisionspinner)).perform(click());
        onData(hasToString(startsWith("Division 1")))
                .perform(click());

        onView(withId(R.id.venuespinner)).perform(click());
        onData(hasToString(startsWith("All")))
                .perform(click());

        onView(withId(R.id.createBroadcastB)).perform(click());
        onView(withId(R.id.divisionspinner)).perform(click());


    }
}
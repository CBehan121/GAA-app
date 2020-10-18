package com.example.ThirdYearProject;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.*;

public class OpenOpposingTeamChallenge {
    @Rule
    public ActivityTestRule<HomeScreen> mLoginScreenTestResult =
            new ActivityTestRule<HomeScreen>(HomeScreen.class);

    @Test
    public void openChallengeScreenSpecificTeam() throws Exception{
        Thread.sleep(2000);
        onView(withId(R.id.ClubListScreen)).perform(click());
        onView(withId(R.id.ClubSearch)).perform(click(), typeText("Cians"), pressImeActionButton());
        onView(withId(R.id.clubList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.clubList)).atPosition(0).perform(click());
        // check the correct information is showing
        onView(withText("Cians Club")).check(matches(isDisplayed()));
        onView(withText("Division 1")).check(matches(isDisplayed()));
        onView(withText("Number: 089565656")).check(matches(isDisplayed()));
        onView(withId(R.id.challengeTeamButton)).perform(click());
        // check everything is available to be pressed
        onView(withId(R.id.pitchSpinner)).perform(click());
        onData(hasToString(startsWith("All")))
                .perform(click());
        onView(withText("Challenge: Cians Club")).check(matches(isDisplayed()));
        onView(withId(R.id.ChallengeButton)).perform(click());
        // after failed challenge ensure we stay on the screen
        onView(withText("Challenge: Cians Club")).check(matches(isDisplayed()));
        pressBack(); // check back button redirection is correct after failed attempt
        onView(withText("Cians Club")).check(matches(isDisplayed()));



    }
}
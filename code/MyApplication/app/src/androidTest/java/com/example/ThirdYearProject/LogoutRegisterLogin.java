package com.example.ThirdYearProject;

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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.*;
// Integration test for registration and logout and login
public class LogoutRegisterLogin {
    // Start the home screen
    @Rule
    public ActivityTestRule<HomeScreen> mLoginScreenTestResult =
            new ActivityTestRule<HomeScreen>(HomeScreen.class);


    @Test
    public void LogoutandRegister() throws Exception {
        //sleep for loading time.
        Thread.sleep(2000);
        onView(withId(R.id.LogoutUser)).perform(click());
        // sleep for logout
        Thread.sleep(800);
        // Fill in all registration details
        onView(withId(R.id.RegistrationButton)).perform(click());
        onView(withId(R.id.clubname)).perform(click(), typeText("TestTeam"), pressImeActionButton());
        onView(withId(R.id.ClubNumber)).perform(click(), typeText("089357426"), pressImeActionButton());
        onView(withId(R.id.emailText)).perform(click(), typeText("Test@test2.com"), pressImeActionButton());
        onView(withId(R.id.CLubDivision)).perform(click());
        onData(hasToString(startsWith("Division 1")))
                .perform(click());
        onView(withId(R.id.pitches)).perform(click(), typeText("PitchTest"), pressImeActionButton());
        onView(withId(R.id.AddPitches)).perform(click());
        onView(withId(R.id.password1)).perform(click(), typeText("123456"), pressImeActionButton());
        onView(withId(R.id.password2)).perform(click(), typeText("123456"), pressImeActionButton());

        // Attempt to register
        onView(withId(R.id.regButton)).perform(click());
        Thread.sleep(3000);
        // Attempt to login
        onView(withId(R.id.loginemail)).perform(click(), typeText("Test@test2.com"));
        onView(withId(R.id.loginpassword)).perform(click(), typeText("123456"), pressImeActionButton());
        onView(withId(R.id.loginButton)).perform(click());


    }
}
package com.example.ThirdYearProject;

import android.content.Context;

import androidx.test.espresso.action.TypeTextAction;
import androidx.test.espresso.intent.Intents;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/// Integration test checking the login feature
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    // Open the Login screen to test.
    @Rule
    public ActivityTestRule<LoginScreen> mLoginScreenTestResult =
            new ActivityTestRule<LoginScreen>(LoginScreen.class);

    @Test
    public void checkLogin() throws Exception{
        onView(withId(R.id.loginemail)).perform(click(), typeText("connerbehan1@gmail.com"));
        onView(withId(R.id.loginpassword)).perform(click(), typeText("123456"), pressImeActionButton());

        onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(4000);
        onView(withText("Conners club")).check(matches(isDisplayed()));

    }

}

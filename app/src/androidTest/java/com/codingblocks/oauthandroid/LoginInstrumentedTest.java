package com.codingblocks.oauthandroid;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void checkClientId () throws Exception {
        onView(withId(R.id.btnLogout)).check(matches(withText("Logout"))).perform(click());

        onView(withId(R.id.auth))
                .perform(click());

        final UiDevice mDevice =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        final int timeOut = 1000 * 60;

        mDevice.wait(Until.findObject(By.clazz(WebView.class)), timeOut);

        //Username edit text box
        UiObject usernameInput = mDevice.findObject(new UiSelector().instance(0).className(EditText.class));
        usernameInput.waitForExists(timeOut);
        usernameInput.setText("venomousboxer");

        //password edit text box
        UiObject passwordInput = mDevice.findObject(new UiSelector().instance(1).className(EditText.class));
        passwordInput.waitForExists(timeOut);
        passwordInput.setText("shanlong39");

        //login button
        UiObject loginButton = mDevice.findObject(new UiSelector().instance(1).className(Button.class));
        loginButton.waitForExists(timeOut);
        loginButton.clickAndWaitForNewWindow();
    }
}

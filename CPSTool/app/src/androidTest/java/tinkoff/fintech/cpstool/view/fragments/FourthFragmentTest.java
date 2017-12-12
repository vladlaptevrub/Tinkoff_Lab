package tinkoff.fintech.cpstool.view.fragments;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.FragmentManager;

import org.junit.Rule;
import org.junit.Test;

import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class FourthFragmentTest {
    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void fourth_fragment_test(){
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startFourthFragment();
            }
        });
        onView(withId(R.id.checkDarkMode)).check(matches(isDisplayed()));
        onView(withId(R.id.clearHistoryButton)).check(matches(isDisplayed()));
    }

    @Test
    public void fourth_fragment_dark_mode_test(){
        //Start FOURTH FRAGMENT and click on checkBox (it was checked before)
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startFourthFragment();
            }
        });
        onView(withId(R.id.checkDarkMode)).check(matches(isChecked()));
        onView(withId(R.id.checkDarkMode)).perform(click());

        //Replace fragment
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startFirstFragment();
            }
        });

        //Replace again to FOURTH FRAGMENT and click on checkBox (it has to be checked after)
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startFourthFragment();
            }
        });
        onView(withId(R.id.checkDarkMode)).perform(click());

        onView(withId(R.id.checkDarkMode)).check(matches(isChecked()));
    }

    private FourthFragment startFourthFragment() {
        MainActivity activity = (MainActivity) activityRule.getActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FourthFragment fourthFragment = new FourthFragment();
        fragmentManager.beginTransaction().replace(R.id.container, fourthFragment).commit();
        return fourthFragment;
    }

    private FirstFragment startFirstFragment() {
        MainActivity activity = (MainActivity) activityRule.getActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FirstFragment firstFragment = new FirstFragment();
        fragmentManager.beginTransaction().replace(R.id.container, firstFragment).commit();
        return firstFragment;
    }

}
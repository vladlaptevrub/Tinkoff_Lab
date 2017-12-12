package tinkoff.fintech.cpstool.view.fragments;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.junit.Rule;
import org.junit.Test;

import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class FirstFragmentTest {
    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void first_fragment_test(){
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FirstFragment firstFragment = startFirstFragment();
            }
        });
        onView(withId(R.id.autoCompleteTextView)).check(matches(isDisplayed()));
    }

    private FirstFragment startFirstFragment() {
        MainActivity activity = (MainActivity) activityRule.getActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FirstFragment firstFragment = new FirstFragment();
        fragmentManager.beginTransaction().replace(R.id.container, firstFragment).commit();
        return firstFragment;
    }

}
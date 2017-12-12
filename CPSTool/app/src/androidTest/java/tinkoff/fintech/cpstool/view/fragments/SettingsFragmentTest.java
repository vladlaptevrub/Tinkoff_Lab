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

public class SettingsFragmentTest {
    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void settings_fragment_test(){
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startSettingsFragment();
            }
        });
        onView(withId(R.id.checkDarkMode)).check(matches(isDisplayed()));
        onView(withId(R.id.clearHistoryButton)).check(matches(isDisplayed()));
    }

    @Test
    public void fourth_fragment_dark_mode_test(){
        /** Start SETTINGS FRAGMENT and click on checkBox (it was checked before) */
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startSettingsFragment();
            }
        });
        onView(withId(R.id.checkDarkMode)).check(matches(isChecked()));
        onView(withId(R.id.checkDarkMode)).perform(click());

        /** Replace fragment */
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startSearchFragment();
            }
        });

        /** Replace again to SETTINGS FRAGMENT and click on checkBox (it has to be checked after) */
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startSettingsFragment();
            }
        });
        onView(withId(R.id.checkDarkMode)).perform(click());

        onView(withId(R.id.checkDarkMode)).check(matches(isChecked()));
    }

    private SettingsFragment startSettingsFragment() {
        MainActivity activity = (MainActivity) activityRule.getActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        SettingsFragment settingsFragment = new SettingsFragment();
        fragmentManager.beginTransaction().replace(R.id.container, settingsFragment).commit();
        return settingsFragment;
    }

    private SearchFragment startSearchFragment() {
        MainActivity activity = (MainActivity) activityRule.getActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        SearchFragment searchFragment = new SearchFragment();
        fragmentManager.beginTransaction().replace(R.id.container, searchFragment).commit();
        return searchFragment;
    }

}
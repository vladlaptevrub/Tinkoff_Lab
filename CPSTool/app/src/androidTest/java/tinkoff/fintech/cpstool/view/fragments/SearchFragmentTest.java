package tinkoff.fintech.cpstool.view.fragments;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.FragmentManager;

import org.junit.Rule;
import org.junit.Test;

import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class SearchFragmentTest {
    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void search_fragment_test(){
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startSearchFragment();
            }
        });
        onView(withId(R.id.autoCompleteTextView)).check(matches(isDisplayed()));
    }

    private SearchFragment startSearchFragment() {
        MainActivity activity = (MainActivity) activityRule.getActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        SearchFragment searchFragment = new SearchFragment();
        fragmentManager.beginTransaction().replace(R.id.container, searchFragment).commit();
        return searchFragment;
    }

}
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

public class HistoryFragmentTest {
    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void history_fragment_test(){
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startHistoryFragment();
            }
        });
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    private void startHistoryFragment() {
        MainActivity activity = (MainActivity) activityRule.getActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        HistoryFragment historyFragment = new HistoryFragment();
        fragmentManager.beginTransaction().replace(R.id.container, historyFragment).commit();
    }
}
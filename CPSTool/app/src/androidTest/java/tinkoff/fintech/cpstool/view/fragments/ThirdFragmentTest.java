package tinkoff.fintech.cpstool.view.fragments;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Rule;
import org.junit.Test;

import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class ThirdFragmentTest{
    @Rule
    public ActivityTestRule activityMainRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void third_fragment_test(){
        activityMainRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startThirdFragment();
            }
        });
        onView(withId(R.id.check_favourite)).check(matches(isDisplayed()));
        onView(withId(R.id.to_map_button)).check(matches(isDisplayed()));
        onView(withId(R.id.delete_button)).check(matches(isDisplayed()));
        onView(withId(R.id.shareButton)).check(matches(isDisplayed()));
    }

    private ThirdFragment startThirdFragment() {
        MainActivity activity = (MainActivity) activityMainRule.getActivity();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        ThirdFragment thirdFragment = new ThirdFragment();
        fragmentManager.beginTransaction().replace(R.id.container, thirdFragment).commit();
        return thirdFragment;
    }
}
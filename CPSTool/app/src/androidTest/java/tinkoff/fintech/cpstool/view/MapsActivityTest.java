package tinkoff.fintech.cpstool.view;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import tinkoff.fintech.cpstool.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MapsActivityTest {
    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule<>(MapsActivity.class);

    @Test
    public void is_map_displayed_test(){
        onView(withId(R.id.mapmap)).check(matches(isDisplayed()));
    }
}

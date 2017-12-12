package tinkoff.fintech.cpstool.view;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.view.Gravity;

import org.junit.Rule;
import org.junit.Test;

import tinkoff.fintech.cpstool.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTests {
    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void click_on_search_navigation_item_test() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_search));

        onView(withId(R.id.autoCompleteTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void click_on_history_navigation_item_test() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_history));

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void click_on_manage_navigation_item_test() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_manage));

        onView(withId(R.id.checkDarkMode)).check(matches(isDisplayed()));
        onView(withId(R.id.clearHistoryButton)).check(matches(isDisplayed()));
    }
}
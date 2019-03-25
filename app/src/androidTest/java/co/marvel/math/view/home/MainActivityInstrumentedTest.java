package co.marvel.math.view.home;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import co.marvel.math.R;
import co.marvel.math.utils.FetchingIdlingResource;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mainView = new ActivityScenarioRule<>(MainActivity.class);


    @Before
    public void setup() {
        IdlingRegistry.getInstance().register(FetchingIdlingResource.sharedInstance());
    }

    @Test
    public void shouldEvaluateAValidOperation() {
        // given
        String valueToEnter = "2+(3*5)";
        String resultExpected = "17";

        // when
        onView(ViewMatchers.withId(R.id.editText)).perform(typeText(valueToEnter), closeSoftKeyboard());
        onView(withId(R.id.doMath)).perform(click());

        // then
        onView(withId(R.id.output)).check(matches(withText(resultExpected)));
    }

    @Test
    public void shouldEvaluateAInvalidOperation() {
        // given
        String valueToEnter = "2 + (- 4)";

        // when
        onView(withId(R.id.editText)).perform(typeText(valueToEnter), closeSoftKeyboard());
        onView(withId(R.id.doMath)).perform(click());

        // then
        onView(withId(R.id.output)).check(matches(withText(R.string.invalid)));
    }

    @Test
    public void shouldEvaluateAValidOperationAndGoToTheStoriesListView() throws Exception {
        // given
        String valueToEnter = "2 + (-4)";
        String resultExpected = "-2";

        // when
        onView(withId(R.id.editText)).perform(typeText(valueToEnter), closeSoftKeyboard());
        onView(withId(R.id.doMath)).perform(click());

        // then
        onView(withId(R.id.output)).check(matches(withText(resultExpected)));
        onView(withId(R.id.viewList)).perform(click());
        onView(withId(R.id.results)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
    }
}

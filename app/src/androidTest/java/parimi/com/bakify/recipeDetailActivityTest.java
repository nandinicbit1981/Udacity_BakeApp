package parimi.com.bakify;


import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class recipeDetailActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void ensureStepsShow() {
        onView(withId(R.id.bake_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Type text and then press the button.
        onView(allOf(withId(R.id.content), withText("Recipe Introduction"))).perform(click());
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onView(allOf(withId(R.id.recipe_step_video_container))).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.prev_step)).check(matches(isDisplayed()));

    }


}

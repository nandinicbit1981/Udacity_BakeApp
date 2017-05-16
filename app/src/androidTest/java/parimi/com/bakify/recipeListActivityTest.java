package parimi.com.bakify;

import android.content.res.Resources;
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
import static org.hamcrest.Matchers.anyOf;

/**
 * Created by nandpa on 5/15/17.
 */

@RunWith(AndroidJUnit4.class)
public class recipeListActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void ensureIngredientsShow() {
        onView(withId(R.id.bake_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Type text and then press the button.
        onView(allOf(withId(R.id.content), withText("Ingredients"))).perform(click());
        onView(withId(R.id.ingredients_header)).check(matches(isDisplayed()));



    }

}

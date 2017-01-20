package com.karcompany.imageexplorer;

/**
 * Created by pvkarthik on 2017-01-14.
 *
 * Full scenario instrumentation tests.
 */

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.widget.EditText;

import com.karcompany.imageexplorer.views.activities.ImageSearchActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class BrowseImagesActivityTest {

	@Rule
	public ActivityTestRule<ImageSearchActivity> activityRule = new ActivityTestRule<>(ImageSearchActivity.class);

	@Test
	public void testAllScenarios() {
		onView(withId(R.id.action_search)).perform(click());
		SystemClock.sleep(3000);
		onView(isAssignableFrom(EditText.class)).perform(typeText("mobile"), pressKey(KeyEvent.KEYCODE_ENTER));
		SystemClock.sleep(10000);
		onView(withId(R.id.image_list)).perform(
				RecyclerViewActions.actionOnItemAtPosition(2, click()));
		SystemClock.sleep(3000);
		onView(withId(R.id.dimissBar)).perform(click());
		SystemClock.sleep(3000);
		onView(isAssignableFrom(EditText.class)).perform(pressKey(KeyEvent.KEYCODE_BACK));
		SystemClock.sleep(3000);
		onView(withId(R.id.image_list)).perform(
				RecyclerViewActions.actionOnItemAtPosition(5, click()));
		SystemClock.sleep(3000);
		onView(withId(R.id.dimissBar)).perform(click());
		SystemClock.sleep(3000);
	}

}

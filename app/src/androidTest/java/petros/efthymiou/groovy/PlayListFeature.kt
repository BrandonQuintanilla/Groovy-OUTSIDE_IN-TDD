package petros.efthymiou.groovy

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 *
 */
@RunWith(AndroidJUnit4::class)
class PlayListFeature {

    //Indicates that the test has to target MainActivity
    val mActivity = ActivityTestRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayScreenTitle() {
        // Context of the app under test.
        /*val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("petros.efthymiou.groovy", appContext.packageName)
        */
        assertDisplayed("Playlist")
    }

    @Test
    fun displayListOfPlayList() {

        //It´s an antipatern to wait the app rendering
        Thread.sleep(4000)

        assertRecyclerViewItemCount(R.id.playlist_list, 10)

        onView(
            allOf(
                withId(R.id.playlist_name),
                isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 0))
            )
        ).check(matches(withText("Hard Rock Cafe"))).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.playlist_category),
                isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 0))
            )
        ).check(matches(withText("rock"))).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 1))
            )
        ).check(matches(withDrawable(R.mipmap.playlist))).check(matches(isDisplayed()))
    }

    @Test
    fun displaysLoaderWhileFetchingThePlaylists() {
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hidesLoader() {
        Thread.sleep(4000)
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun displayRockImageForRockListItems() {
        Thread.sleep(4000)

        onView(
            allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 0))
            )
        ).check(matches(withDrawable(R.mipmap.rock))).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 3))
            )
        )
            .check(matches(withDrawable(R.mipmap.rock)))
            .check(matches(isDisplayed()))

    }

    //utils
    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (parentMatcher.matches(parent) && parent.childCount > childPosition && parent.getChildAt(
                    childPosition
                ) == view)
            }
        }
    }

}
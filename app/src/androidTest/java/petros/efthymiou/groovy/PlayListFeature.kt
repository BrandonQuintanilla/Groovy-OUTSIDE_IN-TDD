package petros.efthymiou.groovy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import petros.efthymiou.groovy.playlist.idlingResource

/**
 *
 */
class PlayListFeature : BaseUITest() {

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
        tearDown()
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hidesLoader() {

        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun displayRockImageForRockListItems() {

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
}
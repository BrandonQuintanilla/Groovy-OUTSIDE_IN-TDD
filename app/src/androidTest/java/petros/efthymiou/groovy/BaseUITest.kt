package petros.efthymiou.groovy

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import petros.efthymiou.groovy.playlist.idlingResource

/**
 * Created by Brandon Quintanilla on Nov/17/2022
 */
@RunWith(AndroidJUnit4::class)
abstract class BaseUITest {

    //Indicates that the test has to target MainActivity
    val mActivity = ActivityTestRule(MainActivity::class.java)
        @Rule get

    //before every test
    @Before
    fun setup() {
        //Enables latency until data is fetched
        IdlingRegistry.getInstance().register(idlingResource)
    }

    //after every test
    @After
    fun tearDown() {
        //Unregisters the latency capability
        IdlingRegistry.getInstance().unregister(idlingResource)
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
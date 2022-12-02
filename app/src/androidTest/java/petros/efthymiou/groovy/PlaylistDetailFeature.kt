package petros.efthymiou.groovy

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotExist
import org.hamcrest.Matchers
import org.junit.Test
import petros.efthymiou.groovy.playlist.idlingResource

/**
 * Created by Brandon Quintanilla on Dec/01/2022
 */
class PlaylistDetailFeature : BaseUITest() {

    @Test
    fun displayPlaylistNameAndDetails() {
        navigateToPlaylistDetails(0)

        assertDisplayed("Hard Rock Cafe")
        assertDisplayed("Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")

    }

    @Test
    fun displaysLoaderWhileFetchingThePlaylistDetails() {
        //Allows to not block the execution of our test while waiting the network latency
        IdlingRegistry.getInstance().unregister(idlingResource)
        Thread.sleep(2000)//gives time to unregister
        navigateToPlaylistDetails(0)
        assertDisplayed(R.id.details_loader)
    }

    @Test
    fun hideLoader() {
        navigateToPlaylistDetails(0)
        assertNotDisplayed(R.id.details_loader)
    }

    @Test
    fun displaysErrorMessageWhenNetworkFails() {
        navigateToPlaylistDetails(1)
        assertDisplayed(R.string.generic_error)
    }

    @Test
    fun hidesErrorMessage() {
        navigateToPlaylistDetails(2)
        Thread.sleep(3000)

        //Checks for there is no instance of this value. Not only if it is GONE o NOT_VISIBLE
        assertNotExist(R.string.generic_error)
    }

    private fun navigateToPlaylistDetails(row: Int) {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.playlist_image),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(
                        ViewMatchers.withId(R.id.playlist_list),
                        row
                    )
                )
            )
        ).perform(ViewActions.click())
    }

}
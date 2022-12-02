package petros.efthymiou.groovy

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.Matchers
import org.junit.Test

/**
 * Created by Brandon Quintanilla on Dec/01/2022
 */
class PlaylistDetailFeature : BaseUITest() {

    @Test
    fun displayPlaylistNameAndDetails() {
        navigateToPlaylistDetails()

        assertDisplayed("Hard Rock Cafe")
        assertDisplayed("Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")

    }

    @Test
    fun displaysLoaderWhileFetchingThePlaylistDetails() {
        navigateToPlaylistDetails()
        assertDisplayed(R.id.details_loader)
    }

    private fun navigateToPlaylistDetails() {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.playlist_image),
                ViewMatchers.isDescendantOfA(nthChildOf(ViewMatchers.withId(R.id.playlist_list), 0))
            )
        ).perform(ViewActions.click())
    }
}
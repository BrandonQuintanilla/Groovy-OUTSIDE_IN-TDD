package petros.efthymiou.groovy.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest

/**
 * Created by Brandon Quintanilla on Dec/01/2022
 */
class PlaylistDetailsServiceShould : BaseUnitTest() {

    lateinit var service: PlaylistDetailsService
    private val id = "100"
    private val api:PlaylistDetailsAPI = mock()

    @Test
    fun fetchPlaylistDetailsFromAPI() = runBlockingTest {
        service = PlaylistDetailsService(api)
        service.fetchPlaylistDetails(id).single()
        verify(
            api,
            times(1)
        ).fetchPlaylistDetails(id)
    }
}
package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest

/**
 * Created by Brandon Quintanilla on Nov/13/2022
 */
class PlaylistServiceShould : BaseUnitTest() {

    private lateinit var service: PlayListService
    private val api: PlaylistAPI = mock()

    private val playlists: List<Playlist> = mock()

    @Test
    fun fetchPlaylistFromAPI() = runBlockingTest {

        service = PlayListService(api)
        service.fetchPlaylists().first()
        verify(api, times(1)).fetchAllPlaylists()
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        mockSuccessfulCase()
        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {
        mockErrorCase()
        assertEquals(
            "Something went wrong",
            service.fetchPlaylists().first().exceptionOrNull()?.message
        )
    }

    //mock cases
    private fun mockErrorCase() {
        whenever(api.fetchAllPlaylists())
            .thenThrow(RuntimeException("Damn backend"))
        service = PlayListService(api)
    }

    private fun mockSuccessfulCase() {
        whenever(api.fetchAllPlaylists()).thenReturn(playlists)
        service = PlayListService(api)
    }
}
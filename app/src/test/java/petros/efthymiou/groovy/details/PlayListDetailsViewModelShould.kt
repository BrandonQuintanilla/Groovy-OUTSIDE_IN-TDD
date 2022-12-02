package petros.efthymiou.groovy.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.getValueForTest

/**
 * Created by Brandon Quintanilla on Dec/01/2022
 */
class PlayListDetailsViewModelShould : BaseUnitTest() {

    lateinit var viewmodel: PlayListDetailViewModel
    private val id = "1"
    private val service: PlaylistDetailsService = mock()
    private val playlistDetails: PlaylistDetails = mock()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Something went wrong")
    private val error = Result.failure<PlaylistDetails>(exception)

    @Test
    fun getPlaylistDetailsFromService() = runBlockingTest {
        mockSuccessfulCase()
        viewmodel.playlistDetails.getValueForTest()
        verify(service, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun emitPlayListDetailsFromService() = runBlockingTest {
        mockSuccessfulCase()
        assertEquals(expected, viewmodel.playlistDetails.getValueForTest())
    }

    @Test
    fun emitErrorWhenServiceFails() = runBlockingTest {

        whenever(service.fetchPlaylistDetails(id)).thenReturn(flow {
                emit(error)
            })

        viewmodel = PlayListDetailViewModel(service)
        viewmodel.getPlaylistDetails(id)

        assertEquals(error,viewmodel.playlistDetails.getValueForTest())
    }


    private suspend fun mockSuccessfulCase() {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(flow {
            emit(expected)
        })
        viewmodel = PlayListDetailViewModel(service)
        viewmodel.getPlaylistDetails(id)
    }


}
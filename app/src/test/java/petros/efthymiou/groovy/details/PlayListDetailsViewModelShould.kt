package petros.efthymiou.groovy.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest

/**
 * Created by Brandon Quintanilla on Dec/01/2022
 */
class PlayListDetailsViewModelShould : BaseUnitTest() {

    lateinit var viewModel: PlayListDetailViewModel
    private val id = "1"
    private val service: PlaylistDetailsService = mock()
    private val playlistDetails: PlaylistDetails = mock()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Something went wrong")
    private val error = Result.failure<PlaylistDetails>(exception)

    @Test
    fun getPlaylistDetailsFromService() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)
        viewModel.playlistDetails.getValueForTest()
        verify(service, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun emitPlayListDetailsFromService() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)
        assertEquals(expected, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun emitErrorWhenServiceFails() = runBlockingTest {

        whenever(service.fetchPlaylistDetails(id)).thenReturn(flow {
            emit(error)
        })

        viewModel = PlayListDetailViewModel(service)
        viewModel.getPlaylistDetails(id)

        assertEquals(error, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun showLoaderWhileLoading() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()
            TestCase.assertEquals(true, values[0])
        }
    }

    @Test
    fun closeLoaderAfterPlaylistDetailsLoad() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()
            TestCase.assertEquals(false, values.last())
        }
    }

    private suspend fun mockSuccessfulCase() {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(flow {
            emit(expected)
        })
        viewModel = PlayListDetailViewModel(service)
    }


}
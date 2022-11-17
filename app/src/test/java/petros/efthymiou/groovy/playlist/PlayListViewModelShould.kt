package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PlayListViewModelShould : BaseUnitTest() {

    //    val viewModel: PlayLisViewModel
    val repository: PlaylistRepository = mock()

    private val playlist = mock<List<Playlist>>()
    private val expected = Result.success(playlist)

    private val exception = RuntimeException("something when wrong")

    @Test
    fun getPlayListFromRepository() = runBlockingTest {

        val viewModel = mocksuccessfulCase()
        viewModel.playlist.getValueForTest()
        verify(repository, times(1)).getPlayList()
    }

    //Important to validate bussines logic
    @Test
    fun emitsPlaylistFromRepository() = runBlockingTest {

        val viewModel = mocksuccessfulCase()
        //
        assertEquals(expected, viewModel.playlist.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest {
        val viewModel = mockErrorCase()
        assertEquals(exception, viewModel.playlist.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun closeLoaderAfterPlayListsLoad() = runBlockingTest {
        val viewmodel = mocksuccessfulCase()
        viewmodel.loader.captureValues {
            viewmodel.playlist.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    @Test
    fun closeLoaderAfterError() = runBlockingTest {
        val viewmodel = mockErrorCase()
        viewmodel.loader.captureValues {
            viewmodel.playlist.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    @Test
    fun showSpinnerWhileLoading() = runBlockingTest {
        val viewModel = mocksuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.playlist.getValueForTest()
            assertEquals(true, values[0])
        }
    }

    //vm cases
    private fun mocksuccessfulCase(): PlayLisViewModel {
        runBlockingTest {
            whenever(repository.getPlayList()).thenReturn(flow {
                emit(expected)
            })
        }
        //
        val viewModel = PlayLisViewModel(repository)
        return viewModel
    }

    private fun mockErrorCase(): PlayLisViewModel {
        runBlockingTest {
            whenever(repository.getPlayList()).thenReturn(
                flow {
                    emit(Result.failure<List<Playlist>>(exception))
                }
            )
        }
        val viewModel = PlayLisViewModel(repository)
        return viewModel
    }
}
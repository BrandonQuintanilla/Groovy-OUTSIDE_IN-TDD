package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.utils.BaseUnitTest

/**
 * Created by Brandon Quintanilla on Nov/13/2022
 */
class PlaylistRepositoryShould : BaseUnitTest() {

    private val service: PlayListService = mock()
    private val playlist = mock<List<Playlist>>()
    private val exception = RuntimeException("Somethign went wrong")

    @Test
    fun getsPlaylistFromService() = runBlockingTest {

        val repository = PlaylistRepository(service)

        repository.getPlayList()
        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun emitPlaylistFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        assertEquals(playlist, repository.getPlayList().first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockFailureCase()
        assertEquals(exception, repository.getPlayList().first().exceptionOrNull())
    }

    private suspend fun mockFailureCase(): PlaylistRepository {
        whenever(service.fetchPlaylists())
            .thenReturn(flow {
                emit(Result.failure<List<Playlist>>(exception))
            })

        val repository = PlaylistRepository(service)
        return repository
    }

    //Utils
    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(flow {
            emit(Result.success(playlist))
        })

        val repository = PlaylistRepository(service)
        return repository
    }
}
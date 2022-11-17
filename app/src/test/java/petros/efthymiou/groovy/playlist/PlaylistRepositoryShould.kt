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
    private val mapper: PlaylistMapper = mock()
    private val playlist = mock<List<Playlist>>()
    private val playlistRaw = mock<List<PlaylistRaw>>()
    private val exception = RuntimeException("Somethign went wrong")

    @Test
    fun getsPlaylistFromService() = runBlockingTest {

        val repository = mockSuccessfulCase()

        repository.getPlayList()
        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun emitMappedPlaylistFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        assertEquals(playlist, repository.getPlayList().first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockFailureCase()
        assertEquals(exception, repository.getPlayList().first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper() = runBlockingTest {
        val repository = mockSuccessfulCase()
        repository.getPlayList().first()
        verify(mapper, times(1)).invoke(playlistRaw)
    }

    //Utils
    private suspend fun mockFailureCase(): PlaylistRepository {
        whenever(service.fetchPlaylists())
            .thenReturn(flow {
                emit(Result.failure<List<PlaylistRaw>>(exception))
            })

        return PlaylistRepository(service, mapper)
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        whenever(service.fetchPlaylists())
            .thenReturn(flow {
                emit(Result.success(playlistRaw))
            })

        whenever(mapper.invoke(playlistRaw))
            .thenReturn(playlist)
        return PlaylistRepository(service, mapper)
    }
}
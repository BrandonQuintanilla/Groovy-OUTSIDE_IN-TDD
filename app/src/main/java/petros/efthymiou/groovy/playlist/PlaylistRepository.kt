package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistRepository @Inject constructor(private val service: PlayListService) {

    suspend fun getPlayList(): Flow<Result<List<Playlist>>> = service.fetchPlaylists()

}

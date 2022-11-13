package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow

class PlaylistRepository constructor(private val service: PlayListService) {

    suspend fun getPlayList(): Flow<Result<List<Playlist>>> = service.fetchPlayList()

}

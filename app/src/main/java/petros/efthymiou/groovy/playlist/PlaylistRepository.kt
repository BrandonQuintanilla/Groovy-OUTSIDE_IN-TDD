package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaylistRepository @Inject
constructor(
    private val service: PlayListService,
    private val mapper: PlaylistMapper
) {

    suspend fun getPlayList(): Flow<Result<List<Playlist>>> =
        service
            .fetchPlaylists()
            .map {
                if (it.isSuccess) {
                    Result.success(mapper(it.getOrNull()!!))
                } else {
                    Result.failure(it.exceptionOrNull()!!)
                }
            }

}

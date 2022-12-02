package petros.efthymiou.groovy.details

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistDetailsService @Inject constructor(private val api: PlaylistDetailsAPI) {

    fun fetchPlaylistDetails(id: String): Flow<Result<PlaylistDetails>> {
        return flow {
            emit(Result.success(api.fetchPlaylistDetails(id)))
        }
    }

}

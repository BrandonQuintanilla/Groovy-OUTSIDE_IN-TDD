package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

/**
 * Created by Brandon Quintanilla on Nov/13/2022
 */
class PlayListService @Inject constructor(private val api: PlaylistAPI) {

    suspend fun fetchPlaylists(): Flow<Result<List<PlaylistRaw>>> = flow {
        emit(Result.success(api.fetchAllPlaylists()))
    }.onCompletion {
        //Log.i("TAG", "fetchPlaylists: COMPLETED")
    }
        .catch {
            //Log.i("TAG", "fetchPlaylists: ${it.message}")
            emit(Result.failure(RuntimeException("Something went wrong!!")))
        }


}
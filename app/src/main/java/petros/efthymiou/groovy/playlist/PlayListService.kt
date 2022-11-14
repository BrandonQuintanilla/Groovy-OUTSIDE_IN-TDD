package petros.efthymiou.groovy.playlist

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Created by Brandon Quintanilla on Nov/13/2022
 */
class PlayListService constructor(private val api: PlaylistAPI) {

    fun fetchPlaylists(): Flow<Result<List<Playlist>>> = flow {
        emit(Result.success(api.fetchAllPlaylists()))
    }.catch {
        Log.i("TAG", "fetchPlaylists: ${it.message}")
        emit(Result.failure(RuntimeException("Something went wrong!!")))
    }


}
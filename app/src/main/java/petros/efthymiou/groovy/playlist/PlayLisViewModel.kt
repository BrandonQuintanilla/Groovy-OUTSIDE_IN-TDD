package petros.efthymiou.groovy.playlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.onEach

class PlayLisViewModel constructor(private val repository: PlaylistRepository) : ViewModel() {

    val loader = MutableLiveData<Boolean>()

    val playlist = liveData<Result<List<Playlist>>>() {
        loader.postValue(true)
        emitSource(
            repository
                .getPlayList()
                .onEach {
                    loader.postValue(false)
                }
                .asLiveData()
        )
    }
}

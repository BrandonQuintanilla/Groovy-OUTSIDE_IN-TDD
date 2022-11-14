package petros.efthymiou.groovy.playlist

import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlayLisViewModel constructor(private val repository: PlaylistRepository) : ViewModel() {

    val playlist = liveData<Result<List<Playlist>>>() {
        emitSource(
            repository
                .getPlayList()
                .asLiveData()
        )
    }
/*
    val playlist = MutableLiveData<Result<List<Playlist>>>()

    init {
        viewModelScope.launch {
            repository.getPlayList()
                .collect {
                    playlist.value = it
                }
        }
    }*/

}

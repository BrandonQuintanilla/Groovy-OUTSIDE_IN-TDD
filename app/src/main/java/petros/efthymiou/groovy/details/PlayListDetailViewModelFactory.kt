package petros.efthymiou.groovy.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petros.efthymiou.groovy.playlist.PlayLisViewModel
import petros.efthymiou.groovy.playlist.PlaylistRepository
import javax.inject.Inject

class PlayListDetailViewModelFactory @Inject constructor(private val repository: PlaylistRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayLisViewModel(repository) as T
    }
}

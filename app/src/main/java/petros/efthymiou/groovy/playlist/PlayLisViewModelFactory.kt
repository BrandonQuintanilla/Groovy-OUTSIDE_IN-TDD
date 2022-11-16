package petros.efthymiou.groovy.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class PlayLisViewModelFactory @Inject constructor(private val repository: PlaylistRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayLisViewModel(repository) as T
    }

}

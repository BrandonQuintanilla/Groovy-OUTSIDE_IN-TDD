package petros.efthymiou.groovy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayLisViewModel: ViewModel() {

    val playlist = MutableLiveData<Result<List<Playlist>>>()


}

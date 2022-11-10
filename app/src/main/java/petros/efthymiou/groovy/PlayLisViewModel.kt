package petros.efthymiou.groovy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayLisViewModel: ViewModel() {

    val plaulist = MutableLiveData<Result<List<Playlist>>>()


}

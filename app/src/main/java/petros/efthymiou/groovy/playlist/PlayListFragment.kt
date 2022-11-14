package petros.efthymiou.groovy.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import petros.efthymiou.groovy.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 */
class PlayListFragment : Fragment() {

    lateinit var viewModel: PlayLisViewModel
    lateinit var viewModelFactory: PlayLisViewModelFactory

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.4:2999/")// please check local ip
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(PlaylistAPI::class.java)

    private val service = PlayListService(api)
    private val repository = PlaylistRepository(service)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)

        setupViewModel()

        viewModel.playlist.observe(this) { playlist ->
            if (playlist.getOrNull() != null) {
                setupList(view, playlist.getOrNull()!!)
            } else {
                //TODO
            }
        }

        return view
    }

    private fun setupList(
        view: View?,
        playlist: List<Playlist>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyPlayListRecyclerViewAdapter(playlist)
        }
    }

    private fun setupViewModel() {
        viewModelFactory = PlayLisViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlayLisViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlayListFragment().apply {}
    }
}
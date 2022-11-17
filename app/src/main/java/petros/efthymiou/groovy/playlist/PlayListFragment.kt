package petros.efthymiou.groovy.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist.*
import kotlinx.android.synthetic.main.fragment_playlist.view.*
import petros.efthymiou.groovy.R
import javax.inject.Inject

/**
 */
@AndroidEntryPoint
class PlayListFragment : Fragment() {

    lateinit var viewModel: PlayLisViewModel

    @Inject
    lateinit var viewModelFactory: PlayLisViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)

        setupViewModel()

        viewModel.loader.observe(this){ loading->
            when(loading){
                true-> loader.visibility = View.VISIBLE
                false-> loader.visibility = View.GONE
            }
        }

        viewModel.playlist.observe(this) { playlist ->
            if (playlist.getOrNull() != null) {
                setupList(view.playlist_list, playlist.getOrNull()!!)
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
            adapter = MyPlayListRecyclerViewAdapter(playlist) { id ->
                val action =
                    PlayListFragmentDirections.actionPlayListFragmentToPlaylistDetailFragment(id)
                findNavController().navigate(action)
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlayLisViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlayListFragment().apply {}
    }
}
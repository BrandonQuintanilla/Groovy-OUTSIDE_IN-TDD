package petros.efthymiou.groovy.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_playlist.view.*
import kotlinx.android.synthetic.main.fragment_playlist_detail.*
import petros.efthymiou.groovy.PlaylistDetailFragmentArgs
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.playlist.PlayLisViewModel
import petros.efthymiou.groovy.playlist.PlayLisViewModelFactory
import javax.inject.Inject

/**
 */
class PlaylistDetailFragment : Fragment() {

    lateinit var viewModel: PlayListDetailViewModel

    @Inject
    lateinit var viewModelFactory: PlayListDetailViewModelFactory

    val args: PlaylistDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_playlist_detail, container, false)
        val id = args.playlistId
        setupViewModel()
        viewModel.getPlaylistDetails(id)
        observeLiveData()
        return view
    }

    private fun observeLiveData() {
        viewModel.playlistDetails.observe(viewLifecycleOwner) { playlistDetails ->
            if (playlistDetails.getOrNull() != null) {
                setupUI(playlistDetails)
            } else {
                //TODO
            }
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PlayListDetailViewModel::class.java)
    }

    private fun setupUI(playlistDetails: Result<PlaylistDetails>) {
        playlist_name.text = playlistDetails.getOrNull()!!.name
        playlist_details.text = playlistDetails.getOrNull()!!.details
    }

    companion object {
        /**
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlaylistDetailFragment().apply {
            }
    }
}
package petros.efthymiou.groovy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist_detail.*
import petros.efthymiou.groovy.R
import javax.inject.Inject

/**
 */
@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {

    lateinit var viewModel: PlayListDetailViewModel

    @Inject
    lateinit var viewModelFactory: PlayListDetailViewModelFactory

    val args: PlaylistDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_playlist_detail, container, false)
        val id = args.playlistId
        setupViewModel()
        viewModel.getPlaylistDetails(id)
        observePlaylistDetails()
        observeLoader()
        return view
    }

    private fun observePlaylistDetails() {
        viewModel.playlistDetails.observe(viewLifecycleOwner) { playlistDetails ->
            if (playlistDetails.getOrNull() != null) {
                setupUI(playlistDetails)
            } else {
                Snackbar.make(playlist_details_root, R.string.generic_error, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun observeLoader() {
        viewModel.loader.observe(this) { loading ->
            when (loading) {
                true -> details_loader.visibility = View.VISIBLE
                else -> details_loader.visibility = View.GONE
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
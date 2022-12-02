package petros.efthymiou.groovy.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.getValueForTest

/**
 * Created by Brandon Quintanilla on Dec/01/2022
 */
class PlayListDetailsViewModelShould : BaseUnitTest() {

    lateinit var viewmodel: PlayListDetailViewModel
    private val id = "1"
    private val service: PlaylistDetailsService = mock()

    @Test
    fun getPlaylistDetailsFromService() {
        viewmodel = PlayListDetailViewModel(service )
        viewmodel.getPlaylistDetails(id)
        viewmodel.playlistDetails.getValueForTest()
        verify(service, times(1)).fetchPlaylistDetails(id)
    }
}
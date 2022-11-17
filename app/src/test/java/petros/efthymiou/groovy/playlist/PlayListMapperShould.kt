package petros.efthymiou.groovy.playlist

import junit.framework.TestCase.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.utils.BaseUnitTest

/**
 * Created by Brandon Quintanilla on Nov/16/2022
 */
class PlayListMapperShould : BaseUnitTest() {

    private val playlistRaw = PlaylistRaw("1", "da name", "da category")
    private val playlistRawRock = PlaylistRaw("1", "da name", "rock")
    private val mapper = PlaylistMapper()
    private val playlists = mapper(listOf(playlistRaw))//Playlist("1","da name", "da category")
    private val playlistsRock = mapper(listOf(playlistRawRock))//Playlist("1","da name", "da category")

    private val playlist= playlists[0]
    private val playlistRock= playlistsRock[0]

    //Define special requirements anda operations of Mapper
    @Test
    fun keepSameId() {
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun keepSameName(){
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun keepSameCategory(){
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock(){
        assertEquals(R.mipmap.playlist,playlist.image)
    }

    @Test
    fun mapRockImageWhenRockCategory(){
        assertEquals(R.mipmap.rock,playlistRock.image)
    }
}
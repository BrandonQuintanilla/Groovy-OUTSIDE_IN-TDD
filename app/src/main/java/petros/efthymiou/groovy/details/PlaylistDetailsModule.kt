package petros.efthymiou.groovy.details

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import retrofit2.Retrofit

/**
 * Created by Brandon Quintanilla on Dec/01/2022
 */
@Module
@InstallIn(FragmentComponent::class)
class PlaylistDetailsModule {

    @Provides
    fun playlistDetailsAPI(retrofit: Retrofit): PlaylistDetailsAPI {
        return retrofit.create(PlaylistDetailsAPI::class.java)
    }
}
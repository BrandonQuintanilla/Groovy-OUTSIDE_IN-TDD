package petros.efthymiou.groovy.playlist

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Brandon Quintanilla on Nov/15/2022
 */
@Module
@InstallIn(FragmentComponent::class)
class PlaylistModule {

    @Provides
    fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.4:2999/")// please check local ip
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun playlistAPI(retrofit: Retrofit): PlaylistAPI {
        return retrofit.create(PlaylistAPI::class.java)
    }

}
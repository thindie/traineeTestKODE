package com.example.thindie.kodetrainee.data.remote.di

import android.app.Application
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import com.example.thindie.kodetrainee.data.remote.EmployeesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module()
@InstallIn(SingletonComponent::class)

object RemoteServiceModule {
    @Provides
    fun provideService(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ENDPOINT)
            .build()
    }

    @Provides
    fun provideOkHttpClient() = OkHttpClient()
        .newBuilder()
        .build()

    @Provides
    internal fun provideRemoteService(retrofit: Retrofit): EmployeesService {
        return retrofit.create(EmployeesService::class.java)
    }

}


@Module
@InstallIn(SingletonComponent::class)
class NetworkRequestModule {

    @Provides
    fun provideConnectivityManager(
        context: Application,
    ): ConnectivityManager {
        return getSystemService(context, ConnectivityManager::class.java) as ConnectivityManager
    }

}


@Module
@InstallIn(SingletonComponent::class)
class DispatcherProviderModule {

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}

private const val ENDPOINT = "https://stoplight.io/mocks/kode-education/trainee-test/25143926/"
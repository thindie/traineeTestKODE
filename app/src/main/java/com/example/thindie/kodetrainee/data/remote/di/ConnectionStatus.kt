package com.example.thindie.kodetrainee.data.remote.di

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject
import kotlinx.coroutines.withContext


class ConnectionStatus @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val provider: DispatchersProvider,
) {


    suspend fun isConnected(): Boolean {
        return isConnectionOn() && isInternetAvailable()
    }

    private fun isConnectionOn(): Boolean {
        return postAndroidMInternetCheck(connectivityManager)
    }

    private suspend fun isInternetAvailable(): Boolean {
        return withContext(provider.ioDispatcher) {
            try {
                val sock = Socket()
                val socketAddress = InetSocketAddress(GOOGLE, PORT)
                sock.connect(socketAddress, 5000)
                sock.close()
                true
            } catch (e: IOException) {
                false
            }
        }
    }

    private fun postAndroidMInternetCheck(
        connectivityManager: ConnectivityManager,
    ): Boolean {
        val network = connectivityManager.activeNetwork
        val connection =
            connectivityManager.getNetworkCapabilities(network)

        return connection != null && (
                connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    companion object {
        private const val TIMEOUT = 1500
        private const val GOOGLE = "8.8.8.8"
        private const val PORT = 53
    }
}



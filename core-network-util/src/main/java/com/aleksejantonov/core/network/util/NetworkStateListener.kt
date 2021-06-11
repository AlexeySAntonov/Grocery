package com.aleksejantonov.core.network.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkStateListener @Inject constructor(app: Application) {

  val networkConnectionFlow = callbackFlow<NetworkState> {
    val networkCallback: ConnectivityManager.NetworkCallback by lazy {
      object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
          offer(NetworkState.AVAILABLE)
        }

        override fun onUnavailable() {
          offer(NetworkState.LOST)
        }

        override fun onLost(network: Network) {
          offer(NetworkState.LOST)
        }
      }
    }

    val manager: ConnectivityManager by lazy { app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      manager.registerDefaultNetworkCallback(networkCallback)
    } else {
      val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()
      manager.registerNetworkCallback(networkRequest, networkCallback)
    }

    awaitClose { manager.unregisterNetworkCallback(networkCallback) }
  }

}
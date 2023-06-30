package com.example.thindie.kodetrainee.presentation.di

import android.app.Application
import android.content.Context
import android.net.wifi.WifiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class WifiManagerModule {
    @Provides
    fun provideWifiManager(context: Application): WifiManager {
        return context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }
}
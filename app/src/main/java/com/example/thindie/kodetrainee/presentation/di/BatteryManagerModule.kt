package com.example.thindie.kodetrainee.presentation.di

import android.app.Application
import android.content.Context
import android.os.BatteryManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class BatteryManagerModule {
    @Provides
    fun provideBatteryPercentage(context: Application): BatteryManager {
        return context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    }
}
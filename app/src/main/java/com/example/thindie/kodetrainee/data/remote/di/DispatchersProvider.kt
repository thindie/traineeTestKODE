package com.example.thindie.kodetrainee.data.remote.di

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

class DispatchersProvider @Inject constructor(val ioDispatcher: CoroutineDispatcher)
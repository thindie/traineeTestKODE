package com.example.thindie.kodetrainee.presentation.di

import com.example.thindie.kodetrainee.presentation.AlphabetCompare
import com.example.thindie.kodetrainee.presentation.BirthDayCompare
import javax.inject.Inject

class FilterHolder @Inject constructor(
    val alphabetCompare: AlphabetCompare,
    val birthDayCompare: BirthDayCompare,
)
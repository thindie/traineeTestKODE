package com.example.thindie.kodetrainee.presentation.uiunits

interface SearchAble {
    fun getTagShadow(): String

    fun getID(): String

    fun <T> getMajorComponent(): T

    fun getMajorComponent1(): String

    fun <T : SearchAble> getReified(): T {
        return this as T
    }
}
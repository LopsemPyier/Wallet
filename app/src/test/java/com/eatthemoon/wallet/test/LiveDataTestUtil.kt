package com.eatthemoon.wallet.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeForTesting(block: () -> Unit = {}) {
    val observer = Observer<T> { }
    try {
        observeForever(observer)
        block()
    } finally {
        removeObserver(observer)
    }
}

val <T> LiveData<T>.forceGet: T
    get() = this.value!!
package com.dicoding.footballapps.utils

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

class TestContextProvider : CoroutineContextProvider() {
    @kotlinx.coroutines.ExperimentalCoroutinesApi
    override val main: CoroutineContext = Dispatchers.Unconfined
}
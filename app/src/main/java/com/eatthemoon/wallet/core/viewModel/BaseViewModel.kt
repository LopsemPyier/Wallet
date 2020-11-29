package com.eatthemoon.wallet.core.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
abstract class BaseViewModel<Intent: ViewIntent, Event: UiEvent> : ViewModel() {

    private val _intentChannel = ConflatedBroadcastChannel<Intent>()

    private val _uiEvent = MutableLiveData<Event>()
    val uiEvent: LiveData<Event>
        get() = _uiEvent

    init {
        _intentChannel
                .asFlow()
                .onEach { viewIntent ->
                    onViewIntent(viewIntent)
                }
                .launchIn(viewModelScope)
    }

    suspend fun processIntent(intent: Intent) = _intentChannel.send(intent)

    protected abstract suspend fun onViewIntent(viewIntent : Intent)

    protected fun addUiEvent(uiEvent: Event) {
        _uiEvent.value = uiEvent
    }
}
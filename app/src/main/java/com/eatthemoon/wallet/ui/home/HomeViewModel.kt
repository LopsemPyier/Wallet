package com.eatthemoon.wallet.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.eatthemoon.wallet.core.viewModel.BaseViewModel
import com.eatthemoon.wallet.db.entity.CurrentEntity
import com.eatthemoon.wallet.ui.home.adapter.items.PaymentItemImpl
import com.eatthemoon.wallet.useCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class HomeViewModel(
    observeCurrentUseCase: ObserveCurrentUseCase,
    observeIfCurrentInsertedUseCase: ObserveIfCurrentInsertedUseCase,
    observePaymentsUseCase: ObservePaymentsUseCase
): BaseViewModel<HomeViewIntent, HomeUiEvent>() {

    private val _current = observeCurrentUseCase
        .observe()
        .asLiveData(viewModelScope.coroutineContext)
    val current: LiveData<CurrentEntity>
        get() = _current

    private val _payments = observePaymentsUseCase
        .observe()
        .map { paymentsList ->
            paymentsList
                .map {
                    PaymentItemImpl(
                        amount = it.amount,
                        location = it.location,
                        date = it.date,
                        category = it.category,
                        ID = it.ID ?: -1
                    )
                }
        }
        .asLiveData(viewModelScope.coroutineContext)
    val payments : LiveData<List<PaymentItemImpl>>
        get() = _payments


    init {
        observeIfCurrentInsertedUseCase
            .observe()
            .onEach {
                if (!it) goInsertCurrent()
                else currentLoaded()
            }
            .launchIn(viewModelScope)

        observeIfCurrentInsertedUseCase(Unit)
        observeCurrentUseCase(Unit)
        observePaymentsUseCase(
            ObservePaymentsUseCase.Params(
                sortBy = PaymentSortingType.DateDesc,
                limitType = PaymentLimitType.ThisMonth
            )
        )
    }

    override suspend fun onViewIntent(viewIntent: HomeViewIntent) {

    }

    private fun goInsertCurrent() {
        addUiEvent(HomeUiEvent.GoInsertCurrent)
    }

    private fun currentLoaded() {
        addUiEvent(HomeUiEvent.DataLoaded)
    }
}
package com.eatthemoon.wallet.ui.listPayments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.eatthemoon.wallet.core.viewModel.BaseViewModel
import com.eatthemoon.wallet.ui.home.adapter.items.PaymentItemImpl
import com.eatthemoon.wallet.useCase.ObservePaymentsUseCase
import com.eatthemoon.wallet.useCase.PaymentLimitType
import com.eatthemoon.wallet.useCase.PaymentSortingType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.map

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ListViewModel(
        private val observePaymentsUseCase: ObservePaymentsUseCase
) : BaseViewModel<ListViewIntent, ListUiEvent>() {

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

    private val _sortBy = MutableLiveData<PaymentSortingType>(PaymentSortingType.DateDesc)
    private val _limitType = MutableLiveData<PaymentLimitType>(PaymentLimitType.ThisMonth)
    val sortBy: LiveData<PaymentSortingType>
        get() = _sortBy
    val limitType: LiveData<PaymentLimitType>
        get() = _limitType

    init {
        sortBy
                .observeForever {
                    it?.let {
                        observePayments(it, limitType.value ?: PaymentLimitType.ThisMonth)
                    }
                }

        limitType
                .observeForever {
                    it?.let {
                        observePayments(sortBy.value ?: PaymentSortingType.DateDesc, it)
                    }
                }

    }

    private fun observePayments(sortBy: PaymentSortingType, limitType: PaymentLimitType) {
        observePaymentsUseCase(ObservePaymentsUseCase.Params(
                sortBy = sortBy,
                limitType = limitType
        ))
    }

    override suspend fun onViewIntent(viewIntent: ListViewIntent) {
        when (viewIntent) {
            is ListViewIntent.SortByAmount -> {
                when (_sortBy.value) {
                    PaymentSortingType.AmountDesc, PaymentSortingType.DateDesc, PaymentSortingType.DateAsc ->
                        _sortBy.value = PaymentSortingType.AmountAsc
                    PaymentSortingType.AmountAsc ->
                        _sortBy.value = PaymentSortingType.AmountDesc
                }
            }
            is ListViewIntent.SortByDate -> {
                when (_sortBy.value) {
                    PaymentSortingType.DateAsc, PaymentSortingType.AmountDesc, PaymentSortingType.AmountAsc ->
                        _sortBy.value = PaymentSortingType.DateDesc
                    PaymentSortingType.DateDesc ->
                        _sortBy.value = PaymentSortingType.DateAsc
                }
            }
            is ListViewIntent.Limit -> {
                _limitType.value = viewIntent.limit
            }
        }
    }
}
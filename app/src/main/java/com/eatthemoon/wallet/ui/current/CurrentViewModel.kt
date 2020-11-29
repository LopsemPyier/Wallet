package com.eatthemoon.wallet.ui.current

import com.eatthemoon.wallet.core.viewModel.BaseViewModel
import com.eatthemoon.wallet.useCase.InsertCurrentUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class CurrentViewModel(
    private val insertCurrentUseCase: InsertCurrentUseCase
) : BaseViewModel<CurrentViewIntent, CurrentUiEvent>() {

    override suspend fun onViewIntent(viewIntent: CurrentViewIntent) {
        when (viewIntent) {
            is CurrentViewIntent.InsertCurrent -> insertCurrent(current = viewIntent)
        }
    }

    private suspend fun insertCurrent(current: CurrentViewIntent.InsertCurrent) {
        if (current.amount < 0) {
            addUiEvent(CurrentUiEvent.ErrorInvalidAmount)
            return
        }

        insertCurrentUseCase(
            InsertCurrentUseCase.Params(
                amount = current.amount
            )
        )

        addUiEvent(CurrentUiEvent.CurrentInserted)
    }
}
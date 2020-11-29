package com.eatthemoon.wallet.useCase

import android.util.Log
import com.eatthemoon.wallet.core.db.Converters
import com.eatthemoon.wallet.core.dispatchers.AppCoroutineDispatchers
import com.eatthemoon.wallet.core.useCase.FlowUseCase
import com.eatthemoon.wallet.db.entity.PaymentEntity
import com.eatthemoon.wallet.repository.PaymentRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDateTime

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ObservePaymentsUseCase(
        private val appCoroutineDispatchers: AppCoroutineDispatchers,
        private val paymentRepo: PaymentRepo
): FlowUseCase<List<PaymentEntity>, ObservePaymentsUseCase.Params>() {

    data class Params(
            val sortBy: PaymentSortingType,
            val limitType: PaymentLimitType
    )

    override fun doWork(params: Params): Flow<List<PaymentEntity>> =
        paymentRepo.observePayments(
            sortBy = PaymentSortingType.sortBy(params.sortBy),
            from = PaymentLimitType.from(params.limitType),
            to = PaymentLimitType.to(params.limitType),
            desc = PaymentSortingType.desc(params.sortBy)
        )

    override val dispatcher: CoroutineDispatcher
        get() = appCoroutineDispatchers.io
}

sealed class PaymentLimitType {
    object NoLimit: PaymentLimitType()
    object ThisWeek: PaymentLimitType()
    object LastWeek: PaymentLimitType()
    object ThisMonth: PaymentLimitType()
    object LastMonth: PaymentLimitType()
    object ThisYear: PaymentLimitType()

    companion object {

        private val today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0)
        private val startOfMonth = today.withDayOfMonth(1)
        private val startOfWeek = today.minusDays(today.dayOfWeek.value.toLong())
        private val startOfYear = today.withDayOfYear(1)

        fun to(limitType: PaymentLimitType): LocalDateTime {
            return when (limitType) {
                ThisWeek, ThisMonth, ThisYear, NoLimit-> today.plusDays(1)
                LastWeek -> startOfWeek
                LastMonth -> startOfMonth
            }
        }

        fun from(limitType: PaymentLimitType): LocalDateTime {
            return when (limitType) {
                ThisWeek -> startOfWeek
                ThisMonth -> startOfMonth
                ThisYear -> startOfYear
                LastWeek -> startOfWeek.minusWeeks(1)
                LastMonth -> startOfMonth.minusMonths(1)
                NoLimit -> startOfYear
            }
        }
    }
}

sealed class PaymentSortingType {
    object DateAsc: PaymentSortingType()
    object DateDesc: PaymentSortingType()
    object AmountAsc: PaymentSortingType()
    object AmountDesc: PaymentSortingType()

    companion object {
        fun sortBy(sortingType: PaymentSortingType): String {
            return when (sortingType) {
                DateAsc, DateDesc -> "date"
                AmountAsc, AmountDesc -> "amount"
            }
        }

        fun desc(sortingType: PaymentSortingType): Boolean {
            return when (sortingType) {
                AmountAsc, DateAsc -> false
                AmountDesc, DateDesc -> true
            }
        }
    }
}
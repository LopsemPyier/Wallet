package com.eatthemoon.wallet.useCase

import com.eatthemoon.wallet.test.UnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ObservePaymentsUseCaseTest : UnitTest() {
    /* @Test
    fun test_should_return_payments_from_repository() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val payments = listOf(
                PaymentEntity(
                    4.90,
                    "Boulangerie",
                    LocalDateTime.now(ZoneId.systemDefault())
                ),
                PaymentEntity(
                    6.20,
                    "FNAC",
                    LocalDateTime.now(ZoneId.systemDefault()).minusWeeks(3)
                )
            )
            val mockPaymentRepo = MockPaymentRepo(observedPayments = payments)

            val useCase =
                ObservePaymentsUseCase(
                    appCoroutineDispatchers = testAppCoroutineDispatchers,
                    paymentRepo = mockPaymentRepo
                )

            useCase(
                ObservePaymentsUseCase.Params(
                    sortBy = PaymentSortingType.DateAsc,
                    limitType = PaymentLimitType.ThisMonth
            ))

            launch {
                useCase.observe().test { assertEquals(payments, expectItem()) }
            }
        } */
}
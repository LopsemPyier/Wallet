package com.eatthemoon.wallet.useCase

import com.eatthemoon.wallet.test.UnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ObserveCurrentUseCaseTest : UnitTest() {
    /* @Test
    fun test_should_return_current_from_repository() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val current = CurrentEntity(
                20.84
            )
            val mockGithubLocalRepo = MockCurrentRepo(observedCurrent = current)

            val useCase =
                ObserveCurrentUseCase(
                    appCoroutineDispatchers = testAppCoroutineDispatchers,
                    currentRepo = mockGithubLocalRepo
                )

            useCase(Unit)

            launch {
                useCase.observe().test { assertEquals(current, expectItem()) }
            }
        } */
}
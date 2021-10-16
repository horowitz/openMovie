package com.dhorowitz.openmovie.test.wait

import java.util.concurrent.TimeUnit

object ConditionWatcher {

    private const val CONDITION_NOT_MET = 0
    private const val CONDITION_MET = 1
    private const val CONDITION_TIMED_OUT = 2
    // 2 seconds by default time out. I do not really want the tests to take too long
    private const val DEFAULT_TIMEOUT_SECONDS = 2L

    private const val WATCH_INTERVAL_MS = 100 // We check every 100ms

    @Suppress("LongMethod")
    @Throws(IllegalStateException::class)
    fun waitForCondition(
        instruction: Instruction,
        timeoutLimitMs: Long = TimeUnit.SECONDS.toMillis(DEFAULT_TIMEOUT_SECONDS)
    ) {
        var status = CONDITION_NOT_MET
        var elapsedTime = 0
        var error: Throwable? = null

        do {
            when (val conditionResult = instruction.checkCondition()) {
                is ConditionResult.Met -> {
                    status = CONDITION_MET
                }
                is ConditionResult.NotMet -> {
                    error = conditionResult.error
                    elapsedTime += WATCH_INTERVAL_MS
                    Thread.sleep(WATCH_INTERVAL_MS.toLong())
                }
            }

            if (elapsedTime >= timeoutLimitMs) {
                status = CONDITION_TIMED_OUT
                break
            }
        } while (status != CONDITION_MET)

        if (status == CONDITION_TIMED_OUT && error != null) {
            val genericMessage =
                "${instruction.javaClass.simpleName} took more than ${TimeUnit.MILLISECONDS.toSeconds(
                    timeoutLimitMs
                )} seconds. Test stopped. See the root cause below."
            throw IllegalStateException(genericMessage, error)
        }
    }
}

package com.dhorowitz.openmovie.test.wait

fun waitUntil(block: () -> Unit) {
    waitForCondition(condition = {
        try {
            block()
            true
        } catch (e: Exception) {
            throw e
        }
    }
    )
}

/**
 * Waits for the given `condition` to return `true`.
 * If the timeout elapses before the condition returns `true`, this method throws an exception.
 * @param reason    Reason printed when waiting for condition timeouts.
 * @param condition Condition to wait for.
 * @param timeout   Timeout in ms.
 */
private fun waitForCondition(
    condition: () -> Boolean,
    reason: String = "waiting for condition",
    timeout: Long = 2000L
) {
    val end = System.currentTimeMillis() + timeout

    try {
        while (!condition.invoke()) {
            if (System.currentTimeMillis() > end) {
                throw AssertionError(reason)
            }

            Thread.sleep(100)
        }
    } catch (e: Exception) {
        throw IllegalStateException(e.message)
    }
}
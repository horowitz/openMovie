package com.dhorowitz.openmovie.test.wait

import com.dhorowitz.openmovie.test.wait.ConditionResult.Met
import com.dhorowitz.openmovie.test.wait.ConditionResult.NotMet
import com.dhorowitz.openmovie.test.wait.ConditionWatcher.waitForCondition

/**
 * This instruction will try to run the [assertionBlock] - which should contain either a Barista
 * or Espresso assertion - and will return [Met] if it succeeds or [NotMet] otherwise.
 */
open class GenericInstruction(private val assertionBlock: () -> Unit) : Instruction {

    @Suppress("SwallowedException", "Detekt.TooGenericExceptionCaught")
    override fun checkCondition(): ConditionResult {
        return try {
            assertionBlock()
            Met
        } catch (e: Throwable) {
            NotMet(e)
        }
    }
}

fun waitUntil(block: () -> Unit) {
    waitForCondition(GenericInstruction(block))
}

fun waitUntilWithTimeout(timeoutLimitMs: Long, block: () -> Unit) {
    waitForCondition(GenericInstruction(block), timeoutLimitMs)
}
package com.dhorowitz.openmovie.test.wait

import com.dhorowitz.openmovie.test.wait.ConditionResult.Met
import com.dhorowitz.openmovie.test.wait.ConditionResult.NotMet


sealed class ConditionResult {
    object Met : ConditionResult()
    data class NotMet(val error: Throwable) : ConditionResult()
}

fun Boolean.toConditionResult(errorMessage: String) = when (this) {
    true -> Met
    false -> NotMet(IllegalStateException(errorMessage))
}

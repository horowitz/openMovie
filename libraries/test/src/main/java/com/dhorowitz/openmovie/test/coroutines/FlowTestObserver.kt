package com.dhorowitz.openmovie.test.coroutines

import java.io.Closeable
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.test(
    scope: CoroutineScope,
    context: CoroutineContext = UnconfinedTestDispatcher()
): FlowTestObserver<T> = FlowTestObserver(scope, context, this)

@OptIn(ExperimentalCoroutinesApi::class)
class FlowTestObserver<T> internal constructor(
    scope: CoroutineScope,
    context: CoroutineContext = UnconfinedTestDispatcher(),
    flow: Flow<T>
) : Closeable {

    private val _values = mutableListOf<T>()
    val values: List<T>
        get() = _values

    private val _errors = mutableListOf<Throwable>()

    private val job: Job = scope.launch(context) {
        flow.catch { _errors.add(it) }
            .collect { _values.add(it) }
    }

    override fun close() {
        job.cancel()
    }
}

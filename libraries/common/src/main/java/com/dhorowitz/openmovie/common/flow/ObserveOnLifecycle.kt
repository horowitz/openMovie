package com.dhorowitz.openmovie.common.flow

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

inline fun <reified T> Flow<T>.observeOnLifecycle(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    distinctUntilChanged: Boolean = false,
    noinline action: suspend (T) -> Unit,
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).let {
        if (distinctUntilChanged) it.distinctUntilChanged() else it
    }.collect { action.invoke(it) }
}
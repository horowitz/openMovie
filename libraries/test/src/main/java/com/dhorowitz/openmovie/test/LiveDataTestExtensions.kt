package com.dhorowitz.openmovie.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

fun <T> LiveData<T>.test(): TestObserver<T> {
    val observer = TestObserver<T>()
    observeForever(observer)
    return observer
}

class TestObserver<T> : Observer<T> {

    private val _items = mutableListOf<T>()

    val items: List<T>
        get() = _items

    override fun onChanged(t: T) {
        _items.add(t)
    }

    fun assertValues(vararg values: T): TestObserver<T> = also {
        assertEquals(items, values.toList())
    }

    fun assertOnce(value: T): TestObserver<T> = also {
        assertTrue(items.count { it == value } == 1)
    }
}
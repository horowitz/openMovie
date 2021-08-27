package com.dhorowitz.openmovie.test

import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class OpenMovieRule(testInstance: Any) : TestRule {

    val hiltAndroidRule = HiltAndroidRule(testInstance)

    private val ruleChain: RuleChain = RuleChain
        .outerRule(hiltAndroidRule)
        .around(MainCoroutineRule())

    override fun apply(base: Statement, description: Description): Statement =
        ruleChain.apply(base, description)
}
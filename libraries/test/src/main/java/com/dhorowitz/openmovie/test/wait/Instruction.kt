package com.dhorowitz.openmovie.test.wait

interface Instruction {

    fun checkCondition(): ConditionResult
}

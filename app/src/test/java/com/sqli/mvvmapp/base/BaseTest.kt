package com.sqli.mvvmapp.base

import com.sqli.mvvmapp.rule.TrampolineSchedulerRule
import org.junit.Rule

abstract class BaseTest {

    @get:Rule
    val trampolineSchedulerRule: TrampolineSchedulerRule = TrampolineSchedulerRule()

}
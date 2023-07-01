package com.example.sample.compose.di

import android.content.Context

class TestDependency(val context: Context) {
    fun test() {
        println("TestDependency.test()")
    }

    fun test2() {
        println("1+2 == 3")
    }
}

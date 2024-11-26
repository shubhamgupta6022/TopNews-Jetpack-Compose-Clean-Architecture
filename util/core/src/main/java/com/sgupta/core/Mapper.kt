package com.sgupta.core

interface Mapper<F, T> {
    fun convert(from: F): T
}
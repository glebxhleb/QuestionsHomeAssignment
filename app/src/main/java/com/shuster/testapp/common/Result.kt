package com.shuster.testapp.common

import kotlin.coroutines.cancellation.CancellationException

sealed class  Result<out T>

data class Success<T>(val data: T) : Result<T>()

data class Failure(val exception: Throwable) : Result<Nothing>()

inline fun <R> runCatchingResult(block: () -> R): Result<R> {
    return try {
        Success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        Failure(e)
    }
}
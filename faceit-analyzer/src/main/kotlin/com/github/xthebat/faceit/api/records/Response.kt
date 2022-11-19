package com.github.xthebat.faceit.api.records


data class Response<T : ResponsePayload>(val result: ResponseResult, val payload: T)
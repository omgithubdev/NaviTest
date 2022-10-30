package com.omagrahari.navitest.model.common

data class Response<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): Response<T> {
            return Response(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Error?, data: T?): Response<T> {
            return Response(Status.ERROR, data, error?.message)
        }

        fun <T> loading(): Response<T> {
            return Response(Status.LOADING, null, null)
        }
    }

}
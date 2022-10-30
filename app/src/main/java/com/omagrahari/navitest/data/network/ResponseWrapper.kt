package com.omagrahari.navitest.data.network

import android.util.Log
import com.google.gson.Gson
import com.omagrahari.navitest.NaviTestApplication
import com.omagrahari.navitest.model.common.Response
import java.io.IOException
import com.omagrahari.navitest.model.common.Error
import com.omagrahari.navitest.util.NetworkUtil

suspend fun <T> safeApiCall(
    apiCall: suspend () -> retrofit2.Response<T>
): Response<T> {
    if (!NetworkUtil.isNetworkConnected(NaviTestApplication.context))
        return Response.error(Error(message = "No Internet Connection"), null)

    return try {
        val result = apiCall.invoke()
        return if (result.isSuccessful) {
            result.body()?.let {
                Response.success(it)
            } ?: run {
                createError(result)
            }
        } else {
            createError(result)
        }
    } catch (throwable: Throwable) {
        convertErrorBody(throwable)
    }

}

private fun <T> createError(result: retrofit2.Response<T>): Response<T> {
    return try {
        val errorResponse = result?.errorBody()?.string()?.let {

            Log.d("TAG", "ERROR RESPONSE CREATED")
            val error = Gson().fromJson<Error>(it, Error::class.java)
            error
        }

        Response.error(errorResponse, null)
    } catch (throwable: Throwable) {
        convertErrorBody(throwable)
    }
}

private fun <T> convertErrorBody(throwable: Throwable): Response<T> {
    return when (throwable) {
        is IOException -> Response.error(Error(message = "Network Error"), null)
        else -> Response.error(null, null)
    }
}
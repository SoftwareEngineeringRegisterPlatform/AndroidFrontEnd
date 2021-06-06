package cn.hospital.registerplatform.data.dto

import cn.hospital.registerplatform.api.Resource
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


data class RawResult<T>(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("content")
    val content: T
)

fun <T, N> suspendFunctionToFlow(
    successHandler: suspend (FlowCollector<Resource<N>>, T) -> Unit = { flowCollector, t ->
        flowCollector.emit(Resource.Success(t as N))
    },
    errorHandler: suspend (FlowCollector<Resource<N>>, Exception) -> Unit = { flowCollector, exception ->
        flowCollector.emit(Resource.Failure(exception))
    },
    valueGetter: suspend (FlowCollector<Resource<N>>) -> RawResult<T>
) = flow {
    try {
        val rawResult = valueGetter(this)
        if (rawResult.success) {
            successHandler(this, rawResult.content)
        } else {
            errorHandler(this, Exception(rawResult.content.toString()))
        }
    } catch (e: Exception) {
        errorHandler(this, e)
    }
}.flowOn(Dispatchers.IO)

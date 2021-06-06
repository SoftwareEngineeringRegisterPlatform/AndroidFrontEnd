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

fun <GetResultClass, ReturnClass> suspendFunctionToFlow(
    successHandler: suspend (FlowCollector<Resource<ReturnClass>>, GetResultClass) -> Unit = { flowCollector, t ->
        flowCollector.emit(Resource.Success(t as ReturnClass))
    },
    errorHandler: suspend (FlowCollector<Resource<ReturnClass>>, Exception) -> Unit = { flowCollector, exception ->
        flowCollector.emit(Resource.Failure(exception))
    },
    valueGetter: suspend (FlowCollector<Resource<ReturnClass>>) -> RawResult<GetResultClass>
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

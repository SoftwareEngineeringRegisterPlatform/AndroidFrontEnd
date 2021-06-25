package cn.hospital.registerplatform.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CombinedLiveData<A, B>(ld1: LiveData<A>, ld2: LiveData<B>) : MediatorLiveData<Pair<A?, B?>>() {
    private var a: A? = null
    private var b: B? = null

    init {
        value = Pair(a, b)
        addSource(ld1) { a: A? ->
            if (a != null) {
                this.a = a
            }
            setValue(Pair(a, b))
        }
        addSource(ld2) { b: B? ->
            if (b != null) {
                this.b = b
            }
            setValue(Pair(a, b))
        }
    }
}

fun CoroutineScope.launchPeriodicAsync(
    repeatMillis: Long,
    repeatTime: Long,
    repeatAction: (Long) -> Unit,
    finishAction: () -> Unit
) = this.launch {
    for (i in repeatTime downTo 1) {
        repeatAction(i)
        delay(repeatMillis)
    }
    finishAction()
}

fun CoroutineScope.delayLaunch(
    delayTime: Long = 1000,
    finishAction: () -> Unit
) = this.launch {
    delay(delayTime)
    finishAction()
}

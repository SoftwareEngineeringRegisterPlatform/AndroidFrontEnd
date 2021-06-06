package cn.hospital.registerplatform.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData


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

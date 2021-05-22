package cn.hospital.registerplatform.utils

inline fun <T, R> T.doWithTry(block: (T) -> R) {
    try {
        block(this)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}
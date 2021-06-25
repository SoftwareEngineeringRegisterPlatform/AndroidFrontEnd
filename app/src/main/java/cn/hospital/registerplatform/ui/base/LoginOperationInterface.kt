package cn.hospital.registerplatform.ui.base

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope

interface LoginOperationInterface {
    fun requireLogin(): Boolean
    fun needLoginToast(
        context: Context,
        ifLoginOperation: () -> Unit
    )
    fun needLoginJump(
        context: Context,
        coroutineScope: LifecycleCoroutineScope,
        ifLoginOperation: () -> Unit
    )
}

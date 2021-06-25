package cn.hospital.registerplatform.ui.base

import android.content.Context

interface LoginOperationInterface {
    suspend fun requireLogin(): Boolean
    suspend fun needLoginToast(
        context: Context,
        ifLoginOperation: () -> Unit,
        elseOperation: (() -> Unit)?
    )
    suspend fun needLoginToast(
        context: Context,
        ifLoginOperation: () -> Unit
    )
}

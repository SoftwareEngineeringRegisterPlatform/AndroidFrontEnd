package cn.hospital.registerplatform.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ToastUtils {
    private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var mToast: Toast

    fun show(
        context: Context,
        @StringRes resId: Int,
        time: Int = Toast.LENGTH_SHORT
    ) {
        show(context, context.getString(resId), time)
    }

    fun show(
        context: Context?,
        content: String?,
        time: Int = Toast.LENGTH_SHORT
    ) {
        scope.launch {
            if (ToastUtils::mToast.isInitialized) {
                mToast.cancel()
                mToast = Toast.makeText(context, content, time)
                mToast.show()
            } else {
                mToast = Toast.makeText(context, content, time)
                mToast.show()
            }
        }
    }
}
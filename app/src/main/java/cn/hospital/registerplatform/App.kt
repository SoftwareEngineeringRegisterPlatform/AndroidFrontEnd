package cn.hospital.registerplatform

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class App : BaseApp()

open class BaseApp : Application()

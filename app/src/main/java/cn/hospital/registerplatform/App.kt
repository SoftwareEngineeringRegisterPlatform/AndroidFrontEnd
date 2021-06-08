package cn.hospital.registerplatform

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.testing.CustomTestApplication

@HiltAndroidApp
open class App : BaseApp()

open class BaseApp : Application()

@CustomTestApplication(BaseApp::class)
interface HiltTestApp

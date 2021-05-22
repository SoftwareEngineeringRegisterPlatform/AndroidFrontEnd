package cn.hospital.registerplatform.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import cn.hospital.registerplatform.data.UserPreference
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferenceModule {
    @Provides
    @Singleton
    fun providesUserPreference(@ApplicationContext context: Context): UserPreference =
        UserPreference(context)
}
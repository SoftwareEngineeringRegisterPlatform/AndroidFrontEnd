package cn.hospital.registerplatform.di

import cn.hospital.registerplatform.api.interfaces.CommentApi
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.repository.CommentRepository
import cn.hospital.registerplatform.data.repository.RepositoryFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        commentApi: CommentApi,
        userPreference: UserPreference
    ): CommentRepository = RepositoryFactory.makeRepository(commentApi, userPreference)
}
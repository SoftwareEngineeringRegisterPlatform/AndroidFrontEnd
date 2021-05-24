package cn.hospital.registerplatform.di

import cn.hospital.registerplatform.api.interfaces.CommentApi
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.repository.CommentRepository
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
    fun provideCommentRepository(
        commentApi: CommentApi,
        userPreference: UserPreference
    ): CommentRepository = CommentRepository(
        commentApi,
        userPreference
    )
}
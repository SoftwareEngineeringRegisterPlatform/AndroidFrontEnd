package cn.hospital.registerplatform.data.repository

import cn.hospital.registerplatform.api.interfaces.CommentApi
import cn.hospital.registerplatform.data.UserPreference

object RepositoryFactory {
    fun makeRepository(
        commentApi: CommentApi,
        userPreference: UserPreference
    ): CommentRepository = CommentRepositoryImpl(
        commentApi,
        userPreference
    )
}
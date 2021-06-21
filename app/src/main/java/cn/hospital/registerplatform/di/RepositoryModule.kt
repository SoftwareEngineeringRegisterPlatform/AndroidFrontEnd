package cn.hospital.registerplatform.di

import androidx.paging.PagingConfig
import cn.hospital.registerplatform.api.interfaces.*
import cn.hospital.registerplatform.data.UserPreference
import cn.hospital.registerplatform.data.repository.*
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
    fun providePagingConfig(): PagingConfig = PagingConfig(
        pageSize = 20,
        enablePlaceholders = true,
        prefetchDistance = 4,
        initialLoadSize = 20
    )

    @Singleton
    @Provides
    fun provideCommentRepository(
        commentApi: CommentApi,
        userPreference: UserPreference,
        pagingConfig: PagingConfig
    ): CommentRepository = CommentRepository(
        commentApi,
        userPreference,
        pagingConfig
    )

    @Singleton
    @Provides
    fun provideHospitalRepository(
        hospitalApi: HospitalApi,
        userPreference: UserPreference,
        pagingConfig: PagingConfig
    ): HospitalRepository = HospitalRepository(
        hospitalApi,
        userPreference,
        pagingConfig
    )

    @Singleton
    @Provides
    fun provideUserRepository(
        userApi: UserApi,
        userPreference: UserPreference
    ): UserRepository = UserRepository(
        userApi,
        userPreference
    )

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeApi: RecipeApi,
        registerApi: RegisterApi,
        hospitalApi: HospitalApi,
        userApi: UserApi,
        userPreference: UserPreference,
        pagingConfig: PagingConfig
    ): RecipeRepository = RecipeRepository(
        recipeApi,
        registerApi,
        hospitalApi,
        userApi,
        userPreference,
        pagingConfig
    )

    @Singleton
    @Provides
    fun provideRegisterRepository(
        registerApi: RegisterApi,
        hospitalApi: HospitalApi,
        userPreference: UserPreference,
        pagingConfig: PagingConfig
    ): RegisterRepository = RegisterRepository(
        registerApi,
        hospitalApi,
        userPreference,
        pagingConfig
    )
}

package cn.hospital.registerplatform.di


import cn.hospital.registerplatform.BuildConfig
import cn.hospital.registerplatform.api.interfaces.CommentApi
import cn.hospital.registerplatform.api.interfaces.HospitalApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    private val baseUrl = "http://172.81.234.9:8000/"

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideDispatcher(): Dispatcher {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        return dispatcher
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: Interceptor, dispatcher: Dispatcher): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .dispatcher(dispatcher)
            .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(okHttpClient)
        .build()


    @Provides
    @Singleton
    fun provideCommentApi(retrofit: Retrofit): CommentApi =
        retrofit.create(CommentApi::class.java)

    @Provides
    @Singleton
    fun provideHospitalApi(retrofit: Retrofit): HospitalApi =
        retrofit.create(HospitalApi::class.java)
}

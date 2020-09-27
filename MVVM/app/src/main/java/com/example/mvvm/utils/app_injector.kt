package com.example.mvvm.utils

import android.util.Log
import com.example.mvvm.repository.AnimeRepository
import com.example.mvvm.repository.AnimeRepositoryImpl
import com.example.mvvm.view_model.AnimeDetailsViewModel
import com.example.mvvm.view_model.AnimeListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { createLoggingInterceptor() }
    single { createHttpClient(httpLoggingInterceptor = get()) }
    single { createApiService(okHttpClient = get()) }
}
val repositoryModule = module {
    single<AnimeRepository> {
        AnimeRepositoryImpl(service = get())
    }
}

val viewModelModule = module {
    viewModel { AnimeListViewModel(context = get(), animeRepository = get()) }
    viewModel { AnimeDetailsViewModel(animeRepository = get()) }
}
val appModule = listOf(viewModelModule, networkModule, repositoryModule)

private fun createApiService(
    okHttpClient: OkHttpClient
): PostApi {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(PostApi::class.java)
}

private fun createHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
    return okHttpClient.build()
}

private fun createLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor(logger = object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.d("OkHttp", message)
        }
    }).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
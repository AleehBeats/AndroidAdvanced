package com.example.pagedlistapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.pagedlistapp.network.PostApi
import com.example.pagedlistapp.repository.MovieRepository
import com.example.pagedlistapp.repository.MovieRepositoryImpl
import com.example.pagedlistapp.view_model.MovieListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { createLoggingInterceptor() }
    single { createHttpClient(httpLoggingInterceptor = get()) }
    single { createApiService(okHttpClient = get()) }
}

val repositoryModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(
            service = get()
        )
    }
}

val viewModelModule = module {
    viewModel { MovieListViewModel(context = get(), movieRepository = get()) }
}
val appModule = listOf(networkModule, repositoryModule, viewModelModule)

private fun createApiService(
    okHttpClient: OkHttpClient
): PostApi {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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

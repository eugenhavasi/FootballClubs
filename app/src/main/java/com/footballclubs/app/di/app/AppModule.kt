package com.footballclubs.app.di.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import at.allaboutapps.retrofit.converter.unwrap.UnwrapConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.footballclubs.app.BuildConfig
import com.footballclubs.app.di.viewmodel.ViewModelModule
import com.footballclubs.app.networking.UserAgentInterceptor
import com.footballclubs.app.networking.services.ApiService
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import javax.inject.Singleton

@Module(
    includes = [
        ViewModelModule::class,
        AppModule.Bindings::class
    ]
)
class AppModule {

    @Module
    interface Bindings {
        @Binds
        fun context(app: Application): Context
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(agentInterceptor: UserAgentInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(agentInterceptor)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideApiService(
        okHttp: OkHttpClient,
        moshi: Moshi
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_API_URL)
            .client(okHttp)
            .addConverterFactory(UnwrapConverterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Reusable
    @Provides
    fun preferences(app: Application): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)
}

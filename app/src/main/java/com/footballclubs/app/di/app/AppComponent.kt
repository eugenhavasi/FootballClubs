package com.footballclubs.app.di.app

import android.app.Application
import android.content.Context
import com.footballclubs.app.application.App
import com.footballclubs.app.di.ActivityModule
import com.footballclubs.app.di.FragmentModule
import com.footballclubs.app.networking.services.ApiService
import com.squareup.moshi.Moshi
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        FragmentModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    fun apiService(): ApiService

    @Component.Builder
    abstract class Builder : AndroidInjector.Factory<App> {


        /**
         * Create an AppComponent with a bound Application.
         */
        override fun create(app: App): AppComponent {
            seedInstance(app)
            application(app)
            return build()


        }

        @BindsInstance
        abstract fun application(application: Application): Builder

        @BindsInstance
        abstract fun seedInstance(application: App): Builder

        abstract fun build(): AppComponent
    }
}

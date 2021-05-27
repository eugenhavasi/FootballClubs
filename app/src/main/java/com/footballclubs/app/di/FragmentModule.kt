package com.footballclubs.app.di

import com.footballclubs.app.features.main.DetailsFragment
import com.footballclubs.app.features.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @PerFragment
    @ContributesAndroidInjector
    fun provideMainFragment(): MainFragment

    @PerFragment
    @ContributesAndroidInjector
    fun provideDetailsFragment(): DetailsFragment
}

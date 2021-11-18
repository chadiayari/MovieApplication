package com.chadi.movieapp.di

import android.app.Application
import com.chadi.movieapp.movieapp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule ::class,
    MainActivityModule::class,
    AppModule::class])
interface AppComponent {

    fun inject(movieapp: movieapp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}

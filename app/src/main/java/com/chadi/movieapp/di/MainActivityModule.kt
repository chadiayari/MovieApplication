
package com.chadi.movieapp.di

import com.chadi.movieapp.view.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainActivityModule {

  @ContributesAndroidInjector (modules = [FragmentBuildersModule::class])
  abstract fun contributeMainActivity(): MainActivity
}

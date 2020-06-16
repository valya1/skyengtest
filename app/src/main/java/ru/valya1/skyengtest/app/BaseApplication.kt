package ru.valya1.skyengtest.app

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import ru.valya1.skyengtest.common.di.AppComponent
import javax.inject.Inject

class BaseApplication : Application(), HasAndroidInjector {
    
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    
    override fun androidInjector(): AndroidInjector<Any> = androidInjector
    
    override fun onCreate() {
        
        AppComponent.getOrCreate().inject(this)
        super.onCreate()
    }
}
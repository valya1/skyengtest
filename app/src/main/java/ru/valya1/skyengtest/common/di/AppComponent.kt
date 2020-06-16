package ru.valya1.skyengtest.common.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import ru.valya1.skyengtest.app.BaseApplication
import javax.inject.Singleton

@Component(
    modules = [ApiModule::class, AssistantInjectModule::class, AndroidInjectionModule::class, AndroidSupportInjectionModule::class, FragmentsModule::class]
)
@Singleton
interface AppComponent {
    
    companion object {
        
        @Volatile
        var component: AppComponent? = null
        
        @Synchronized
        fun getOrCreate(): AppComponent {
            return component ?: DaggerAppComponent.builder()
                .build()
                .also { component = it }
        }
    }
    
    fun inject(app: BaseApplication)
}
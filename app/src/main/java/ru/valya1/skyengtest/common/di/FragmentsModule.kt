package ru.valya1.skyengtest.common.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.valya1.skyengtest.presentation.meaning_details.MeaningDetailsFragment
import ru.valya1.skyengtest.presentation.meanings_list.MeaningsListFragment

@Module
interface FragmentsModule {
    
    @ContributesAndroidInjector
    fun meaningsListFragment(): MeaningsListFragment
    
    @ContributesAndroidInjector
    fun meaningDetailsFragment(): MeaningDetailsFragment
    
}

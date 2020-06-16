package ru.valya1.skyengtest.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import ru.valya1.skyengtest.R
import ru.valya1.skyengtest.common.ui.fragment.EmptyFragmentArgs
import ru.valya1.skyengtest.common.ui.fragment.asFragment
import ru.valya1.skyengtest.common.ui.fragment.openScreenByArgs
import ru.valya1.skyengtest.presentation.meanings_list.MeaningsListFragment

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        if (savedInstanceState != null) return
        
        supportFragmentManager.openScreenByArgs(EmptyFragmentArgs<MeaningsListFragment>(), false)
        
    }
}
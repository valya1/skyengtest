package ru.valya1.skyengtest.common.ui.fragment

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import ru.valya1.skyengtest.R

inline fun <TArgs : FragmentArgs<TFragment>, reified TFragment : BaseFragment<TArgs>>
    FragmentManager.openScreenByArgs(
    args: TArgs,
    addToBackStack: Boolean = true
) {
    
    val fragment = args.asFragment()
    commit {
        setCustomAnimations(
            R.anim.fragment_enter_from_right,
            R.anim.fragment_exit_to_left,
            R.anim.fragment_enter_from_left,
            R.anim.fragment_exit_to_right
        
        )
        
        replace(
            R.id.fragmentContainerView,
            fragment,
            fragment::class.java.simpleName
        )
        if (addToBackStack) addToBackStack(null)
    }
    
}
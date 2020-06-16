package ru.valya1.skyengtest.common.ui.fragment

import androidx.annotation.LayoutRes
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatFragment
import ru.valya1.skyengtest.common.presentation.BaseMvpView

abstract class BaseFragment<T : FragmentArgs<*>>(@LayoutRes layoutId: Int) : MvpAppCompatFragment(layoutId), BaseMvpView {
    
    companion object {
        const val ARGS = "arguments"
    }
    
    val args: T by lazy { requireArguments().getParcelable<T>(ARGS)!! }
    
    override fun showError(errorText: String) {
        Snackbar.make(requireView(), errorText, Snackbar.LENGTH_LONG).show()
    }
}
package ru.valya1.skyengtest.common.ui.fragment

import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize

abstract class FragmentArgs<TFragment : Fragment> : Parcelable

@Parcelize
class EmptyFragmentArgs<TFragment : Fragment> : FragmentArgs<TFragment>(), Parcelable

inline fun <reified TFragment : BaseFragment<TArgs>, TArgs : FragmentArgs<TFragment>>
    TArgs.asFragment(): BaseFragment<TArgs> = TFragment::class.java.newInstance()
    .apply { arguments = bundleOf(BaseFragment.ARGS to this@asFragment) }
package ru.valya1.skyengtest.presentation.meaning_details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_meaning_details.*
import moxy.ktx.moxyPresenter
import ru.valya1.skyengtest.R
import ru.valya1.skyengtest.common.ui.fragment.BaseFragment
import ru.valya1.skyengtest.common.ui.fragment.FragmentArgs
import ru.valya1.skyengtest.domain.data.network.entity.MeaningDetailsItem
import javax.inject.Inject

class MeaningDetailsFragment :
    BaseFragment<MeaningDetailsFragment.MeaningDetailsArgs>(R.layout.fragment_meaning_details),
    MeaningDetailsView {
    
    @Inject
    lateinit var presenterFactory: MeaningDetailsPresenter.Factory
    
    private val meaningDetailsPresenter by moxyPresenter {
        presenterFactory.createPresenter(args.meaningId)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
    
    override fun showDetails(meaningDetails: MeaningDetailsItem) {
        tvText.text = meaningDetails.text
        tvTranslation.text = meaningDetails.translation?.text ?: ""
        
        val url =
            (if (meaningDetails.images?.isNotEmpty() == true) meaningDetails.images[0].url else null)
                ?: return
        
        Glide.with(requireContext())
            .load("https:${url.dropLastWhile { it != 'g' }}")
            .transition(withCrossFade())
            .into(ivMeaning)
    }
    
    override fun setLoadingState(isLoading: Boolean) {
        spinnerContainer.isVisible = isLoading
    }
    
    @Parcelize
    data class MeaningDetailsArgs(val meaningId: Int) : FragmentArgs<MeaningDetailsFragment>()
}
package ru.valya1.skyengtest.presentation.meanings_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_meanings_list.*
import moxy.ktx.moxyPresenter
import ru.valya1.skyengtest.R
import ru.valya1.skyengtest.common.ui.fragment.BaseFragment
import ru.valya1.skyengtest.common.ui.fragment.EmptyFragmentArgs
import ru.valya1.skyengtest.common.ui.fragment.openScreenByArgs
import ru.valya1.skyengtest.domain.data.network.entity.Meaning
import ru.valya1.skyengtest.presentation.meaning_details.MeaningDetailsFragment
import ru.valya1.skyengtest.presentation.meanings_list.list.MeaningsListAdapter
import javax.inject.Inject

class MeaningsListFragment :
    BaseFragment<EmptyFragmentArgs<MeaningsListFragment>>(R.layout.fragment_meanings_list),
    MeaningsListView {
    
    @Inject
    lateinit var _presenter_: MeaningsListPresenter
    
    private val meaningsListPresenter: MeaningsListPresenter by moxyPresenter { _presenter_ }
    
    private val meaningsListAdapter by lazy {
        MeaningsListAdapter(meaningsListPresenter::onMeaningClicked)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                meaningsListPresenter.submitQuery(newText)
                return true
            }
            
            override fun onQueryTextSubmit(query: String?): Boolean = true
        })
        
        rvMeaningItems.adapter = meaningsListAdapter
    }
    
    override fun setLoadingState(isLoading: Boolean) {
        progressBar.isVisible = isLoading
        rvMeaningItems.isVisible = rvMeaningItems.isVisible && !isLoading
        tvResultsDescription.isVisible = tvResultsDescription.isVisible && !isLoading
    }
    
    override fun showError(errorText: String) {
        Snackbar.make(requireView(), R.string.loading_error_text, Snackbar.LENGTH_SHORT).show()
    }
    
    override fun openMeaning(meaning: Meaning) {
        if (meaning.id != null) {
            parentFragmentManager.openScreenByArgs(
                MeaningDetailsFragment.MeaningDetailsArgs(meaning.id)
            )
        }
    }
    
    override fun submitMeaningsSearchResult(result: MeaningsListView.MeaningsSearchResult) {
        when (result) {
            is MeaningsListView.MeaningsSearchResult.EmptyResult -> {
                tvResultsDescription.text = getString(R.string.empty_search_text)
                tvResultsDescription.isVisible = true
                rvMeaningItems.isVisible = false
            }
            
            is MeaningsListView.MeaningsSearchResult.EmptySearch -> {
                tvResultsDescription.text = getString(R.string.enter_word_part)
                tvResultsDescription.isVisible = true
                rvMeaningItems.isVisible = false
            }
            
            is MeaningsListView.MeaningsSearchResult.MeaningsResult -> {
                tvResultsDescription.isVisible = false
                rvMeaningItems.isVisible = true
                meaningsListAdapter.submitList(result.meanings)
            }
        }
    }
    
}
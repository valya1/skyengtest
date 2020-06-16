package ru.valya1.skyengtest

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.reactivex.internal.operators.single.SingleJust
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import ru.valya1.skyengtest.domain.data.network.MeaningsAPI
import ru.valya1.skyengtest.domain.data.network.entity.Meaning
import ru.valya1.skyengtest.domain.data.network.entity.Translation
import ru.valya1.skyengtest.domain.data.network.entity.WordMeaningItem
import ru.valya1.skyengtest.domain.usecase.meanings.LoadMeanignsUseCase
import ru.valya1.skyengtest.domain.usecase.meanings.SearchMeaningsUseCase
import ru.valya1.skyengtest.presentation.meanings_list.MeaningsListPresenter
import ru.valya1.skyengtest.presentation.meanings_list.MeaningsListView
import ru.valya1.skyengtest.presentation.meanings_list.`MeaningsListView$$State`
import ru.valya1.skyengtest.presentation.meanings_list.list.asListMeaningItem
import ru.valya1.skyengtest.rule.RxSchedulersRule

class MeaningsListPresenterTests {
    
    @JvmField
    @Rule
    val rule = RxSchedulersRule()
    
    @MockK
    lateinit var api: MeaningsAPI
    
    @MockK(relaxUnitFun = true)
    private lateinit var viewState: `MeaningsListView$$State`
    
    @MockK
    private lateinit var meaningsListView: MeaningsListView
    
    private val searchMeaningsUseCase by lazy { SearchMeaningsUseCase(api) }
    private val meaningsUseCase by lazy { LoadMeanignsUseCase(searchMeaningsUseCase) }
    private val presenter by lazy { MeaningsListPresenter(meaningsUseCase) }
    
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        presenter.attachView(meaningsListView)
        presenter.setViewState(viewState)
    }
    
    @Test
    fun `submit not empty search list`() {
        
        val mockedResults = (0..3).map {
            WordMeaningItem(
                it, listOf(
                Meaning(
                    it, it.toString(), it.toString(), it.toString(), it.toString(), it.toString(),
                    Translation(it.toString(), it.toString())
                )
            ),
                it.toString()
            )
        }
        
        every { api.searchMeanings(any(), any(), any()) } returns
            SingleJust(Response.success(mockedResults))
        
        presenter.submitQuery("test")
        
        verifyOrder {
            viewState.setLoadingState(true)
            viewState.setLoadingState(false)
            viewState.submitMeaningsSearchResult(
                MeaningsListView.MeaningsSearchResult.MeaningsResult(
                    mockedResults.map { it.asListMeaningItem() })
            )
        }
        
    }
}
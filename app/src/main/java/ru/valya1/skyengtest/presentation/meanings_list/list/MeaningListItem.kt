package ru.valya1.skyengtest.presentation.meanings_list.list

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.item_word_meaning.view.*
import ru.valya1.skyengtest.common.list.BaseListItem
import ru.valya1.skyengtest.common.ui.util.setOnSafeClickListener
import ru.valya1.skyengtest.domain.data.network.entity.Meaning
import ru.valya1.skyengtest.domain.data.network.entity.WordMeaningItem

sealed class MeaningListItem : BaseListItem {
    
    protected fun loadPreview(url: String, image: ImageView) {
        Glide.with(image.context)
            .load(url)
            .transform(CircleCrop())
            .into(image)
    }
    
    data class MultipleMeaningListItem(val meaningItem: WordMeaningItem) : MeaningListItem() {
        
        override val layoutId: Int = ru.valya1.skyengtest.R.layout.item_word_meaning
        
        fun bind(
            view: View,
            isExpanded: Boolean,
            onMultipleMeaningItemClickListener: (MultipleMeaningListItem) -> Unit
        ) = with(view) {
            
            ivMeaningPreview.isVisible = false
            tvMeaningCount.isVisible = true
            tvMeanings.isVisible = true
            ivArrow.isVisible = true
            
            tvMeanings.text = meaningItem.meanings
                .mapNotNull { it.translation?.text }
                .joinToString(", ")
            
            tvMeaningCount.text = meaningItem.meanings.size.toString()
            ivArrow.rotation = if (isExpanded) 180f else 0f
            tvWord.text = meaningItem.text ?: ""
            
            setOnClickListener {
                ivArrow.animate().rotationBy(180f).start()
                onMultipleMeaningItemClickListener(this@MultipleMeaningListItem)
            }
        }
    }
    
    data class SingleMeaningListItem(val meaningItem: WordMeaningItem) : MeaningListItem() {
        
        override val layoutId: Int = ru.valya1.skyengtest.R.layout.item_word_meaning
        
        fun bind(view: View, onMeaningClickListener: (Meaning) -> Unit) = with(view) {
            
            tvMeanings.isVisible = true
            ivMeaningPreview.isVisible = true
            tvMeaningCount.isVisible = false
            ivArrow.isVisible = false
            
            val meaning = meaningItem.meanings[0]
            
            if (!meaning.previewUrl.isNullOrEmpty()) {
                loadPreview("https:${meaning.previewUrl}", ivMeaningPreview)
            }
            
            tvWord.text = meaningItem.text ?: ""
            tvMeanings.text = meaning.translation?.text ?: ""
            setOnSafeClickListener { onMeaningClickListener(meaning) }
        }
    }
    
    data class ExpandedMeaningListItem(val meaning: Meaning) : MeaningListItem() {
        
        override val layoutId: Int = ru.valya1.skyengtest.R.layout.item_word_meaning
        
        fun bind(view: View, onMeaningClickListener: (Meaning) -> Unit) = with(view) {
            
            tvMeanings.isVisible = false
            ivMeaningPreview.isVisible = true
            tvMeaningCount.isVisible = false
            ivArrow.isVisible = false
            
            
            if (!meaning.imageUrl.isNullOrEmpty()) {
                loadPreview("https:${meaning.previewUrl}", ivMeaningPreview)
            }
            
            tvWord.text = meaning.translation?.text ?: ""
            setOnSafeClickListener { onMeaningClickListener(meaning) }
        }
    }
    
}
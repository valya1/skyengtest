package ru.valya1.skyengtest.presentation.meanings_list.list

import android.view.View
import ru.valya1.skyengtest.common.list.BaseAdapter
import ru.valya1.skyengtest.common.list.ViewHolder
import ru.valya1.skyengtest.domain.data.network.entity.Meaning
import java.util.*

class MeaningsListAdapter(private val singleMeaningClickListener: (Meaning) -> Unit) :
    BaseAdapter<MeaningListItem>() {
    
    private val currentList: MutableList<MeaningListItem> = LinkedList()
    
    override fun getItem(position: Int): MeaningListItem = currentList[position]
    
    override fun getItemCount(): Int = currentList.size
    
    override fun bind(
        view: View, viewHolder: ViewHolder, item: MeaningListItem, position: Int
    ) {
        when (item) {
            is MeaningListItem.MultipleMeaningListItem -> item.bind(
                view,
                item.isExpandedOnPosition(position)
            ) { expandableItem ->
                handleExpandableItemClick(expandableItem, viewHolder.adapterPosition)
            }
            
            is MeaningListItem.SingleMeaningListItem -> item.bind(view, singleMeaningClickListener)
            
            is MeaningListItem.ExpandedMeaningListItem -> item.bind(
                view, singleMeaningClickListener
            )
        }
    }
    
    private fun MeaningListItem.MultipleMeaningListItem.isExpandedOnPosition(
        position: Int
    ): Boolean {
        if (position == currentList.lastIndex) return false
        val nextItem = currentList[position + 1]
        return (nextItem is MeaningListItem.ExpandedMeaningListItem && nextItem.meaning == this.meaningItem.meanings[0])
    }
    
    private fun handleExpandableItemClick(
        item: MeaningListItem.MultipleMeaningListItem, position: Int
    ) {
        
        if (item.isExpandedOnPosition(position)) {
            collapse(position, item.meaningItem.meanings.size)
        } else {
            expand(position, item.meaningItem.meanings)
        }
    }
    
    fun submitList(meanings: List<MeaningListItem>) {
        currentList.clear()
        currentList.addAll(meanings)
        notifyDataSetChanged()
    }
    
    private fun expand(expandingPosition: Int, meanings: List<Meaning>) {
        currentList.addAll(
            expandingPosition + 1,
            meanings.map { MeaningListItem.ExpandedMeaningListItem(it) }
        )
        notifyItemRangeInserted(expandingPosition + 1, meanings.size)
    }
    
    private fun collapse(expandedPosition: Int, count: Int) {
        for (i in 0 until count) currentList.removeAt(expandedPosition + 1)
        notifyItemRangeRemoved(expandedPosition + 1, count)
    }
}

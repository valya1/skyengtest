package ru.valya1.skyengtest.common.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : BaseListItem> :
    RecyclerView.Adapter<ViewHolder>() {
    
    abstract fun bind(view: View, viewHolder: ViewHolder, item: T, position: Int)
    
    abstract fun getItem(position: Int): T
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        bind(holder.itemView, holder, getItem(position), position)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        )
    
    override fun getItemViewType(position: Int) = getItem(position).layoutId
    
}